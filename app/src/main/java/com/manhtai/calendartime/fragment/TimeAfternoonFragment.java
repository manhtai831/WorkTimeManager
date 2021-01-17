package com.manhtai.calendartime.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.manhtai.calendartime.R;
import com.manhtai.calendartime.until1.IGetDataFromFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeAfternoonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeAfternoonFragment extends Fragment {

    private TextInputEditText mEdtTmeAfternoon;
    private RadioGroup rgTimeAfternoon;
    private RadioButton rbTimeAfternoon0;
    private RadioButton rbTimeAfternoon4;
    private RadioButton rbTimeAfternoon3;
    private RadioButton rbTimeAfternoon5;


    IGetDataFromFragment iGetDataFromFragment;
    public TimeAfternoonFragment() {
        // Required empty public constructor
    }

    public static TimeAfternoonFragment newInstance(String param1, String param2) {
        TimeAfternoonFragment fragment = new TimeAfternoonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iGetDataFromFragment = (IGetDataFromFragment) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_afternoon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEdtTmeAfternoon = view.findViewById(R.id.edt_tme_afternoon);
        rgTimeAfternoon = view.findViewById(R.id.rg_time_afternoon);
        rbTimeAfternoon0 = view.findViewById(R.id.rb_time_afternoon_0);
        rbTimeAfternoon4 = view.findViewById(R.id.rb_time_afternoon_4);
        rbTimeAfternoon3 = view.findViewById(R.id.rb_time_afternoon_3);
        rbTimeAfternoon5 = view.findViewById(R.id.rb_time_afternoon_5);

        mEdtTmeAfternoon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().trim().length() != 0){
                    iGetDataFromFragment.getTextTimeWorkAfternoon(s.toString());
                }
            }
        });
        if(mEdtTmeAfternoon.getText().toString().trim().length() == 0){
            iGetDataFromFragment.getTextTimeWorkAfternoon("0");

        }
        rgTimeAfternoon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()){
                    case R.id.rb_time_afternoon_0:
                        mEdtTmeAfternoon.setText("");
                        break;
                    case R.id.rb_time_afternoon_3:
                        mEdtTmeAfternoon.setText("3");
                        break;
                    case R.id.rb_time_afternoon_4:
                        mEdtTmeAfternoon.setText("4");
                        break;
                    case R.id.rb_time_afternoon_5:
                        mEdtTmeAfternoon.setText("5");
                        break;
                    default:
                        mEdtTmeAfternoon.setText("3");
                }
            }
        });


    }
}