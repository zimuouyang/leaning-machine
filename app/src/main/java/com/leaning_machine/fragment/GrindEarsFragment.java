package com.leaning_machine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaning_machine.R;

/**
 * @author John
 * @date 2021/9/15
 */
public class GrindEarsFragment extends BaseFragment implements View.OnClickListener {

    public GrindEarsFragment() {

    }

    public static GrindEarsFragment newInstance() {
        return new GrindEarsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grind_ears, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.kids).setOnClickListener(this);
        view.findViewById(R.id.dong_ting_hui_ben).setOnClickListener(this);
        view.findViewById(R.id.raz).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.kids:
                openApp(getString(R.string.english_mo_er_duo_khankids), getActivity());
                break;
            case R.id.dong_ting_hui_ben:
                openApp(getString(R.string.english_mo_er_duo_dong_ting_hui_ben), getActivity());
                break;
            case R.id.raz:
                openApp(getString(R.string.english_mo_er_duo_raz), getActivity());
                break;
            default:
                break;
        }
    }
}