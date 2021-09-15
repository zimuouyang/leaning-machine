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
public class EnglishCategoryFragment extends BaseFragment implements View.OnClickListener {

    public EnglishCategoryFragment() {
    }


    public static EnglishCategoryFragment newInstance() {
        return new EnglishCategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english_category, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        String packageName = "";
        switch (v.getId()) {
            case R.id.mo_er_duo:
                packageName = "11";
                break;
            case R.id.xue_pin_du:
                break;
            case R.id.liu_li_shuo:
                break;
            case R.id.ai_yue_du:
                break;
            case R.id.qin_lian_xi:
                break;
            case R.id.bei_dan_ci:
                break;
            case R.id.kan_dian_ying:
                break;
            default:
                break;
        }
        if (!packageName.isEmpty()) {
            openApp(packageName, getActivity());
        }
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.mo_er_duo).setOnClickListener(this::initView);
        view.findViewById(R.id.xue_pin_du).setOnClickListener(this::initView);
        view.findViewById(R.id.liu_li_shuo).setOnClickListener(this::initView);
        view.findViewById(R.id.ai_yue_du).setOnClickListener(this::initView);
        view.findViewById(R.id.qin_lian_xi).setOnClickListener(this::initView);
        view.findViewById(R.id.bei_dan_ci).setOnClickListener(this::initView);
        view.findViewById(R.id.kan_dian_ying).setOnClickListener(this::initView);
    }
}