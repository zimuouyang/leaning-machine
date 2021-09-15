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
public class LoveReadFragment extends BaseFragment implements View.OnClickListener {

    public LoveReadFragment() {
        // Required empty public constructor
    }

    public static LoveReadFragment newInstance() {
        return new LoveReadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love_read, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.read_along).setOnClickListener(this);
        view.findViewById(R.id.reading_pro).setOnClickListener(this);
        view.findViewById(R.id.abc_reading).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.read_along:
                openApp(getString(R.string.english_ai_yue_du_read_long), getActivity());
                break;
            case R.id.reading_pro:
                openApp(getString(R.string.english_ai_yue_du_reading_pro), getActivity());
                break;
            case R.id.abc_reading:
                openApp(getString(R.string.english_ai_yue_du_abc_reading), getActivity());
                break;
            default:
                break;
        }
    }
}