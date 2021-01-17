package com.manhtai.calendartime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manhtai.calendartime.adapter.WorkAdapter;
import com.manhtai.calendartime.database.AppDatabase;
import com.manhtai.calendartime.model.Work;
import com.manhtai.calendartime.until1.CallBackRecyclerView;

import java.util.List;

public class WorkTimeActivity extends AppCompatActivity implements CallBackRecyclerView {
    private static final String TAG = "AAA";
    public static RecyclerView rvWorkTime;
    private FloatingActionButton fabWorkTime;
    private Toolbar mTbWorkTime;
    public static WorkAdapter workAdapter;
    private CheckBox cbWorkTimeCheckAll;
    private TextView tvWorkTimeClear;
    Context context = WorkTimeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_time);
        rvWorkTime = findViewById(R.id.rv_work_time);
        fabWorkTime = findViewById(R.id.fab_work_time);
        mTbWorkTime = findViewById(R.id.tb_work_time);
        cbWorkTimeCheckAll = findViewById(R.id.cb_work_time_check_all);
        tvWorkTimeClear = findViewById(R.id.tv_work_time_clear);

        setCustomActionBar();
        fabWorkTime.setVisibility(View.INVISIBLE);
        cbWorkTimeCheckAll.setVisibility(View.INVISIBLE);
        setRecyclerView();
       // orderRecyclerViewById();

    }

    private void setRecyclerView() {
        tvWorkTimeClear.setVisibility(View.INVISIBLE);
        cbWorkTimeCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                workAdapter = new WorkAdapter(WorkTimeActivity.this, AppDatabase.getInstance(WorkTimeActivity.this).workDao().getAllOrdered(), cbWorkTimeCheckAll.isChecked());
                rvWorkTime.setAdapter(workAdapter);
                if (isChecked) {
                    tvWorkTimeClear.setVisibility(View.VISIBLE);
                    tvWorkTimeClear.setText("Xóa tất cả");
                } else {
                    tvWorkTimeClear.setVisibility(View.INVISIBLE);
                }
            }
        });
//        rvWorkTime.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                workAdapter = new WorkAdapter(WorkTimeActivity.this, AppDatabase.getInstance(WorkTimeActivity.this).workDao().getAll());
//                rvWorkTime.setAdapter(workAdapter);
//                rvWorkTime.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
//
//            }
//        });
        workAdapter = new WorkAdapter(WorkTimeActivity.this, AppDatabase.getInstance(WorkTimeActivity.this).workDao().getAllOrdered());
        rvWorkTime.setAdapter(workAdapter);
        rvWorkTime.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

    }

    private void setCustomActionBar() {
        setSupportActionBar(mTbWorkTime);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_work_time);
        View view = getSupportActionBar().getCustomView();
        ImageButton ibCustomAbBack = view.findViewById(R.id.ib_custom_ab_back);
        ibCustomAbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void getItemChecked(List<Work> works, boolean b) {
        tvWorkTimeClear.setVisibility(View.VISIBLE);
        tvWorkTimeClear.setText("Xóa");
        if(b){
            cbWorkTimeCheckAll.setVisibility(View.VISIBLE);
        }else{
            cbWorkTimeCheckAll.setVisibility(View.INVISIBLE);
        }
        tvWorkTimeClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove item
                for (Work work : works) {
                    AppDatabase.getInstance(context).workDao().deleteWork(work);
                }
                //load recyclerview again
                workAdapter = new WorkAdapter(WorkTimeActivity.this, AppDatabase.getInstance(WorkTimeActivity.this).workDao().getAll(), cbWorkTimeCheckAll.isChecked());
                rvWorkTime.setAdapter(workAdapter);
            }
        });
    }
}