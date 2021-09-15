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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mo_er_duo:
               goToFragment(GrindEarsFragment.newInstance());
                break;
            case R.id.xue_pin_du:
                goToFragment(LearnPhonicsFragment.newInstance());
                break;
            case R.id.liu_li_shuo:
                goToFragment(FluentFragment.newInstance());
                break;
            case R.id.ai_yue_du:
                goToFragment(LoveReadFragment.newInstance());
                break;
            case R.id.qin_lian_xi:
                goToFragment(PracticeFrequentlyFragment.newInstance());
                break;
            case R.id.bei_dan_ci:
                goToFragment(ReciteWordsFragment.newInstance());
                break;
            case R.id.qu_yi_zhi:
                goToFragment(FunPuzzleFragment.newInstance());
                break;
            default:
                break;
        }
    }

    private void goToFragment(Fragment fragment) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.switchContent(this, fragment);
        }
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.mo_er_duo).setOnClickListener(this);
        view.findViewById(R.id.xue_pin_du).setOnClickListener(this);
        view.findViewById(R.id.liu_li_shuo).setOnClickListener(this);
        view.findViewById(R.id.ai_yue_du).setOnClickListener(this);
        view.findViewById(R.id.qin_lian_xi).setOnClickListener(this);
        view.findViewById(R.id.bei_dan_ci).setOnClickListener(this);
        view.findViewById(R.id.qu_yi_zhi).setOnClickListener(this);
    }
}