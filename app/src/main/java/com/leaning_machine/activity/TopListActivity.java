package com.leaning_machine.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leaning_machine.R;
import com.leaning_machine.adapter.TopListAdapter;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.TopListModel;
import com.leaning_machine.base.dto.TopListResultModel;
import com.leaning_machine.common.service.CommonApiService;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TopListActivity extends BaseActivity implements View.OnClickListener {
    private TextView totalTitle;
    private View totalLine;
    private TextView monthTitle;
    private View monthLine;
    private TextView dayTitle;
    private View dayLine;
    private RecyclerView recyclerView;
    private TopListAdapter topListAdapter;
    private List<TopListModel> dayList;
    private List<TopListModel> monthList;
    private List<TopListModel> totalList;
    private TextView topFirst;
    private TextView topSecond;
    private TextView topThird;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTotalList();
    }

    private void getTotalList() {
        CommonApiService.instance.getTopList().
                subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<BaseDto<TopListResultModel>>() {
                    @Override
                    public void call(BaseDto<TopListResultModel> topListResultModelBaseDto) {
                        if (topListResultModelBaseDto.getBusinessCode() == 200) {
                            dayList = topListResultModelBaseDto.getResult().getDayList();
                            monthList = topListResultModelBaseDto.getResult().getMonthList();
                            totalList = topListResultModelBaseDto.getResult().getTotalList();
                            setTopList();
                        }
                    }
                });
    }

    @Override
    public void initView() {
        totalTitle = findViewById(R.id.total_title);
        totalLine = findViewById(R.id.total_line);
        monthTitle = findViewById(R.id.month_title);
        monthLine = findViewById(R.id.month_line);
        dayTitle = findViewById(R.id.day_title);
        dayLine = findViewById(R.id.day_line);
        topFirst = findViewById(R.id.top_first);
        topSecond = findViewById(R.id.top_second);
        topThird = findViewById(R.id.top_third);


        recyclerView = findViewById(R.id.top_list);

        totalTitle.setOnClickListener(this);
        monthTitle.setOnClickListener(this);
        dayTitle.setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);

        initAdapter();
    }

    private void initAdapter() {
        topListAdapter = new TopListAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(topListAdapter);
    }

    private void setTopList() {
        List<TopListModel> list;
        if (currentIndex == 0) {
            list = totalList;
        } else if (currentIndex == 1) {
            list = monthList;
        } else {
            list = dayList;
        }
        if (list.size() >= 3) {
            topListAdapter.setData(list);
        }
        setTitle(list);
    }

    private void setTitle(List<TopListModel> totalList) {
        topFirst.setText("");
        topSecond.setText("");
        topThird.setText("");
        topFirst.setVisibility(View.GONE);
        topSecond.setVisibility(View.GONE);
        topThird.setVisibility(View.GONE);
        if (totalList.size() > 0) {
            topFirst.setText(detailTitle(totalList.get(0)));
            topFirst.setVisibility(View.VISIBLE);
        }
        if (totalList.size() > 1) {
            topSecond.setText(detailTitle(totalList.get(1)));
            topSecond.setVisibility(View.VISIBLE);
        }
        if (totalList.size() > 2) {
            topThird.setText(detailTitle(totalList.get(2)));
            topThird.setVisibility(View.VISIBLE);
        }
    }

    private String detailTitle(TopListModel topListModel) {
        return topListModel.getUserName() + " : " + topListModel.getTime() / 60 + "分钟";
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.total_title:
                changeLayout(0);
                break;
            case R.id.month_title:
                changeLayout(1);
                break;
            case R.id.day_title:
                changeLayout(2);
                break;
            case R.id.play:
                finish();
                break;
        }
    }

    private void changeLayout(int index) {
        currentIndex = index;
        totalTitle.setTextSize(14);
        totalTitle.setTextColor(getResources().getColor(R.color.un_select_text));
        totalLine.setVisibility(View.GONE);
        monthTitle.setTextSize(14);
        monthTitle.setTextColor(getResources().getColor(R.color.un_select_text));
        monthLine.setVisibility(View.GONE);
        dayTitle.setTextSize(14);
        dayTitle.setTextColor(getResources().getColor(R.color.un_select_text));
        dayLine.setVisibility(View.GONE);


        if (index == 0) {
            totalTitle.setTextSize(20);
            totalTitle.setTextColor(getResources().getColor(R.color.select_read_text));
            totalLine.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            monthTitle.setTextSize(20);
            monthTitle.setTextColor(getResources().getColor(R.color.select_read_text));
            monthLine.setVisibility(View.VISIBLE);
        } else {
            dayTitle.setTextSize(20);
            dayTitle.setTextColor(getResources().getColor(R.color.select_read_text));
            dayLine.setVisibility(View.VISIBLE);
        }

        setTopList();
    }
}