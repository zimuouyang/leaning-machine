package com.leaning_machine.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeView;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.Announcement;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.base.dto.TerminalSign;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.ComplexViewMF;
import com.leaning_machine.layout.LoadingAlertDialog;
import com.leaning_machine.layout.PasswordDialog;
import com.leaning_machine.model.VoiceType;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author John
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    ImageView goClass;
    ImageView goExtension;
    ImageView goRecord;
    ImageView goEnglish;
    ImageView goMath;
    ImageView goLanguage;
    ImageView goTextBook;
    TextView task;
    MarqueeView marqueeView;
    List<Announcement> datas;
    ComplexViewMF marqueeFactory;
    private long leftDay;
    private LoadingAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
    }

    @Override
    public void initView() {
        goClass = findViewById(R.id.welcome_go_class);
        goExtension = findViewById(R.id.welcome_extension);
        goRecord = findViewById(R.id.welcome_check_record);
        goEnglish = findViewById(R.id.welcome_english);
        goMath = findViewById(R.id.welcome_math);
        goLanguage = findViewById(R.id.welcome_language);
        goTextBook = findViewById(R.id.welcome_textbook);
        marqueeView = findViewById(R.id.task);

        goExtension.setOnClickListener(this);
        goRecord.setOnClickListener(this);
        goEnglish.setOnClickListener(this);
        goMath.setOnClickListener(this);
        goLanguage.setOnClickListener(this);
        goTextBook.setOnClickListener(this);
        findViewById(R.id.one_clean).setOnClickListener(this);

        datas = new ArrayList<>();
        marqueeView.setVisibility(View.GONE);
    }

    private void startFling() {
        marqueeFactory = new ComplexViewMF(this);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();

    }
    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onResume() {
        super.onResume();
        datas = new ArrayList<>();
        getTerminalDetail();
        getAnnouncement();
    }

    public void getTerminalDetail() {
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
                    Utils.goToLogin(getApplicationContext());
                }
            }
        });
    }

    private void saveData(TerminalDetail terminalDetail) {
        SharedPreferencesUtils.putString(this, Constant.SYNC_DATE, Utils.getDateString());
        SharedPreferencesUtils.putString(this, Constant.USER_NAME, terminalDetail.getName());
        SharedPreferencesUtils.putLong(this, Constant.MAX_DAY, terminalDetail.getMaxContinuousDay());
        SharedPreferencesUtils.putLong(this, Constant.CURRENT_DAY, terminalDetail.getCurrentContinuousDay());
        SharedPreferencesUtils.putLong(this, Constant.TOTAL_DAY, terminalDetail.getTotalDay() > 0 ? terminalDetail.getTotalDay() : 1);
        SharedPreferencesUtils.putLong(this, Constant.TERMINAL_ID, terminalDetail.getId());
        SharedPreferencesUtils.putInt(this, Constant.ROLE, terminalDetail.getRole());
        SharedPreferencesUtils.putString(this, Constant.TERMINAL_PWD, terminalDetail.getTerminalSign());
        leftDay = Utils.daysBetween( new Date(), terminalDetail.getInvalidationDate()) + 1;
        if (leftDay > 0 && leftDay <= 7) {
            Announcement announcement = new Announcement();
            announcement.setContent("账户有效期还有：" + leftDay + "天");
            datas.add(announcement);
        }
        if (datas.size() == 0) {
            marqueeView.setVisibility(View.GONE);
        } else {
            marqueeView.setVisibility(View.VISIBLE);
            startFling();
        }
        SharedPreferencesUtils.putLong(this, Constant.LEFT_DATE, leftDay);

    }

    private void getAnnouncement() {
        CommonApiService.instance.getAnnouncement().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseDto<Announcement>>() {
            @Override
            public void call(BaseDto<Announcement> announcementBaseDto) {
                if (announcementBaseDto.getBusinessCode() == 200) {
                    Announcement announcement = announcementBaseDto.getResult();
                    if (announcement != null) {
                        datas.add(announcement);
                    }
                    if (datas.size() == 0) {
                        marqueeView.setVisibility(View.GONE);
                    } else {
                        marqueeView.setVisibility(View.VISIBLE);
                        startFling();
                    }
                } else if (announcementBaseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(WelcomeActivity.this);
                }
            }


        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
//                Utils.goToLogin(getApplicationContext());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcome_go_class:
                if (SharedPreferencesUtils.getInt(this, Constant.ROLE, 0) == 1) {
                    startActivity(new Intent(this, GoClassActivity.class));
                } else {
                    PasswordDialog passwordDialog = new PasswordDialog.Builder().context(this).type(VoiceType.PASSWORD).build();
                    passwordDialog.setPasswordClick(new PasswordDialog.PasswordClick() {
                        @Override
                        public void onSuccess() {
                            startActivity(new Intent(WelcomeActivity.this, GoClassActivity.class));
                        }
                    });
                    passwordDialog.show();
                }
                break;
            case R.id.welcome_extension:
                startActivity(new Intent(this, ExpandActivity.class));
                break;
            case R.id.welcome_check_record:
                startActivity(new Intent(this, ReadRecordActivity.class));
                break;
            case R.id.welcome_english:
                startActivity(new Intent(this, EnglishActivity.class));
                break;
            case R.id.welcome_math:
                startActivity(new Intent(this, MathActivity.class));
                break;
            case R.id.welcome_language:
                startActivity(new Intent(this, LanguageActivity.class));
                break;
            case R.id.welcome_textbook:
                startActivity(new Intent(this, TextBookActivity.class));
                break;
            case R.id.one_clean:
                oneClean();
                break;
        }
    }

    private void oneClean() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();

        int count = 0;
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);

                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        Log.d("WelcomeActivity", "It will be killed, package name : " + pkgList[j]);
                        am.killBackgroundProcesses(pkgList[j]);
                        count++;
                    }
                }

            }
        }

        showProgress();
        Observable.just(Boolean.TRUE).delay(4, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                dismissWindowDialog();
            }
        });

    }

    public void showProgress() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingAlertDialog(this);
        dialog.show(getString(R.string.msg_clean));
    }

    public void dismissWindowDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}