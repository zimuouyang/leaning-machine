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
public class LearnPhonicsFragment extends BaseFragment implements View.OnClickListener {

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
        View view = inflater.inflate(R.layout.fragment_learn_phonics, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.speed_phonics).setOnClickListener(this);
        view.findViewById(R.id.abc_kides).setOnClickListener(this);
        view.findViewById(R.id.starfall).setOnClickListener(this);
        view.findViewById(R.id.starfall_learn_to_read).setOnClickListener(this);
        view.findViewById(R.id.sight_words).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.speed_phonics:
                openApp(getString(R.string.english_pin_du_speedphonics), getActivity());
                break;
            case R.id.abc_kides:
                openApp(getString(R.string.english_pin_du_abc_kids), getActivity());
                break;
            case R.id.starfall:
                openApp(getString(R.string.english_pin_du_starfall_abcs), getActivity());
                break;
            case R.id.starfall_learn_to_read:
                openApp(getString(R.string.english_pin_du_starfall_ltr), getActivity());
                break;
            case R.id.sight_words:
                openApp(getString(R.string.english_pin_du_sightwords), getActivity());
                break;
            default:
                break;

        }
    }
}