package com.leaning_machine.activity;

import android.os.Bundle;

import com.leaning_machine.R;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;

import java.util.ArrayList;
import java.util.List;

public class MathActivity extends BaseActivity {
    TopWithContentLayout topWithContentLayout;
    List<App> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void initView() {
        topWithContentLayout = findViewById(R.id.content);
        initTopWithContentLayout();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new App(R.mipmap.moose_math, getString(R.string.moose_math), getString(R.string.package_name_moose_math)));
        list.add(new App(R.mipmap.dou_dou_math, getString(R.string.dou_dou_shu_xue), getString(R.string.package_name_du_du_math)));
        list.add(new App( R.mipmap.pythagorea, getString(R.string.pythagorea), getString(R.string.package_name_pythagorea)));
        list.add(new App(R.mipmap.ke_han_xue_yuan, getString(R.string.ke_han_xue_yuan), getString(R.string.package_name_ke_han_xue_yuan)));
        topWithContentLayout.setAppList(list);
    }

    private void initTopWithContentLayout() {
        topWithContentLayout.setFinishClick(MathActivity.this::finish);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_math;
    }
}