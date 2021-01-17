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
 * Use the {@link TimeEveningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeEveningFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText mEdtTimeEvening;


    IGetDataFromFragment iGetDataFromFragment;
    private RadioButton rbTimeEvening0;
    private RadioButton rbTimeEvening4;
    private RadioButton rbTimeEvening3;
    private RadioButton rbTimeEvening5;
    private RadioGroup rgTimeEvening;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimeEveningFragment() {
        // Required empty public constructor
    }

    public static TimeEveningFragment newInstance(String param1, String param2) {
        TimeEveningFragment fragment = new TimeEveningFragment();
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
        return inflater.inflate(R.layout.fragment_time_evening, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEdtTimeEvening = view.findViewById(R.id.edt_time_evening);
        rbTimeEvening0 = view.findViewById(R.id.rb_time_evening_0);
        rbTimeEvening4 = view.findViewById(R.id.rb_time_evening_4);
        rbTimeEvening3 = view.findViewById(R.id.rb_time_evening_3);
        rbTimeEvening5 = view.findViewById(R.id.rb_time_evening_5);
        rgTimeEvening = view.findViewById(R.id.rg_time_evening);
        mEdtTimeEvening.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String s1;
//                if(count == 0){
//                    s1 = "4";
//                }else{
//                    s1 = s.toString().trim();
//                }
//                iGetDataFromFragment.getTextTimeWorkEvening(s1);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().trim().length() != 0){
                    iGetDataFromFragment.getTextTimeWorkEvening(s.toString().trim());
                }


            }
        });
        if(mEdtTimeEvening.getText().toString().trim().length() == 0){
            iGetDataFromFragment.getTextTimeWorkEvening("0");
        }
        rgTimeEvening.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()){
                    case R.id.rb_time_evening_0:
                        mEdtTimeEvening.setText("");
                        break;
                    case R.id.rb_time_evening_3:
                        mEdtTimeEvening.setText("3");
                        break;
                    case R.id.rb_time_evening_4:
                        mEdtTimeEvening.setText("4");
                        break;
                    case R.id.rb_time_evening_5:
                        mEdtTimeEvening.setText("5");
                        break;
                    default:
                        mEdtTimeEvening.setText("5");
                }
            }
        });
    }
}