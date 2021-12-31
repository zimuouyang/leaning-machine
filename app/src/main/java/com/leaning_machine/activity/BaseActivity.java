package com.leaning_machine.activity;

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
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.dao.UsedPackageDao;
import com.leaning_machine.db.entity.UsedPackageEntity;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.LoadingAlertDialog;
import com.leaning_machine.model.UsingApp;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity {
    private LoadingAlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //证明有正在使用的应用
        UsingApp usingApp = SharedPreferencesUtils.getObject(getApplicationContext(), Constant.USING_PACKAGE, UsingApp.class, null);

        if (usingApp != null) {
            //保存数据库
            saveAppTime(usingApp);
        }
    }


    private void saveAppTime(UsingApp usingApp) {
        Observable.create(new Observable.OnSubscribe<List<LearnTime>>() {
            @Override
            public void call(Subscriber<? super List<LearnTime>> subscriber) {
                //插入当天应用使用的数据库
                UsedPackageDao usedPackageDao = GlobalDatabase.getInstance(getApplicationContext()).usedPackageDao();
                UsedPackageEntity usedPackageEntity = usedPackageDao.getUsedTimeEntity(Utils.getDateString(), usingApp.getPackageName());
                if (usedPackageEntity != null) {
                    usedPackageEntity.setTime(usedPackageEntity.getTime() + (System.currentTimeMillis() - usingApp.getStartTime()) / 1000);
                    usedPackageEntity.setLastUseTime(System.currentTimeMillis() / 1000);
                } else {
                    usedPackageEntity = new UsedPackageEntity();
                    usedPackageEntity.setDate(Utils.getDateString());
                    //单个应用使用的保存
                    usedPackageEntity.setPackageName(usingApp.getPackageName());
                    //记录使用时长
                    usedPackageEntity.setTime((System.currentTimeMillis() - usingApp.getStartTime()) / 1000);
                    //记录上次使用时长
                    usedPackageEntity.setLastUseTime(System.currentTimeMillis() / 1000);
                }
                usedPackageDao.insertUsedTime(usedPackageEntity);


                //插入总的数据库并上传

                subscriber.onNext(updateLearnTime(usingApp));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).flatMap(new Func1<List<LearnTime>, Observable<BaseDto>>() {
            @Override
            public Observable<BaseDto> call(List<LearnTime> learnTimes) {
                SharedPreferencesUtils.putObject(getApplicationContext(), Constant.USING_PACKAGE, null);
                return CommonApiService.instance.addLearnTime(learnTimes);
            }
        }).observeOn(Schedulers.io()).subscribe(new DefaultObserver<BaseDto>() {
            @Override
            public void onNext(BaseDto baseDto) {
                super.onNext(baseDto);
                if (baseDto != null && baseDto.getBusinessCode() == 200) {
                    GlobalDatabase.getInstance(BaseActivity.this).usedTimeDao().deleteAll();
                } else if (baseDto != null && baseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(getApplicationContext());
                }
                Log.d("BaseActivity", baseDto == null ? "Response is null" : baseDto.getBusinessCode() + " : " + baseDto.getMessage());
            }
        });
    }

    private List<LearnTime> updateLearnTime(UsingApp usingApp) {
        //查询当天的
        UsedTimeEntity usedTimeEntity = GlobalDatabase.getInstance(this).usedTimeDao().getUsedTimeEntity(Utils.getDateString());
        if (usedTimeEntity == null) {
            usedTimeEntity = new UsedTimeEntity();
            usedTimeEntity.setDate(Utils.getDateString());
        }
        Utils.addTime(usedTimeEntity, usingApp, this);
        GlobalDatabase.getInstance(this).usedTimeDao().insertUsedTime(usedTimeEntity);


        List<UsedTimeEntity> usedTimeEntities = GlobalDatabase.getInstance(this).usedTimeDao().getAll();

        List<LearnTime> learnTimes = new ArrayList<>();
        if (usedTimeEntities != null) {
            for (UsedTimeEntity timeEntity : usedTimeEntities) {
                LearnTime learnTime = new LearnTime();
                learnTime.setCreateDate(timeEntity.getDate());
                learnTime.setTotal(timeEntity.getTotalLength());
                learnTime.setSpelling(timeEntity.getPinDuLength());
                learnTime.setGrindEars(timeEntity.getErDuoLength());
                learnTime.setFluent(timeEntity.getQuLeZhiLength());
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
        return learnTimes;
    }

    public abstract void initView();

    public abstract int getLayoutId();

    public void openApp(String packageName) {
        PackageManager packageManager = getPackageManager();
        if (!Utils.checkAppInstalled(this, packageName)) {
            Toast.makeText(this, "未安装", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        }
    }

    public void showProgress() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingAlertDialog(this);
        dialog.show(getString(R.string.msg_loading));
    }

    public void dismissWindowDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
