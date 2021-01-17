package com.manhtai.calendartime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.manhtai.calendartime.adapter.WorkAdapter;
import com.manhtai.calendartime.database.AppDatabase;
import com.manhtai.calendartime.fragment.ChooseDateFragment;
import com.manhtai.calendartime.fragment.PunishFragment;
import com.manhtai.calendartime.fragment.TimeAfternoonFragment;
import com.manhtai.calendartime.fragment.TimeEveningFragment;
import com.manhtai.calendartime.fragment.TimeMorningFragment;
import com.manhtai.calendartime.model.Work;
import com.manhtai.calendartime.until1.CallBackRecyclerView;
import com.manhtai.calendartime.until1.IGetDataFromFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateWorkActivity extends AppCompatActivity implements IGetDataFromFragment, CallBackRecyclerView {
    private static final String TAG = "AAA";
    private ImageButton ivAddWorkCancel;
    private ImageButton ivAddWorkConfirm;
    private TextInputEditText edtAddWorkName;
    private RadioButton rbAddWorkDateCurrent;
    private RadioButton rbAddWorkManyDate;
    private FrameLayout flAddWorkDate;
    private CheckBox cbAddWorkPunish;
    private FrameLayout flAddWorkPunish;
    private CheckBox cbAddWork1;
    private CheckBox cbAddWork2;
    private CheckBox cbAddWork3;
    private FrameLayout flAddWorkTimeMorning;
    private FrameLayout flAddWorkTimeAfternoon;
    private FrameLayout flAddWorkTimeEverning;
    private TextInputEditText edtAddWorkNote;
    private Button btnAddWorkConfirm;
    String dateWork = "";
    String moneyPunish = null;
    String timeWork = null;
    int tmp1;
    int tmp2;
    int tmp3;
    ChooseDateFragment chooseDateFragment = new ChooseDateFragment();
    Work work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_time);
        anhXa();
        Log.d(TAG, "onCreate: " + getIntent().getIntExtra("key_id_work", -1));
        setDataToField();
        updateData();
    }

    private void updateData() {
        setClickHeader();
        setDateWorkRadio();
        setBooleanPunish();
        setShifts();
        setClickButtonConfirm();
    }

    private void setShifts() {
        cbAddWork1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_morning, new TimeMorningFragment()).commit();
                } else {
                    flAddWorkTimeMorning.removeAllViews();
                }
            }
        });
        cbAddWork2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_afternoon, new TimeAfternoonFragment()).commit();
                } else {
                    flAddWorkTimeAfternoon.removeAllViews();
                }
            }
        });
        cbAddWork3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_everning, new TimeEveningFragment()).commit();
                } else {
                    flAddWorkTimeEverning.removeAllViews();
                }
            }
        });
    }

    private void setBooleanPunish() {
        cbAddWorkPunish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_punish, PunishFragment.newInstance()).commit();
                } else {
                    flAddWorkPunish.removeAllViews();
                }
            }
        });
    }

    private void setDateWorkRadio() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_date, chooseDateFragment).commit();
        rbAddWorkManyDate.setText("Ngày khác");

        dateWork = work.getDate();

        Log.d(TAG, "setDateWorkRadio: " + dateWork);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseDateFragment.setDateToField(dateWork);
            }
        }, 300);


    }

    private void setClickButtonConfirm() {
        btnAddWorkConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInfomation();
                finish();
            }
        });
    }

    private void confirmInfomation() {
        String nameWork;
        if (edtAddWorkName.getText().toString().trim().length() == 0) {
            nameWork = "Work";
        } else {
            nameWork = edtAddWorkName.getText().toString().trim();
        }

        Boolean isPunish = cbAddWorkPunish.isChecked();
        timeWork = String.valueOf(tmp1 + tmp2 + tmp3);
        StringBuilder builder = new StringBuilder();
        if (cbAddWork1.isChecked()) {
            builder.append(cbAddWork1.getText().toString() + "  ");
            if (cbAddWork2.isChecked()) {
                builder.append(cbAddWork2.getText().toString() + "  ");
                if (cbAddWork3.isChecked()) {
                    builder.append(cbAddWork3.getText().toString() + "  ");
                }
            } else {
                if (cbAddWork3.isChecked()) {
                    builder.append(cbAddWork3.getText().toString() + "  ");
                }
            }
        } else {
            if (cbAddWork2.isChecked()) {
                builder.append(cbAddWork2.getText().toString() + "  ");
                if (cbAddWork3.isChecked()) {
                    builder.append(cbAddWork3.getText().toString() + "  ");
                }
            } else {
                if (cbAddWork3.isChecked()) {
                    builder.append(cbAddWork3.getText().toString() + "  ");
                }
            }
        }


        Log.d(TAG, "confirmInfomation: " + builder);
        String note = edtAddWorkNote.getText().toString();

        //work = new Work(nameWork, dateWork, isPunish, moneyPunish, builder.toString(), timeWork, note);
        work.setNameEvent(nameWork);
        work.setDate(dateWork);
        work.setPunish(isPunish);
        work.setMoneyPunish(moneyPunish);
        work.setShifts(builder.toString());
        work.setNumberOfTime(timeWork);
        work.setNote(note);

        Log.d(TAG, "confirmInfomation: " + work.toString());
        AppDatabase.getInstance(UpdateWorkActivity.this).workDao().updateWork(work);
        if(WorkTimeActivity.workAdapter == null){
            Toast.makeText(UpdateWorkActivity.this, "Vui lòng mở lại để cập nhật danh sách", Toast.LENGTH_SHORT).show();
        }else{
            WorkTimeActivity.workAdapter = new WorkAdapter(UpdateWorkActivity.this,AppDatabase.getInstance(UpdateWorkActivity.this).workDao().getAllOrdered());
            WorkTimeActivity.rvWorkTime.setAdapter(WorkTimeActivity.workAdapter);
        }

    }

    private void setClickHeader() {
        ivAddWorkCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivAddWorkConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInfomation();
            }
        });
    }

    private void setDataToField() {
        if (work == null) {
            work = AppDatabase.getInstance(this).workDao().getWorkById(getIntent().getIntExtra("key_id_work", -1));
        }
        if(work.getNameEvent().equals("Work")){
            edtAddWorkName.setText("");
        }else
        edtAddWorkName.setText(work.getNameEvent());
        if (work.getDate().equals(rbAddWorkDateCurrent.getText().toString())) {
            rbAddWorkDateCurrent.setChecked(true);
            rbAddWorkManyDate.setVisibility(View.INVISIBLE);
        } else {
            rbAddWorkManyDate.setChecked(true);
            rbAddWorkDateCurrent.setVisibility(View.INVISIBLE);
        }
        if (work.isPunish()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_punish, PunishFragment.newInstance()).commit();
        } else {
            flAddWorkPunish.removeAllViews();
        }
        String[] shifts = work.getShifts().split("  ");
        for (int i = 0; i < shifts.length; i++) {
            if (shifts[i].equals(cbAddWork1.getText().toString())) {
                cbAddWork1.setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_morning, new TimeMorningFragment()).commit();
            }
            if (shifts[i].equals(cbAddWork2.getText().toString())) {
                cbAddWork2.setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_afternoon, new TimeAfternoonFragment()).commit();

            }
            if (shifts[i].equals(cbAddWork3.getText().toString())) {
                cbAddWork3.setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_everning, new TimeEveningFragment()).commit();

            }

        }

        edtAddWorkNote.setText(work.getNote());



    }

    @Override
    public void getTextData(String s) {
        moneyPunish = s;
    }

    @Override
    public void getTextTimeWorkMorning(String s) {
        tmp1 = Integer.parseInt(s);
    }

    @Override
    public void getTextTimeWorkAfternoon(String s) {
        tmp2 = Integer.parseInt(s);
    }

    @Override
    public void getTextTimeWorkEvening(String s) {
        tmp3 = Integer.parseInt(s);
    }


    private void anhXa() {
        ivAddWorkCancel = findViewById(R.id.iv_add_work_cancel);
        ivAddWorkConfirm = findViewById(R.id.iv_add_work_confirm);
        edtAddWorkName = findViewById(R.id.edt_add_work_name);
        rbAddWorkDateCurrent = findViewById(R.id.rb_add_work_date_current);
        rbAddWorkManyDate = findViewById(R.id.rb_add_work_many_date);
        flAddWorkDate = findViewById(R.id.fl_add_work_date);
        cbAddWorkPunish = findViewById(R.id.cb_add_work_punish);
        flAddWorkPunish = findViewById(R.id.fl_add_work_punish);
        cbAddWork1 = findViewById(R.id.cb_add_work_1);
        cbAddWork2 = findViewById(R.id.cb_add_work_2);
        cbAddWork3 = findViewById(R.id.cb_add_work_3);
        flAddWorkTimeMorning = findViewById(R.id.fl_add_work_time_morning);
        flAddWorkTimeAfternoon = findViewById(R.id.fl_add_work_time_afternoon);
        flAddWorkTimeEverning = findViewById(R.id.fl_add_work_time_everning);
        edtAddWorkNote = findViewById(R.id.edt_add_work_note);
        btnAddWorkConfirm = findViewById(R.id.btn_add_work_confirm);
    }

    @Override
    public void getItemChecked(List<Work> works, boolean b) {

    }
}