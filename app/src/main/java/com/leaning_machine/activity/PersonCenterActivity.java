package com.leaning_machine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.leaning_machine.R;

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
                break;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.main:
                finish();
                break;
        }
    }
}