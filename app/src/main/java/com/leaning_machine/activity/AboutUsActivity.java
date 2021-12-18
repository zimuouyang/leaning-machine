package com.leaning_machine.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.leaning_machine.R;

import java.util.List;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = AboutUsActivity.class.getSimpleName();

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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                break;
            case R.id.about_app_manage:
                startActivity(new Intent(this, AppManagerActivity.class));
                break;
            case R.id.about_one_clean:
                oneClean();
                break;
            case R.id.play:
                finish();
                break;
        }
    }

    private void oneClean() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);

//        long beforeMem = getAvailMemory(ClearMemoryActivity.this);
//        Log.d(TAG, "-----------before memory info : " + beforeMem);
        int count = 0;
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                Log.d(TAG, "process name : " + appProcessInfo.processName);
                //importance 该进程的重要程度  分为几个级别，数值越低就越重要。
                Log.d(TAG, "importance : " + appProcessInfo.importance);

                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        Log.d(TAG, "It will be killed, package name : " + pkgList[j]);
                        am.killBackgroundProcesses(pkgList[j]);
                        count++;
                    }
                }

            }
        }

    }
}