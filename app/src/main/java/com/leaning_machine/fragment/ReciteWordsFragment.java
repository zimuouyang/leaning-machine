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
public class ReciteWordsFragment extends BaseFragment implements View.OnClickListener {

    public ReciteWordsFragment() {
    }

    public static ReciteWordsFragment newInstance() {
        return new ReciteWordsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recite_words, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.zhi_neng_dan_ci).setOnClickListener(this);
        view.findViewById(R.id.bai_ci_zhan).setOnClickListener(this);
        view.findViewById(R.id.three_danci).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.zhi_neng_dan_ci:
                openApp(getString(R.string.english_bei_dan_ci_ai_bei_dan_ci), getActivity());
                break;
            case R.id.bai_ci_zhan:
                openApp(getString(R.string.english_bei_dan_ci_bai_ci_zhan), getActivity());
                break;
            case R.id.three_danci:
                openApp(getString(R.string.english_bei_dan_ci_three_d), getActivity());
                break;
            default:
                break;
        }
    }
}