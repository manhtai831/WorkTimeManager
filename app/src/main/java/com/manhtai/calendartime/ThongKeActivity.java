package com.manhtai.calendartime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.manhtai.calendartime.adapter.WorkAdapter;
import com.manhtai.calendartime.database.AppDatabase;
import com.manhtai.calendartime.model.Work;
import com.manhtai.calendartime.until1.CallBackAboutRecyclerView;
import com.manhtai.calendartime.until1.CallBackRecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity implements CallBackRecyclerView {
    private static final String TAG = "AAA";
    private TextView tvNumberOfDateNomal;
    private TextView tvNumberOfDateSpecial;
    private Spinner spMonth;
    private Spinner spYear;
    public static RecyclerView rvThongKe;
    private Toolbar tbThongKe;
    private TextView tvThongKeTongTien;
    public static WorkAdapter workAdapter;
    private RadioGroup rgThongKe;
    final String[] array_months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    List<Integer> list_years = new ArrayList<>();
    String month_query;
    String year_query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        anhXa();

        setCustomActionBar();
        setArrayAdapterMonth();
        setArrayAdapterYear();
        setQueryByMonth();
        setQueryByYear();
        setQueryByDayOfWeek();
        rvThongKe.addItemDecoration(new DividerItemDecoration(ThongKeActivity.this, DividerItemDecoration.VERTICAL));
    }

    private void setQueryByDayOfWeek() {
        List<Work> workQueries = new ArrayList<>();
        workQueries.clear();
        List<Work> works = AppDatabase.getInstance(this).workDao().getAllOrdered();
        for (Work work : works) {
            String mMonth = work.getDate().split("\\.")[1];
            String mYear = work.getDate().split("\\.")[0].substring(work.getDate().split("\\.")[0].length() - 4);
            Log.d(TAG, "query: " + mYear + mMonth);
            if (mMonth.equals(spMonth.getSelectedItem().toString()) && mYear.equals(spYear.getSelectedItem().toString())) {
                workQueries.add(work);
                Log.d(TAG, "query: Confirmed");
            }
        }
        rgThongKe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_thong_ke_all:
                        workAdapter = new WorkAdapter(ThongKeActivity.this, workQueries);
                        rvThongKe.setAdapter(workAdapter);
                        break;
                    case R.id.rb_thong_ke_nomal:
                        List<Work> workNomalsWorks1 = new ArrayList<>();
                        for (Work work : workQueries) {
                            String dateTmp = work.getDate().split(",")[0];
                            if (!(dateTmp.equals("Th 7") || dateTmp.equals("Th 8") ||
                                    dateTmp.substring(dateTmp.length() - 1).equals("7") ||
                                    dateTmp.substring(dateTmp.length() - 1).equals("8") ||
                                    dateTmp.equals("CN") ||
                                    dateTmp.substring(dateTmp.length() - 1).equals("1"))){
                                workNomalsWorks1.add(work);
                            }
                        }
                        workAdapter = new WorkAdapter(ThongKeActivity.this, workNomalsWorks1);
                        rvThongKe.setAdapter(workAdapter);
                        break;
                    case R.id.rb_thong_ke_special:
                        List<Work> workNomalsWorks2 = new ArrayList<>();
                        for (Work work : workQueries) {
                            String dateTmp = work.getDate().split(",")[0];
                            if ((dateTmp.equals("Th 7") || dateTmp.equals("Th 8") ||
                                    dateTmp.substring(dateTmp.length() - 1).equals("7") ||
                                    dateTmp.substring(dateTmp.length() - 1).equals("8") ||
                                    dateTmp.equals("CN") ||
                                    dateTmp.substring(dateTmp.length() - 1).equals("1"))){
                                workNomalsWorks2.add(work);
                            }
                        }
                        workAdapter = new WorkAdapter(ThongKeActivity.this, workNomalsWorks2);
                        rvThongKe.setAdapter(workAdapter);
                        break;
                    default:
                        workAdapter = new WorkAdapter(ThongKeActivity.this, workQueries);
                        rvThongKe.setAdapter(workAdapter);
                }
            }
        });

    }

    private void setQueryByYear() {
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                month_query = spMonth.getSelectedItem().toString();
                year_query = list_years.get(position).toString();
                query(month_query, year_query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setQueryByMonth() {
        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + position);
                month_query = array_months[position];
                year_query = spYear.getSelectedItem().toString();
                query(month_query, year_query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void query(String month_query, String year_query) {
        List<Work> workQueries = new ArrayList<>();
        workQueries.clear();
        List<Work> works = AppDatabase.getInstance(this).workDao().getAllOrdered();
        for (Work work : works) {
            String mMonth = work.getDate().split("\\.")[1];
            String mYear = work.getDate().split("\\.")[0].substring(work.getDate().split("\\.")[0].length() - 4);
            Log.d(TAG, "query: " + mYear + mMonth);
            if (mMonth.equals(month_query) && mYear.equals(year_query)) {
                workQueries.add(work);
                Log.d(TAG, "query: Confirmed");
            }
        }
        workAdapter = new WorkAdapter(ThongKeActivity.this, workQueries);

        rvThongKe.setAdapter(workAdapter);
        int i = 0;
        int j = 0;
        int timeNomal = 0;
        int timeSpecial = 0;
        for (Work work : workQueries) {
            String dateTmp = work.getDate().split(",")[0];
            if (dateTmp.equals("Th 7") || dateTmp.equals("Th 8") ||
                    dateTmp.substring(dateTmp.length() - 1).equals("7") ||
                    dateTmp.substring(dateTmp.length() - 1).equals("8") ||
                    dateTmp.equals("CN") ||
                    dateTmp.substring(dateTmp.length() - 1).equals("1")) {
                i++;
                timeSpecial += Integer.parseInt(work.getNumberOfTime());
            } else {
                j++;
                timeNomal += Integer.parseInt(work.getNumberOfTime());
            }
        }
        tvNumberOfDateNomal.setText(j + " ngày. " + timeNomal + " giờ");
        tvNumberOfDateSpecial.setText(i + " ngày. " + timeSpecial + " giờ");

        tvThongKeTongTien.setText(timeNomal + " * 16 + " + timeSpecial + " * 18 = " + NumberFormat.getInstance().format(timeNomal * 16 + timeSpecial * 18) + " nghìn");

    }

    private void setArrayAdapterYear() {
        list_years.add(Calendar.getInstance().get(Calendar.YEAR));
        list_years.add(Calendar.getInstance().get(Calendar.YEAR) - 1);
        list_years.add(Calendar.getInstance().get(Calendar.YEAR) - 2);
        list_years.add(Calendar.getInstance().get(Calendar.YEAR) - 3);
        list_years.add(Calendar.getInstance().get(Calendar.YEAR) - 4);
        list_years.add(Calendar.getInstance().get(Calendar.YEAR) - 5);
        ArrayAdapter adapterYear = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_years);
        spYear.setAdapter(adapterYear);
        spMonth.setPadding(0, 0, 5, 0);
    }

    private void setArrayAdapterMonth() {
        ArrayAdapter adapterMonth = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_months) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView = super.getDropDownView(position, convertView, parent);
                convertView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams p = convertView.getLayoutParams();
                p.height = 120;
                convertView.setLayoutParams(p);

                return convertView;
            }
        };
        spMonth.setAdapter(adapterMonth);
        spYear.setPadding(0, 0, 5, 0);
    }

    private void anhXa() {
        tvNumberOfDateNomal = findViewById(R.id.tv_number_of_date_nomal);
        tvNumberOfDateSpecial = findViewById(R.id.tv_number_of_date_special);
        spMonth = findViewById(R.id.sp_month);
        spYear = findViewById(R.id.sp_year);
        tbThongKe = findViewById(R.id.tb_thong_ke);
        rvThongKe = findViewById(R.id.rv_thong_ke);
        tvThongKeTongTien = findViewById(R.id.tv_thong_ke_tong_tien);
        rgThongKe = findViewById(R.id.rg_thong_ke);
    }

    private void setCustomActionBar() {
        setSupportActionBar(tbThongKe);
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

    }
}