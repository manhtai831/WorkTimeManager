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
 * Use the {@link TimeMorningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeMorningFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    IGetDataFromFragment iGetDataFromFragment;
    private TextInputEditText mEdtTimeMorning;
    private RadioGroup rgTimeMorning;
    private RadioButton rbTimeMorning0;
    private RadioButton rbTimeMorning4;
    private RadioButton rbTimeMorning3;
    private RadioButton rbTimeMorning5;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public   TimeMorningFragment() {
        // Required empty public constructor
    }

    public static TimeMorningFragment newInstance(String param1, String param2) {
        TimeMorningFragment fragment = new TimeMorningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        return inflater.inflate(R.layout.fragment_time_morning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEdtTimeMorning = view.findViewById(R.id.edt_time_morning);
        rgTimeMorning = view.findViewById(R.id.rg_time_morning);
        rbTimeMorning0 = view.findViewById(R.id.rb_time_morning_0);
        rbTimeMorning4 = view.findViewById(R.id.rb_time_morning_4);
        rbTimeMorning3 = view.findViewById(R.id.rb_time_morning_3);
        rbTimeMorning5 = view.findViewById(R.id.rb_time_morning_5);
        mEdtTimeMorning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length() != 0){
                    iGetDataFromFragment.getTextTimeWorkMorning(s.toString());
                }
            }
        });
        if(mEdtTimeMorning.getText().toString().length() == 0){
            iGetDataFromFragment.getTextTimeWorkMorning("0");
        }
        rgTimeMorning.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()){
                    case R.id.rb_time_morning_0:
                        mEdtTimeMorning.setText("");
                        break;
                    case R.id.rb_time_morning_3:
                        mEdtTimeMorning.setText("3");
                        break;
                    case R.id.rb_time_morning_4:
                        mEdtTimeMorning.setText("4");
                        break;
                    case R.id.rb_time_morning_5:
                        mEdtTimeMorning.setText("5");
                        break;
                    default:
                        mEdtTimeMorning.setText("5");
                }
            }
        });
    }

    public void setTimeToField(String s){
        mEdtTimeMorning.setText(s);
    }
}