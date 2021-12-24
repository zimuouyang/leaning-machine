package com.leaning_machine.activity;

import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.Announcement;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.TerminalSign;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.PasswordDialog;
import com.leaning_machine.model.VoiceType;
import com.leaning_machine.utils.SharedPreferencesUtils;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        task = findViewById(R.id.task);

        goClass.setOnClickListener(this);
        goExtension.setOnClickListener(this);
        goRecord.setOnClickListener(this);
        goEnglish.setOnClickListener(this);
        goMath.setOnClickListener(this);
        goLanguage.setOnClickListener(this);
        goTextBook.setOnClickListener(this);
        task.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAnnouncement();
        getLatestSign();
    }

    private void getAnnouncement() {
        CommonApiService.instance.getAnnouncement().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseDto<Announcement>>() {
            @Override
            public void call(BaseDto<Announcement> announcementBaseDto) {
                if (announcementBaseDto.getBusinessCode() == 200) {
                    Announcement announcement = announcementBaseDto.getResult();
                    if (announcement != null) {
                        task.setVisibility(View.VISIBLE);
                        task.setText(announcement.getContent());
                    } else {
                        task.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getLatestSign() {
        CommonApiService.instance.getLatestSign().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BaseDto<TerminalSign>>() {
            @Override
            public void onNext(BaseDto<TerminalSign> terminalSignBaseDto) {
                super.onNext(terminalSignBaseDto);
                if (terminalSignBaseDto.getBusinessCode() == 200) {
                    SharedPreferencesUtils.putString(getApplicationContext(), Constant.TERMINAL_PWD, terminalSignBaseDto.getResult().getSign());
                }
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
            case R.id.task:

                break;
        }
    }
}