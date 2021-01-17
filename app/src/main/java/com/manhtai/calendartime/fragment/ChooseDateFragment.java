package com.manhtai.calendartime.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.manhtai.calendartime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ChooseDateFragment extends Fragment {
    private static final String TAG = "AAA";
    private TextView tvChooseDateCurrent;
    private TextView tvChooseDateChoose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvChooseDateCurrent = view.findViewById(R.id.tv_choose_date_current);
        tvChooseDateChoose = view.findViewById(R.id.tv_choose_date_choose);

    }

    public void setDateToField(String s){
        tvChooseDateCurrent.setText("Ngày hiện tại: " + new SimpleDateFormat("EEE, yyyy.MM.dd 'at' HH:mm:ss").format(new Date(String.valueOf(Calendar.getInstance().getTime()))));
        tvChooseDateChoose.setText("Ngày đang chọn: " + s);
        if(String.valueOf(new SimpleDateFormat("yyyy.MM.dd").format(new Date(String.valueOf(Calendar.getInstance().getTime())))).equals(s)){
            tvChooseDateCurrent.setTextColor(Color.parseColor("#4CAF50"));
        }
    }
}