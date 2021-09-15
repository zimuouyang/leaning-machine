package com.leaning_machine.fragment;

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
public class LearnPhonicsFragment extends BaseFragment {

    public LearnPhonicsFragment() {
    }

    public static LearnPhonicsFragment newInstance() {
        return new LearnPhonicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_learn_phonics, container, false);
    }

    @Override
    public void initView(View view) {

    }
}