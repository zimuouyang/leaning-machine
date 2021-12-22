package com.leaning_machine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.utils.SharedPreferencesUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PersonCenterActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        findViewById(R.id.top_list).setOnClickListener(this);
        findViewById(R.id.listen_statistics).setOnClickListener(this);
        findViewById(R.id.statistics).setOnClickListener(this);
        findViewById(R.id.about_us).setOnClickListener(this);
        findViewById(R.id.main).setOnClickListener(this);
        findViewById(R.id.log_out).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_center;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_list:
                startActivity(new Intent(this, TopListActivity.class));
                break;
            case R.id.listen_statistics:
                startActivity(new Intent(this, ListenStatisticalActivity.class));
                break;
            case R.id.statistics:
                startActivity(new Intent(this, TodayLearnActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.main:
                finish();
                break;
            case R.id.log_out:
                logout();
                break;
        }
    }

    private void logout() {
        CommonApiService.instance.logOut().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<BaseDto>() {
            @Override
            public void call(BaseDto baseDto) {
                if (baseDto.getBusinessCode() == 200) {
                   toLogin();
                } else {
                    Toast.makeText(PersonCenterActivity.this, "登出失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toLogin() {
        SharedPreferencesUtils.putBoolean(this, Constant.LOGIN, false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}