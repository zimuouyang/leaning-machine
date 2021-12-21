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
import com.leaning_machine.base.dto.LearnTime;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.base.dto.TerminalDetailDto;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.dao.UsedPackageDao;
import com.leaning_machine.db.entity.UsedPackageEntity;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.layout.LoadingAlertDialog;
import com.leaning_machine.model.UsingApp;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("zzz", "onresume");
        //证明有正在使用的应用
        UsingApp usingApp = SharedPreferencesUtils.getObject(this, Constant.USING_PACKAGE, UsingApp.class, null);

        if (usingApp != null) {
            //保存数据库
            Log.d("zzz", usingApp.toString());
            saveAppTime(usingApp);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("zzz", "onRestart");
    }

    private void saveAppTime(UsingApp usingApp) {
        Observable.create(new Observable.OnSubscribe<UsingApp>() {
            @Override
            public void call(Subscriber<? super UsingApp> subscriber) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                UsedPackageDao usedPackageDao = GlobalDatabase.getInstance(getApplicationContext()).usedPackageDao();
                UsedPackageEntity usedPackageEntity = usedPackageDao.getUsedTimeEntity(simpleDateFormat.format(new Date()), usingApp.getPackageName());
                if (usedPackageEntity != null) {
                    usedPackageEntity.setTime(usedPackageEntity.getTime() + (System.currentTimeMillis() - usingApp.getStartTime()) / 1000 );
                } else {
                    usedPackageEntity = new UsedPackageEntity();
                    usedPackageEntity.setDate(simpleDateFormat.format(new Date()));
                    usedPackageEntity.setPackageName(usingApp.getPackageName());
                    usedPackageEntity.setTime((System.currentTimeMillis() - usingApp.getStartTime()) / 1000 );
                }
                usedPackageDao.insertUsedTime(usedPackageEntity);
                Log.d("zzz", usedPackageDao.getAll().toString());
                subscriber.onNext(usingApp);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Action1<UsingApp>() {
            @Override
            public void call(UsingApp usingApp) {

            }
        });
    }

    private void updateLearnTime(UsingApp usingApp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<UsedTimeEntity> usedTimeEntities = GlobalDatabase.getInstance(this).usedTimeDao().getAll();
        List<LearnTime> learnTimes = new ArrayList<>();
        if (usedTimeEntities != null) {
            for (UsedTimeEntity timeEntity: usedTimeEntities) {
                LearnTime learnTime = new LearnTime();
                learnTime.setCreateDate(timeEntity.getDate());
                learnTime.setTotal(timeEntity.getTotalLength());
                learnTime.setSpelling(timeEntity.getPinDuLength());
                learnTime.setGrindEars(timeEntity.getErDuoLength());
                learnTime.setFluent(timeEntity.getLiuLiLength());
                learnTime.setLoveRead(timeEntity.getYueDuLength());
                learnTime.setPracticeFrequently(timeEntity.getLianXiLength());
                learnTime.setReciteWords(timeEntity.getDanCiLength());
                learnTime.setLanguage(timeEntity.getLanguageLength());
                learnTime.setMath(timeEntity.getMathLength());
                learnTime.setOthers(timeEntity.getOtherLength());
                learnTime.setUserId(SharedPreferencesUtils.getLong(getApplicationContext(), Constant.TERMINAL_ID, 0));
                learnTimes.add(learnTime);
            }
        }
        LearnTime learnTime = new LearnTime();
        learnTime.setUserId(SharedPreferencesUtils.getLong(getApplicationContext(), Constant.TERMINAL_ID, 0));
        learnTime.setCreateDate(simpleDateFormat.format(new Date()));
    }

    public abstract void initView();

    public abstract int getLayoutId();

    public void openApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        if (!Utils.checkAppInstalled(this, packageName)) {
            Toast.makeText(this, "未安装", Toast.LENGTH_LONG).show();
            return;
        }
        UsingApp usingApp = new UsingApp();
        usingApp.setStartTime(System.currentTimeMillis());
        usingApp.setPackageName(packageName);
        SharedPreferencesUtils.putObject(getApplicationContext(), Constant.USING_PACKAGE, usingApp);
        Log.d("zzz", usingApp.toString());
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
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
        SharedPreferencesUtils.putLong(this, Constant.TOTAL_DAY, Utils.daysBetween(terminalDetail.getFirstLoginDate(), new Date()) + 1);
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
