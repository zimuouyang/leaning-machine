package com.leaning_machine.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.common.service.CommonApiService;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppManagerActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = AppManagerActivity.class.getSimpleName();
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApp(1);
    }

    @Override
    public void initView() {
        findViewById(R.id.play).setOnClickListener(this);

        findViewById(R.id.setting_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uninstallApk(AppManagerActivity.this, getString(R.string.package_name_we_chat));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_manager;
    }


    public void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
//        Uri uri = Uri.parse("package:" + packageName);
//        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
//        context.startActivity(intent);
    }

    private void getApp(int page) {
        CommonApiService.instance.getApps(page, 5).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseDto>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseDto pageInfoBaseDto) {
                if (pageInfoBaseDto.getBusinessCode() == Constant.SUCCESS) {
//                    hasData = !pageInfoBaseDto.getResult().isLastPage();
//                    currentPage = pageInfoBaseDto.getResult().getPageNum();
//                    resourceDtos.addAll(pageInfoBaseDto.getResult().getList());
//                    resourceAdapter.setData(resourceDtos);
//                    if (!hasData) {
//                        refreshLayout.finishLoadMoreWithNoMoreData();
//                    } else {
//                        refreshLayout.finishLoadMore();
//                    }
                } else {
//                    refreshLayout.finishLoadMore();
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                finish();
                break;
        }
    }
}