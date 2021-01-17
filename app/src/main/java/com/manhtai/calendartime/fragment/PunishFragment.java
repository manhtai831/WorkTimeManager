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

import com.google.android.material.textfield.TextInputEditText;
import com.manhtai.calendartime.R;
import com.manhtai.calendartime.until1.IGetDataFromFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PunishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PunishFragment extends Fragment {

    IGetDataFromFragment iGetDataFromFragment;

    private TextInputEditText mEdtFragmentPunish;



    public PunishFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PunishFragment newInstance() {
        PunishFragment fragment = new PunishFragment();
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
        return inflater.inflate(R.layout.fragment_punish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEdtFragmentPunish = view.findViewById(R.id.edt_fragment_punish);
        mEdtFragmentPunish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iGetDataFromFragment.getTextData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                iGetDataFromFragment.getTextData(s.toString());
            }
        });

    }
}