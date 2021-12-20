package com.leaning_machine.activity;


import android.os.Bundle;

import com.leaning_machine.R;
import com.leaning_machine.layout.TopWithContentLayout;

public class ExpandActivity extends BaseActivity {
    TopWithContentLayout topWithContentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        topWithContentLayout = findViewById(R.id.content);

        topWithContentLayout.setFinishClick(ExpandActivity.this::finish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_expand;
    }
}