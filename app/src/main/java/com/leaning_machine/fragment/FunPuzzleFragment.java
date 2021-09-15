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
public class FunPuzzleFragment extends BaseFragment implements View.OnClickListener {

    public FunPuzzleFragment() {

    }

    public static FunPuzzleFragment newInstance() {
        return new FunPuzzleFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fun_puzzle, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.puzzle_kids).setOnClickListener(this);
        view.findViewById(R.id.play_games).setOnClickListener(this);
        view.findViewById(R.id.molly).setOnClickListener(this);
        view.findViewById(R.id.play_and_learn).setOnClickListener(this);
        view.findViewById(R.id.play_learn).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (getActivity() == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.puzzle_kids:
                openApp(getString(R.string.english_qu_yi_zhi_kids), getActivity());
                break;
            case R.id.play_games:
                openApp(getString(R.string.english_qu_yi_zhi_pbsk), getActivity());
                break;
            case R.id.molly:
                openApp(getString(R.string.english_qu_yi_zhi_molly), getActivity());
                break;
            case R.id.play_and_learn:
                openApp(getString(R.string.english_qu_yi_zhi_engineering), getActivity());
                break;
            case R.id.play_learn:
                openApp(getString(R.string.english_qu_yi_zhi_science), getActivity());
                break;
            default:
                break;
        }
    }
}