package com.leaning_machine.activity;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.ArrayRes;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.ContentAppAdapter;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.DownloadHistory;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExpandActivity extends BaseActivity {
    TopWithContentLayout topWithContentLayout;
    private List<App> appList;
    private List<DownloadHistory> downloadList;
    private List<String> packageNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDownloadHistory();
    }

    @Override
    public void initView() {
        topWithContentLayout = findViewById(R.id.content);

        topWithContentLayout.setFinishClick(ExpandActivity.this::finish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_expand;
    }


    private void getDownloadHistory() {
        showProgress();
        CommonApiService.instance.getHistories().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BaseDto<List<DownloadHistory>>>() {
            @Override
            public void onNext(BaseDto<List<DownloadHistory>> listBaseDto) {
                super.onNext(listBaseDto);
                dismissWindowDialog();
                if (listBaseDto.getBusinessCode() == 200) {
                   List<DownloadHistory> downloadHistories = listBaseDto.getResult();
                   if (downloadHistories != null && downloadHistories.size() != 0) {
                       downloadList = downloadHistories;
                       detailList();
                       topWithContentLayout.setAppList(appList);
                   }
                } else if (listBaseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(getApplicationContext());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissWindowDialog();
            }
        } );
    }

    private void detailList() {
        if (downloadList == null || downloadList.size() == 0) {
            return;
        }
        appList = new ArrayList<>();
        for (DownloadHistory downloadHistory: downloadList) {
            if (!isExistPackageName(downloadHistory.getPackageName(),R.array.total_array, this)) {
              App app = new App();
              app.setName(downloadHistory.getAppName());
              app.setPackageName(downloadHistory.getPackageName());
              app.setRemote(true);
              app.setImgUrl(downloadHistory.getApkIconFileId());
              appList.add(app);
            }
        }
    }

    private static boolean isExistPackageName(String packageName, @ArrayRes int stringId, Context context) {
        String[] array = context.getResources().getStringArray(stringId);
        for (String string : array) {
            if (packageName.equals(string.trim())) {
                return true;
            }
        }
        return false;
    }



}