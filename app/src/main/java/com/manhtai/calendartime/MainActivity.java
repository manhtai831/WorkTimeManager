package com.manhtai.calendartime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manhtai.calendartime.adapter.WorkAdapter;
import com.manhtai.calendartime.database.AppDatabase;
import com.manhtai.calendartime.model.Work;
import com.manhtai.calendartime.until1.CallBackRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallBackRecyclerView {

    private static final String TAG = "AAA";
    private TextView tvTime;
    private TextView tvDate;
    private ImageButton ibMore;
    private ImageButton ibWorkTime;
    private FloatingActionButton fabMain;
    private RecyclerView rvMain;
    private CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        setDateTime();
        setOnClickWorkTime();
        setOnClickButtonMore();
        setOnClickFloatingButton();
        rvMain.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
    }

    private void anhXa() {
        tvTime = findViewById(R.id.tv_time);
        tvDate = findViewById(R.id.tv_date);
        ibMore = findViewById(R.id.ib_more);
        ibWorkTime = findViewById(R.id.ib_work_time);
        fabMain = findViewById(R.id.fab_main);
        rvMain = findViewById(R.id.rv_main);
        calendarView = findViewById(R.id.calendar_view);
    }

    private void setOnClickButtonMore() {
        ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,ibMore);
                popupMenu.inflate(R.menu.menu_options);
                Menu menu = popupMenu.getMenu();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return setClickItemMenu(item);
                    }
                });
                popupMenu.show();

            }
        });
    }

    private boolean setClickItemMenu(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                return true;
            case R.id.menu_setting:
                startActivity(new Intent(MainActivity.this, ThongKeActivity.class));
                return true;
            default: return false;
        }
    }

    private void setOnClickFloatingButton() {
        Intent intent = new Intent(MainActivity.this,AddWorkTimeActivity.class);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                List<Work> tmpListWork = new ArrayList<>();
                for(Work work : AppDatabase.getInstance(MainActivity.this).workDao().getAll()){
                    String dateCompare = work.getDate().split("\\.")[1] + "." + work.getDate().split("\\.")[2].substring(0,2);
                    if(dateCompare.equals(validateDateTime(year,month,dayOfMonth))){
                        tmpListWork.add(work);
                    }
                }
                if(tmpListWork != null){
                    WorkAdapter workAdapter = new WorkAdapter(MainActivity.this, tmpListWork);

                    rvMain.setAdapter(workAdapter);
                }else{

                }

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if(month+1 < 10){
                    if(dayOfMonth < 10){
                        intent.putExtra("date", calendar.get(Calendar.DAY_OF_WEEK) + ", " + year + ".0" + (month + 1) + ".0" + dayOfMonth + " 00:00:00");
                    }else{
                        intent.putExtra("date", calendar.get(Calendar.DAY_OF_WEEK) + ", " + year + ".0" + (month + 1) + "." + dayOfMonth + " 00:00:00");
                    }

                }else{
                    if(dayOfMonth < 10){
                        intent.putExtra("date", calendar.get(Calendar.DAY_OF_WEEK) + ", " + year + ".0" + (month + 1) + ".0" + dayOfMonth + " 00:00:00");
                    }else{
                        intent.putExtra("date", calendar.get(Calendar.DAY_OF_WEEK) + ", " + year + "." + (month + 1) + "." + dayOfMonth + " 00:00:00");
                    }
                }
            }
        });

        
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void setOnClickWorkTime() {
        ibWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WorkTimeActivity.class));

            }
        });
    }
    private String validateDateTime(int year,int month,int dayOfMonth){
        StringBuilder builder = new StringBuilder();
        if(month+1 < 10){
            if(dayOfMonth < 10){
                builder.append("0" + (month + 1) + ".0" + dayOfMonth);

            }else{
                builder.append("0" + (month + 1) + "." + dayOfMonth);
            }

        }else{
            if(dayOfMonth < 10){
                builder.append("0" + (month + 1) + ".0" + dayOfMonth);
            }else{
                builder.append((month + 1) + "." + dayOfMonth);
            }
        }
        return builder.toString();
    }

//    private void setRecyclerViewWork() {
//        WorkAdapter workAdapter = new WorkAdapter(this,AppDatabase.getInstance(this).workDao().getAll());
//        rvMain.setAdapter(workAdapter);
//    }

    private void setDateTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat format1 = new SimpleDateFormat("E, dd.MM.yyyy");
                tvTime.setText(format.format(new Date().getTime()));
                tvDate.setText(format1.format(new Date().getTime()));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText(format.format(new Date().getTime()));
                        new Handler().postDelayed(this,1000);
                    }
                },1000);

            }
        },200);

    }


    @Override
    public void getItemChecked(List<Work> works, boolean b) {

    }
}