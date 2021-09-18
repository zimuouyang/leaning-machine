package com.leaning_machine.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leaning_machine.R;
import com.leaning_machine.fragment.EnglishCategoryFragment;
import com.leaning_machine.fragment.FluentFragment;
import com.leaning_machine.fragment.FunPuzzleFragment;
import com.leaning_machine.fragment.GrindEarsFragment;
import com.leaning_machine.fragment.LanguageFragment;
import com.leaning_machine.fragment.LearnPhonicsFragment;
import com.leaning_machine.fragment.LoveReadFragment;
import com.leaning_machine.fragment.MainFragment;
import com.leaning_machine.fragment.MathFragment;
import com.leaning_machine.fragment.OthersFragment;
import com.leaning_machine.fragment.PersonCenterFragment;
import com.leaning_machine.fragment.PracticeFrequentlyFragment;
import com.leaning_machine.fragment.ReciteWordsFragment;
import com.leaning_machine.utils.Utils;

/**
 * @author John
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private Fragment fragment;
    private int topIndex = -1;
    private int bottomIndex = 0;
    private TextView[ ] topTextViews;
    private TextView[ ] bottomTextViews;
    private TextView xuePinDu;
    private TextView moErDuo;
    private TextView liuLiShuo;
    private TextView aiYueDu;
    private TextView qinLianXi;
    private TextView beiDanCi;
    private TextView seeMovie;
    private TextView quYiZhi;


    private TextView main;
    private TextView englishText;
    private TextView languageText;
    private TextView mathText;
    private TextView otherText;
    private TextView personCenterText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            fragment = MainFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.content_layout, fragment, "tag");
            transaction.commit();
        }else{
            fragment = getSupportFragmentManager().findFragmentByTag("tag");
        }

        initView();
        Utils.transparencyBar(this);
    }

    private void initView() {
        xuePinDu= findViewById(R.id.pin_du);
        moErDuo= findViewById(R.id.mo_er_duo);
        liuLiShuo= findViewById(R.id.liu_li_shuo);
        aiYueDu= findViewById(R.id.ai_yue_du);
        qinLianXi= findViewById(R.id.qin_lian_xi);
        beiDanCi= findViewById(R.id.bei_dan_ci);
        seeMovie= findViewById(R.id.see_movie);
        quYiZhi= findViewById(R.id.qu_yi_zhi);


        main= findViewById(R.id.main);
        englishText= findViewById(R.id.english);
        mathText= findViewById(R.id.math);
        languageText= findViewById(R.id.language);
        otherText= findViewById(R.id.others);
        personCenterText= findViewById(R.id.person_center);

        topTextViews = new TextView[]{xuePinDu, moErDuo, liuLiShuo, aiYueDu, qinLianXi, beiDanCi, seeMovie, quYiZhi};
        bottomTextViews = new TextView[]{main, englishText, mathText, languageText, otherText, personCenterText};

        xuePinDu.setOnClickListener(this);
        moErDuo.setOnClickListener(this);
        liuLiShuo.setOnClickListener(this);
        aiYueDu.setOnClickListener(this);
        qinLianXi.setOnClickListener(this);
        beiDanCi.setOnClickListener(this);
        seeMovie.setOnClickListener(this);
        quYiZhi.setOnClickListener(this);

        main.setOnClickListener(this);
        englishText.setOnClickListener(this);
        mathText.setOnClickListener(this);
        languageText.setOnClickListener(this);
        otherText.setOnClickListener(this);
        personCenterText.setOnClickListener(this);

        main.setTextColor(Color.RED);

    }

    /**
     * 防止Fragement数据重复加载，采用显示和隐藏的方式
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (fragment != to) {
            fragment = to;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.replace(R.id.content_layout, to).commitAllowingStateLoss();
        }

        changeTopFixView(to);

    }

    private void changeTopFixView(Fragment to) {
        clearTopFixView();
        int currentIndex = -1;
        if (to instanceof LearnPhonicsFragment) {
            currentIndex = 0;
        } else if (to instanceof GrindEarsFragment) {
            currentIndex = 1;
        } else if (to instanceof FluentFragment) {
            currentIndex = 2;
        } else if (to instanceof LoveReadFragment) {
            currentIndex = 3;
        } else if (to instanceof PracticeFrequentlyFragment) {
            currentIndex = 4;
        } else if (to instanceof ReciteWordsFragment) {
            currentIndex = 5;
        }  else if (to instanceof FunPuzzleFragment) {
            currentIndex = 7;
        }
        if (currentIndex != -1) {
            topIndex = currentIndex;
            fixEnglish();
            topTextViews[topIndex].setTextColor(Color.RED);
        }
    }

    private void clearTopFixView() {
        if (topIndex != -1) {
            topTextViews[topIndex].setTextColor(Color.BLACK);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pin_du:
                switchContent(fragment, LearnPhonicsFragment.newInstance());
                break;
            case R.id.mo_er_duo:
                switchContent(fragment, GrindEarsFragment.newInstance());
                break;
            case R.id.liu_li_shuo:
                switchContent(fragment, FluentFragment.newInstance());
                break;
            case R.id.ai_yue_du:
                switchContent(fragment, LoveReadFragment.newInstance());
                break;
            case R.id.qin_lian_xi:
                switchContent(fragment, PracticeFrequentlyFragment.newInstance());
                break;
            case R.id.bei_dan_ci:
                switchContent(fragment, ReciteWordsFragment.newInstance());
                break;
            case R.id.see_movie:
                openApp(getString(R.string.english_movie), this);
                break;
            case R.id.qu_yi_zhi:
                switchContent(fragment, FunPuzzleFragment.newInstance());
                break;

            case R.id.main:
                bottomTextViews[bottomIndex].setTextColor(Color.BLACK);
                bottomIndex = 0;
                main.setTextColor(Color.RED);
                switchContent(fragment, MainFragment.newInstance());
                break;
            case R.id.english:
                fixEnglish();
                switchContent(fragment, EnglishCategoryFragment.newInstance());
                break;
            case R.id.math:
                bottomTextViews[bottomIndex].setTextColor(Color.BLACK);
                bottomIndex = 2;
                mathText.setTextColor(Color.RED);
                switchContent(fragment, MathFragment.newInstance());
                break;
            case R.id.language:
                bottomTextViews[bottomIndex].setTextColor(Color.BLACK);
                bottomIndex = 3;
                languageText.setTextColor(Color.RED);
                switchContent(fragment, LanguageFragment.newInstance());
                break;
            case R.id.others:
                bottomTextViews[bottomIndex].setTextColor(Color.BLACK);
                bottomIndex = 4;
                otherText.setTextColor(Color.RED);
                switchContent(fragment, OthersFragment.newInstance());
                break;
            case R.id.person_center:
                bottomTextViews[bottomIndex].setTextColor(Color.BLACK);
                 bottomIndex= 5;
                personCenterText.setTextColor(Color.RED);
                switchContent(fragment, PersonCenterFragment.newInstance());
                break;
        }
    }

    private void fixEnglish() {
        bottomTextViews[bottomIndex].setTextColor(Color.BLACK);
        bottomIndex = 1;
        englishText.setTextColor(Color.RED);
    }

    public void openApp(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            Toast.makeText(context, "未安装", Toast.LENGTH_LONG).show();
        } else {
            context.startActivity(intent);
        }
    }
}