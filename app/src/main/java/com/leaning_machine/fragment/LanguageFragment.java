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
public class LanguageFragment extends BaseFragment implements View.OnClickListener {

    public LanguageFragment() {

    }


    public static LanguageFragment newInstance() {
        return new LanguageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.chinese).setOnClickListener(this);
        view.findViewById(R.id.chu_zhong_yu_wen).setOnClickListener(this);
        view.findViewById(R.id.bi_shen_zuo_wen).setOnClickListener(this);
        view.findViewById(R.id.study).setOnClickListener(this);
        view.findViewById(R.id.high_yu_wen).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.chinese:
                openApp(getString(R.string.zuo_wen_zi_dian), getActivity());
                break;
            case R.id.chu_zhong_yu_wen:
                openApp(getString(R.string.zuo_wen_middle), getActivity());
                break;
            case R.id.bi_shen_zuo_wen:
                openApp(getString(R.string.zuo_wen_bi_shen), getActivity());
                break;
            case R.id.study:
                openApp(getString(R.string.zuo_wen_study), getActivity());
                break;
            case R.id.high_yu_wen:
                openApp(getString(R.string.zuo_wen_high), getActivity());
                break;
            default:
                break;
        }
    }
}