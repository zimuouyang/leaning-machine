package com.leaning_machine.activity;

import android.os.Bundle;

import com.leaning_machine.R;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends BaseActivity {
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
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new App(R.mipmap.han_yu_zi_dian, getString(R.string.han_yu_zi_dian)));
        list.add(new App(R.mipmap.xue_xi_qiang_guo, getString(R.string.xue_xi_qiang_guo)));
        list.add(new App( R.mipmap.history, getString(R.string.quan_li_shi)));
        list.add(new App(R.mipmap.zuo_ye_pi_gai, getString(R.string.bi_shen_zuo_wen)));
        topWithContentLayout.setAppList(list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_language;
    }
}