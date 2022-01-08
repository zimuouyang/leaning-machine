package com.leaning_machine.base.application;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.leaning_machine.Constant;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.LearnTime;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.dao.UsedPackageDao;
import com.leaning_machine.db.entity.UsedPackageEntity;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.model.UsingApp;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.SmallUtils;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class BaseApplication extends Application {
    private ScreenBroadcastReceiver mScreenReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        CommonApiService.instance.init(Constant.BASE_URI);
        HttpClient.instance.init(this);
        SmallUtils.init(this);
        String token = SharedPreferencesUtils.getString(this, Constant.TOKEN, "");
        if (!token.isEmpty()) {
            HttpClient.instance.addHeader(Constant.TOKEN, token);
        }
        mScreenReceiver = new ScreenBroadcastReceiver();
        startScreenBroadcastReceiver();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                //全局强制横屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                //全局强制横屏
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;


        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                revertData();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                detailData();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                revertData();
            }
        }
    }

    private void startScreenBroadcastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, filter);
    }


    private void revertData() {
        UsingApp usingApp = SharedPreferencesUtils.getObject(getApplicationContext(), Constant.USING_PACKAGE, UsingApp.class, null);
        if (usingApp != null) {
            usingApp.setStartTime(System.currentTimeMillis());
        }
        SharedPreferencesUtils.putObject(getApplicationContext(), Constant.USING_PACKAGE, usingApp);
    }

    private void detailData() {
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
                UsedPackageEntity usedPackageEntity = usedPackageDao.getUsedTimeEntity(Utils.getDateString());

                //如果是跨天,就记录今天开始到当前时间
                if (usingApp.getStartTime() < Utils.getTodayZero().getTime()) {
                    if (usedPackageEntity != null) {
                        usedPackageEntity.setTime((System.currentTimeMillis() - Utils.getTodayZero().getTime()) / 1000);
                        usedPackageEntity.setLastUseTime(System.currentTimeMillis() / 1000);
                    } else {
                        usedPackageEntity = new UsedPackageEntity();
                        usedPackageEntity.setDate(Utils.getDateString());
                        //记录使用时长
                        usedPackageEntity.setTime((System.currentTimeMillis() - Utils.getTodayZero().getTime()) / 1000);
                        //记录上次使用时长
                        usedPackageEntity.setLastUseTime(System.currentTimeMillis() / 1000);
                    }
                } else {
                    if (usedPackageEntity != null) {
                        usedPackageEntity.setTime((System.currentTimeMillis() - usingApp.getStartTime()) / 1000);
                        usedPackageEntity.setLastUseTime(System.currentTimeMillis() / 1000);
                    } else {
                        usedPackageEntity = new UsedPackageEntity();
                        usedPackageEntity.setDate(Utils.getDateString());
                        //记录使用时长
                        usedPackageEntity.setTime((System.currentTimeMillis() - usingApp.getStartTime()) / 1000);
                        //记录上次使用时长
                        usedPackageEntity.setLastUseTime(System.currentTimeMillis() / 1000);
                    }
                }

                usedPackageDao.insertUsedTime(usedPackageEntity);

                //插入总的数据库并上传
                subscriber.onNext(updateLearnTime(usingApp));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).flatMap(new Func1<List<LearnTime>, Observable<BaseDto>>() {
            @Override
            public Observable<BaseDto> call(List<LearnTime> learnTimes) {
                return CommonApiService.instance.addLearnTime(learnTimes);
            }
        }).observeOn(Schedulers.io()).subscribe(new DefaultObserver<BaseDto>() {
            @Override
            public void onNext(BaseDto baseDto) {
                super.onNext(baseDto);
                if (baseDto != null && baseDto.getBusinessCode() == 200) {
                    GlobalDatabase.getInstance(getApplicationContext()).usedTimeDao().deleteAll();
                } else if (baseDto != null && baseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(getApplicationContext());
                }
                Log.d("BaseActivity", baseDto == null ? "Response is null" : baseDto.getBusinessCode() + " : " + baseDto.getMessage());
            }
        });
    }

    private List<LearnTime> updateLearnTime(UsingApp usingApp) {
        //假如说没有跨天
        if (usingApp.getStartTime() >= Utils.getTodayZero().getTime()) {
            UsedTimeEntity usedTimeEntity = GlobalDatabase.getInstance(this).usedTimeDao().getUsedTimeEntity(Utils.getDateString());
            if (usedTimeEntity == null) {
                usedTimeEntity = new UsedTimeEntity();
                usedTimeEntity.setDate(Utils.getDateString());
            }
            Utils.addTime(usedTimeEntity, usingApp.getPackageName(), (System.currentTimeMillis() - usingApp.getStartTime()) / 1000, usingApp.getStartTime(), this);
            GlobalDatabase.getInstance(this).usedTimeDao().insertUsedTime(usedTimeEntity);
        } else {
            //跨天逻辑

            //第一天的数据
            Date startDate = new Date(usingApp.getStartTime());
            long firstDayEndTime =  Utils.getDateEnd(startDate).getTime();
            collectTime(startDate, usingApp.getPackageName(),  (Utils.getDateEnd(startDate).getTime() - usingApp.getStartTime()) / 1000, usingApp.getStartTime());

            //跨天全部设置为24小时
            long crossDay = (System.currentTimeMillis() - firstDayEndTime) / Constant.ONE_DAY;
            for (int i = 0; i < crossDay; i++) {
                collectTime(Utils.getAfterDate(startDate, i + 1), usingApp.getPackageName(), Constant.ONE_DAY / 1000, Utils.getDateZero(Utils.getAfterDate(startDate, i + 1)).getTime());
            }

            //处理今天的
            collectTime(new Date(), usingApp.getPackageName(), (System.currentTimeMillis() - Utils.getTodayZero().getTime()) / 1000, Utils.getTodayZero().getTime());

        }


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
        Log.d("zzz", learnTimes.toString());
        return learnTimes;
    }

    private void collectTime(Date startDate, String packageName, long time, long startTime) {
        UsedTimeEntity usedTimeEntity = GlobalDatabase.getInstance(this).usedTimeDao().getUsedTimeEntity(Utils.getDateString(startDate));
        if (usedTimeEntity == null) {
            usedTimeEntity = new UsedTimeEntity();
            usedTimeEntity.setDate(Utils.getDateString(startDate));
        }
        Utils.addTime(usedTimeEntity, packageName, time, startTime, this);
        GlobalDatabase.getInstance(this).usedTimeDao().insertUsedTime(usedTimeEntity);
    }

}
