package com.leaning_machine.activity;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.leaning_machine.R;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.LoadingAlertDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = AboutUsActivity.class.getSimpleName();
    private LoadingAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        findViewById(R.id.about_app).setOnClickListener(this);
        findViewById(R.id.about_app_manage).setOnClickListener(this);
        findViewById(R.id.about_one_clean).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.title).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    long[] mHits = new long[3];
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                break;
            case R.id.about_app_manage:
                startActivity(new Intent(this, AppManagerActivity.class));
                break;
            case R.id.play:
                finish();
                break;
            case R.id.title:
                click();
                break;
        }
    }

    private void click() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //获取离开机的时间
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        //单击时间的间隔，以500毫秒为临界值
        if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
            System.out.println("我被三击了。。。");
            findViewById(R.id.about_app_manage).setVisibility(View.VISIBLE);
            //一个三击（双击或多击事件完成），
            //把数组置为空并重写初始化，为下一次三击（双击或多击）做准备
            mHits = null;
            mHits = new long[3];
        }
    }
}