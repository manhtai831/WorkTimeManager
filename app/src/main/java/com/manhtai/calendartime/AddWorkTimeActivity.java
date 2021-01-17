package com.manhtai.calendartime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.manhtai.calendartime.adapter.WorkAdapter;
import com.manhtai.calendartime.dao.WorkDao;
import com.manhtai.calendartime.database.AppDatabase;
import com.manhtai.calendartime.fragment.ChooseDateFragment;
import com.manhtai.calendartime.fragment.PunishFragment;
import com.manhtai.calendartime.fragment.TimeAfternoonFragment;
import com.manhtai.calendartime.fragment.TimeEveningFragment;
import com.manhtai.calendartime.fragment.TimeMorningFragment;
import com.manhtai.calendartime.model.Work;
import com.manhtai.calendartime.until1.IGetDataFromFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddWorkTimeActivity extends AppCompatActivity implements IGetDataFromFragment {
    private static final String TAG = "AAA";
    private ImageButton mIvAddWorkCancel;
    private ImageButton mIvAddWorkConfirm;
    private TextInputEditText mEdtAddWorkName;
    private FrameLayout mFlAddWorkDate;
    private CheckBox mCbAddWorkPunish;
    private FrameLayout mFlAddWorkPunish;
    private CheckBox mCbAddWork1;
    private CheckBox mCbAddWork2;
    private CheckBox mCbAddWork3;
    private FrameLayout mFlAddWorkTimeMorning;
    private FrameLayout mFlAddWorkTimeAfternoon;
    private FrameLayout mFlAddWorkTimeEverning;

    private TextInputEditText mEdtAddWorkNote;
    private Button mBtnAddWorkConfirm;
    private RadioGroup mRadioGroup;
    private RadioButton mRbAddWorkDateCurrent;
    private RadioButton mRbAddWorkManyDate;
    String dateWork = "";
    String moneyPunish = null;
    String timeWork = null;
    int tmp1;
    int tmp2;
    int tmp3;
    ChooseDateFragment chooseDateFragment = new ChooseDateFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_time);

        anhXa();
        setClickHeader();
        setDateWorkRadio();
        setBooleanPunish();
        setShifts();
        setClickButtonConfirm();


    }


    private void setShifts() {
        mCbAddWork1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_morning, new TimeMorningFragment()).commit();
                } else {
                    mFlAddWorkTimeMorning.removeAllViews();
                }
            }
        });
        mCbAddWork2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_afternoon, new TimeAfternoonFragment()).commit();
                } else {
                    mFlAddWorkTimeAfternoon.removeAllViews();
                }
            }
        });
        mCbAddWork3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_time_everning, new TimeEveningFragment()).commit();
                } else {
                    mFlAddWorkTimeEverning.removeAllViews();
                }
            }
        });
    }

    private void setBooleanPunish() {
        mCbAddWorkPunish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_punish, PunishFragment.newInstance()).commit();
                } else {
                    mFlAddWorkPunish.removeAllViews();
                }
            }
        });
    }

    private void setDateWorkRadio() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_add_work_date, chooseDateFragment).commit();
        mRbAddWorkManyDate.setText("Ngày khác");
        if (getIntent().getStringExtra("date") == null) {
            dateWork = new SimpleDateFormat("EEE, yyyy.MM.dd 'at' HH:mm:ss").format(new Date(String.valueOf(Calendar.getInstance().getTime())));
            mRbAddWorkManyDate.setVisibility(View.INVISIBLE);
            mRbAddWorkDateCurrent.setChecked(true);
        } else {
            dateWork = getIntent().getStringExtra("date");
            mRbAddWorkDateCurrent.setVisibility(View.INVISIBLE);
            mRbAddWorkManyDate.setChecked(true);
        }
        Log.d(TAG, "setDateWorkRadio: " + dateWork);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseDateFragment.setDateToField(dateWork);
            }
        }, 300);


    }

    private void setClickButtonConfirm() {
        mBtnAddWorkConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInformation()){
                    confirmInfomation();
                    finish();
                }
            }
        });
    }

    private boolean validateInformation() {
        if(!(mCbAddWork1.isChecked() || mCbAddWork2.isChecked() || mCbAddWork3.isChecked())){
            Toast.makeText(this, "Vui lòng chọn ca làm", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

    private void confirmInfomation() {
        String nameWork;
        if (mEdtAddWorkName.getText().toString().trim().length() == 0) {
            nameWork = "Work";
        } else {
            nameWork = mEdtAddWorkName.getText().toString().trim();
        }

        Boolean isPunish = mCbAddWorkPunish.isChecked();
        timeWork = String.valueOf(tmp1 + tmp2 + tmp3);
        StringBuilder builder = new StringBuilder();
        if (mCbAddWork1.isChecked()) {
            builder.append(mCbAddWork1.getText().toString() + "  ");
            if (mCbAddWork2.isChecked()) {
                builder.append(mCbAddWork2.getText().toString() + "  ");
                if (mCbAddWork3.isChecked()) {
                    builder.append(mCbAddWork3.getText().toString() + "  ");
                }
            } else {
                if (mCbAddWork3.isChecked()) {
                    builder.append(mCbAddWork3.getText().toString() + "  ");
                }
            }
        } else {
            if (mCbAddWork2.isChecked()) {
                builder.append(mCbAddWork2.getText().toString() + "  ");
                if (mCbAddWork3.isChecked()) {
                    builder.append(mCbAddWork3.getText().toString() + "  ");
                }
            } else {
                if (mCbAddWork3.isChecked()) {
                    builder.append(mCbAddWork3.getText().toString() + "  ");
                }
            }
        }


        String note = mEdtAddWorkNote.getText().toString();

        Work work = new Work(nameWork, dateWork, isPunish, moneyPunish, builder.toString(), timeWork, note);

        Log.d(TAG, "confirmInfomation: " + work.toString());
        AppDatabase.getInstance(AddWorkTimeActivity.this).workDao().insertWork(work);
    }

    private void setClickHeader() {
        mIvAddWorkCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mIvAddWorkConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInformation()){
                    confirmInfomation();
                    finish();
                }
            }
        });
    }

    private void anhXa() {
        mIvAddWorkCancel = findViewById(R.id.iv_add_work_cancel);
        mIvAddWorkConfirm = findViewById(R.id.iv_add_work_confirm);
        mEdtAddWorkName = findViewById(R.id.edt_add_work_name);
        mFlAddWorkDate = findViewById(R.id.fl_add_work_date);
        mCbAddWorkPunish = findViewById(R.id.cb_add_work_punish);
        mFlAddWorkPunish = findViewById(R.id.fl_add_work_punish);
        mCbAddWork1 = findViewById(R.id.cb_add_work_1);
        mCbAddWork2 = findViewById(R.id.cb_add_work_2);
        mCbAddWork3 = findViewById(R.id.cb_add_work_3);
        mEdtAddWorkNote = findViewById(R.id.edt_add_work_note);
        mBtnAddWorkConfirm = findViewById(R.id.btn_add_work_confirm);
        mRadioGroup = findViewById(R.id.radioGroup);
        mRbAddWorkDateCurrent = findViewById(R.id.rb_add_work_date_current);
        mRbAddWorkManyDate = findViewById(R.id.rb_add_work_many_date);
        mFlAddWorkTimeMorning = findViewById(R.id.fl_add_work_time_morning);
        mFlAddWorkTimeAfternoon = findViewById(R.id.fl_add_work_time_afternoon);
        mFlAddWorkTimeEverning = findViewById(R.id.fl_add_work_time_everning);
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


}