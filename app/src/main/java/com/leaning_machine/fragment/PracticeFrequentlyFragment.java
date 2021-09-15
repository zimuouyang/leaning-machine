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
public class PracticeFrequentlyFragment extends BaseFragment {

    public PracticeFrequentlyFragment() {
        // Required empty public constructor
    }

    public static PracticeFrequentlyFragment newInstance() {
        return new PracticeFrequentlyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practice_frequently, container, false);
    }

    @Override
    public void initView(View view) {

    }
}