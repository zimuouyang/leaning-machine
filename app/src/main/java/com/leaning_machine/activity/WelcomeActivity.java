package com.leaning_machine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;


import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

/**
 * @author John
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initData();

        AppOpsManager appOps = (AppOpsManager)getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;
        if (!granted) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivityForResult(intent, 0);
        }
        Utils.transparencyBar(this);

        findViewById(R.id.go).setOnClickListener(this);
        findViewById(R.id.go_english).setOnClickListener(this);
        findViewById(R.id.go_language).setOnClickListener(this);
        findViewById(R.id.go_others).setOnClickListener(this);
        findViewById(R.id.go_math).setOnClickListener(this);
    }

    private void initData() {
        if (!SharedPreferencesUtils.getBoolean(this, Constant.INIT, false)) {
            SharedPreferencesUtils.putBoolean(this, Constant.INIT, true);
            SharedPreferencesUtils.putLong(this, Constant.TIME, System.currentTimeMillis());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
                startIntent(0);
                break;
            case R.id.go_english:
                startIntent(1);
                break;
            case R.id.go_language:
                startIntent(3);
                break;
            case R.id.go_others:
                startIntent(4);
                break;
            case R.id.go_math:
                startIntent(2);
                break;
        }
    }

    private void startIntent(int index) {
        Intent i=new Intent();
        i.setClass(WelcomeActivity.this, MainActivity.class);
        i.putExtra("id",index);
        startActivity(i);
    }
}