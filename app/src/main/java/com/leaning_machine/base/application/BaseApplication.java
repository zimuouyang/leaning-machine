package com.leaning_machine.base.application;

import android.app.Application;

import com.leaning_machine.Constant;
import com.leaning_machine.common.service.CommonApiService;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonApiService.instance.init(Constant.BASE_URI);
    }
}
