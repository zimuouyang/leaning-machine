package com.leaning_machine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.leaning_machine.R;
import com.leaning_machine.activity.MainActivity;

/**
 *
 * @author John
 * @date 2021/9/15
 */
public class PracticeFrequentlyFragment extends BaseFragment implements View.OnClickListener {

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
        View view = inflater.inflate(R.layout.fragment_practice_frequently, container, false);
        initView(view);
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.duo_lin_guo:
            case R.id.duo_lingo:
                openApp(getString(R.string.english_qin_lian_xi_duo_lin_guo), getActivity());
                break;
            case R.id.ef_hello:
                openApp(getString(R.string.english_qin_lian_xi_ef_hello), getActivity());
                break;
            case R.id.middle_english:
                openApp(getString(R.string.english_qin_lian_xi_middle_english), getActivity());
                break;
            case R.id.high_english:
                openApp(getString(R.string.english_qin_lian_xi_high_english), getActivity());
                break;
            case R.id.new_english:
                openApp(getString(R.string.english_qin_lian_xi_new_gai_nian), getActivity());
                break;
            case R.id.civia_machine:
                openApp(getString(R.string.english_qin_lian_xi_civia), getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.duo_lin_guo).setOnClickListener(this::initView);
        view.findViewById(R.id.duo_lingo).setOnClickListener(this::initView);
        view.findViewById(R.id.ef_hello).setOnClickListener(this::initView);
        view.findViewById(R.id.middle_english).setOnClickListener(this::initView);
        view.findViewById(R.id.high_english).setOnClickListener(this::initView);
        view.findViewById(R.id.new_english).setOnClickListener(this::initView);
        view.findViewById(R.id.civia_machine).setOnClickListener(this::initView);
    }
}