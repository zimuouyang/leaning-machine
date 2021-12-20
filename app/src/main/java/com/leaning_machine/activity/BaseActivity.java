package com.leaning_machine.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.base.dto.TerminalDetailDto;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.layout.LoadingAlertDialog;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity {
    private LoadingAlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        getTerminalDetail();
    }

    public abstract void initView();

    public abstract int getLayoutId();

    public void openApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            Toast.makeText(this, "未安装", Toast.LENGTH_LONG).show();
        } else {
            startActivity(intent);
        }
    }

    public void getTerminalDetail() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //今天没有同步过
        if (SharedPreferencesUtils.getString(this, Constant.SYNC_DATE, "").equals(simpleDateFormat.format(new Date()))) {
            return;
        }
        CommonApiService.instance.getTerminalDetail().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseDto<TerminalDetail>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseDto<TerminalDetail> terminalDetailBaseDto) {
                if (terminalDetailBaseDto.getBusinessCode() == 200) {
                    saveData(terminalDetailBaseDto.getResult());
                } else if (terminalDetailBaseDto.getBusinessCode() == 401) {
                   startLogin();
                }
            }

        });
    }

    private void startLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void saveData(TerminalDetail terminalDetail) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (terminalDetail.getInvalidationDate().getTime() < new Date().getTime()) {
            startLogin();
        }
        SharedPreferencesUtils.putString(this, Constant.SYNC_DATE, simpleDateFormat.format(new Date()));
        SharedPreferencesUtils.putLong(this, Constant.MAX_DAY, terminalDetail.getMaxContinuousDay());
        SharedPreferencesUtils.putLong(this, Constant.CURRENT_DAY, terminalDetail.getCurrentContinuousDay());
        SharedPreferencesUtils.putLong(this, Constant.TOTAL_DAY, Utils.daysBetween(terminalDetail.getFirstLoginDate(), new Date()));
        SharedPreferencesUtils.putLong(this, Constant.TERMINAL_ID, terminalDetail.getId());
        SharedPreferencesUtils.putInt(this, Constant.ROLE, terminalDetail.getRole());
    }

    private void showProgress() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingAlertDialog(this);
        dialog.show(getString(R.string.msg_loading));
    }

    private void dismissWindowDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
