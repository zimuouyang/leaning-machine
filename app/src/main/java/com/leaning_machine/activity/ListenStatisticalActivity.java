package com.leaning_machine.activity;


import com.leaning_machine.R;

public class ListenStatisticalActivity extends BaseActivity {


    @Override
    public void initView() {
        findViewById(R.id.play).setOnClickListener(view -> ListenStatisticalActivity.this.finish());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_listen_statis;
    }
}