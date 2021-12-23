package com.leaning_machine.base.application;

import android.app.Application;

import com.leaning_machine.Constant;
import com.leaning_machine.activity.LoginActivity;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.SmallUtils;


public class BaseApplication extends Application {

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

    }
}
