package com.leaning_machine.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.TodayAdapter;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.LearnTime;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.model.TodayUse;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TodayLearnActivity extends BaseActivity {
    private  List<TodayUse> todayUseList;
    private TodayUse pinDu;
    private TodayUse erDuo;
    private TodayUse yueDu;
    private TodayUse lianXi;
    private TodayUse danCi;
    private TodayUse quYiZhi;
    private TodayUse language;
    private TodayUse math;
    private TodayUse total;
    private TodayUse others;
    private RecyclerView recyclerView;
    private TodayAdapter todayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        todayUseList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycle_list);
        initAdapter();
        initDate();
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getLearnTime();
    }

    private void initDate() {
        pinDu = new TodayUse(getString(R.string.pin_du));
        erDuo = new TodayUse(getString(R.string.mo_er_duo));
        yueDu = new TodayUse(getString(R.string.ai_yue_du));
        lianXi = new TodayUse(getString(R.string.qin_lian_xi));
        danCi = new TodayUse(getString(R.string.bei_dan_ci));
        quYiZhi = new TodayUse(getString(R.string.qi_yi_zhi));
        language = new TodayUse("大语文素养");
        math = new TodayUse("数学逻辑");
        total = new TodayUse(getString(R.string.today_total_length));
        others = new TodayUse(getString(R.string.others));
        showDate();
    }

    private void showDate() {
        todayUseList = new ArrayList<>();
        LearnTime todayUse = SharedPreferencesUtils.getObject(this, Constant.SP_TODAY_USE_TIME, LearnTime.class, null);
        if (todayUse != null) {
            pinDu.setUseTime(todayUse.getSpelling());
            erDuo.setUseTime(todayUse.getGrindEars());
            yueDu.setUseTime(todayUse.getLoveRead());
            lianXi.setUseTime(todayUse.getPracticeFrequently());
            danCi.setUseTime(todayUse.getReciteWords());
            quYiZhi.setUseTime(todayUse.getFluent());
            language.setUseTime(todayUse.getLanguage());
            math.setUseTime(todayUse.getMath());
            total.setUseTime(todayUse.getTotal());
            others.setUseTime(todayUse.getOthers());
        }

        todayUseList.add(total);
        todayUseList.add(language);
        todayUseList.add(math);
        todayUseList.add(others);
        todayUseList.add(pinDu);
        todayUseList.add(erDuo);
        todayUseList.add(yueDu);
        todayUseList.add(lianXi);
        todayUseList.add(danCi);
        todayUseList.add(quYiZhi);

        todayAdapter.setData(todayUseList);
    }


    private void initAdapter() {
        todayAdapter = new TodayAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(todayAdapter);
    }

    private void getLearnTime() {
        showProgress();
        CommonApiService.instance.getLearnTime(Utils.getDateString()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BaseDto<LearnTime>>() {
            @Override
            public void onNext(BaseDto<LearnTime> learnTimeBaseDto) {
                if (learnTimeBaseDto.getBusinessCode() == 200) {
                    SharedPreferencesUtils.putObject(getApplicationContext(), Constant.SP_TODAY_USE_TIME, learnTimeBaseDto.getResult());
                    showDate();
                } else if (learnTimeBaseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(getApplicationContext());
                }
                dismissWindowDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                dismissWindowDialog();
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_today_learn;
    }
}