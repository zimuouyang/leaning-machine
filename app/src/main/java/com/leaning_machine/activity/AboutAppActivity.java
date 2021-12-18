package com.leaning_machine.activity;


import com.leaning_machine.R;

public class AboutAppActivity extends BaseActivity {


    @Override
    public void initView() {
        findViewById(R.id.play).setOnClickListener(view -> finish());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_app;
    }
}