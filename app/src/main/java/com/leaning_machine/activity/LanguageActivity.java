package com.leaning_machine.activity;

import android.os.Bundle;


import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.TerminalSign;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.TopWithContentLayout;
import com.leaning_machine.model.App;

import com.leaning_machine.utils.SharedPreferencesUtils;


import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LanguageActivity extends BaseActivity {
    TopWithContentLayout topWithContentLayout;
    List<App> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        getLatestSign();
    }

    @Override
    public void initView() {
        topWithContentLayout = findViewById(R.id.content);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new App(R.mipmap.han_yu_zi_dian, getString(R.string.han_yu_zi_dian), getString(R.string.package_name_han_yu_zi_dian), getString(R.string.han_yu_zi_dian_des)));
        list.add(new App(R.mipmap.xue_xi_qiang_guo, getString(R.string.xue_xi_qiang_guo), getString(R.string.package_name_xue_xi_qiang_guo)));
        list.add(new App( R.mipmap.history, getString(R.string.quan_li_shi), getString(R.string.package_name_quan_li_shi), getString(R.string.quan_li_shi_des)));
        list.add(new App(R.mipmap.zuo_ye_pi_gai, getString(R.string.bi_shen_zuo_wen), getString(R.string.package_name_bi_shen_zuo_wen), getString(R.string.bi_shen_zuo_wen_des)));
        topWithContentLayout.setAppList(list);

        topWithContentLayout.setFinishClick(LanguageActivity.this::finish);
    }

    private void getLatestSign() {
        CommonApiService.instance.getLatestSign().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BaseDto<TerminalSign>>() {
            @Override
            public void onNext(BaseDto<TerminalSign> terminalSignBaseDto) {
                super.onNext(terminalSignBaseDto);
                if (terminalSignBaseDto.getBusinessCode() == 200) {
                    SharedPreferencesUtils.putString(getApplicationContext(), Constant.TERMINAL_PWD, terminalSignBaseDto.getResult().getSign());
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_language;
    }
}