package com.leaning_machine.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.leaning_machine.R;

public class GoClassActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        findViewById(R.id.person_center).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.ding_ding).setOnClickListener(this);
        findViewById(R.id.we_chat).setOnClickListener(this);
        findViewById(R.id.qq).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_go_class;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_center:
                startActivity(new Intent(this, PersonCenterActivity.class));
                break;
            case R.id.play:
                finish();
                break;
            case R.id.ding_ding:
                openApp(getString(R.string.package_name_ding_ding));
                break;
            case R.id.we_chat:
                openApp(getString(R.string.package_name_we_chat));
                break;
            case R.id.qq:
                openApp(getString(R.string.package_name_qq));
                break;
        }
    }
}