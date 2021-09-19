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
public class OthersFragment extends BaseFragment implements View.OnClickListener {

    public OthersFragment() {
    }

    public static OthersFragment newInstance() {
        return new OthersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
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
            case R.id.chu_zhong_wuli:
                openApp(getString(R.string.others_middle_wu_li), getActivity());
                break;
            case R.id.chu_zhong_huaxue:
                openApp(getString(R.string.others_middle_hua_xue), getActivity());
                break;
            case R.id.middle_sheng_wu:
                openApp(getString(R.string.others_middle_sheng_wu), getActivity());
                break;
            case R.id.middle_di_li:
                openApp(getString(R.string.others_middle_geography), getActivity());
                break;
            case R.id.middle_history:
                openApp(getString(R.string.others_middle_history), getActivity());
                break;
            case R.id.middle_zheng_zhi:
                openApp(getString(R.string.others_middle_zheng_zi), getActivity());
                break;
            case R.id.khan_kids:
                openApp(getString(R.string.english_mo_er_duo_khankids), getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.chu_zhong_wuli).setOnClickListener(this);
        view.findViewById(R.id.chu_zhong_huaxue).setOnClickListener(this);
        view.findViewById(R.id.middle_sheng_wu).setOnClickListener(this);
        view.findViewById(R.id.middle_di_li).setOnClickListener(this);
        view.findViewById(R.id.middle_history).setOnClickListener(this);
        view.findViewById(R.id.middle_zheng_zhi).setOnClickListener(this);
        view.findViewById(R.id.khan_kids).setOnClickListener(this);
    }
}