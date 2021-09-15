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
public class MathFragment extends BaseFragment implements View.OnClickListener {

    public MathFragment() {
        // Required empty public constructor
    }

    public static MathFragment newInstance() {
        return new MathFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_math, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.learn_everthing).setOnClickListener(this);
        view.findViewById(R.id.pythagorea).setOnClickListener(this);
        view.findViewById(R.id.ou_la_middle_math).setOnClickListener(this);
        view.findViewById(R.id.moose_math).setOnClickListener(this);
        view.findViewById(R.id.dou_dou_math).setOnClickListener(this);
        view.findViewById(R.id.middle_math).setOnClickListener(this);
        view.findViewById(R.id.high_math).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.learn_everthing:
                openApp(getString(R.string.math_ke_han_xue_yuan), getActivity());
                break;
            case R.id.pythagorea:
                openApp(getString(R.string.math_pythagorea), getActivity());
                break;
            case R.id.ou_la_middle_math:
                openApp(getString(R.string.math_ou_la_math), getActivity());
                break;
            case R.id.moose_math:
                openApp(getString(R.string.math_moose_math), getActivity());
                break;
            case R.id.dou_dou_math:
                openApp(getString(R.string.math_du_du_math), getActivity());
                break;
            case R.id.middle_math:
                openApp(getString(R.string.math_chu_zhong_math), getActivity());
                break;
            case R.id.high_math:
                openApp(getString(R.string.math_high_math), getActivity());
                break;
            default:
                break;
        }
    }
}