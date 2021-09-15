package com.leaning_machine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaning_machine.R;

/**
 * @author John
 * @date 2021/9/14
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    public MainFragment() {
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.ding_ding).setOnClickListener(this);
        view.findViewById(R.id.we_chat).setOnClickListener(this);
        view.findViewById(R.id.qq).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.ding_ding:
                openApp(getString(R.string.go_class_ding_ding), getActivity());
                break;
            case R.id.we_chat:
                openApp(getString(R.string.go_class_we_chat), getActivity());
                break;
            case R.id.qq:
                openApp(getString(R.string.go_class_qq), getActivity());
                break;
            default:
                break;
        }
    }
}