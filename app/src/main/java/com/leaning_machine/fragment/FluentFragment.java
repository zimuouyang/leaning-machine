package com.leaning_machine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaning_machine.R;

/**
 *
 * @author John
 * @date 2021/9/15
 */
public class FluentFragment extends BaseFragment implements View.OnClickListener {

    public FluentFragment() {
    }


    public static FluentFragment newInstance() {
        return  new FluentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fluent, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.qu_pei_yin).setOnClickListener(this);
        view.findViewById(R.id.liu_li_shuo).setOnClickListener(this);
        view.findViewById(R.id.ko_yu_machine).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.qu_pei_yin:
                openApp(getString(R.string.english_liu_li_shuo_pei_yin), getActivity());
                break;
            case R.id.liu_li_shuo:
                openApp(getString(R.string.english_liu_li_shuo_liu_li_shuo), getActivity());
                break;
            case R.id.ko_yu_machine:
                openApp(getString(R.string.english_liu_li_shuo_ko_yu), getActivity());
                break;
            default:
                break;
        }
    }
}