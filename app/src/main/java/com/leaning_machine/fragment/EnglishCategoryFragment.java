package com.leaning_machine.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaning_machine.R;

public class EnglishCategoryFragment extends BaseFragment implements View.OnClickListener {

    public EnglishCategoryFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnglishCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnglishCategoryFragment newInstance(String param1, String param2) {
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