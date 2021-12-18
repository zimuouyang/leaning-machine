package com.leaning_machine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leaning_machine.R;
import com.leaning_machine.adapter.ResourceAdapter;
import com.leaning_machine.adapter.TopListAdapter;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TopListDto;

import java.util.ArrayList;
import java.util.List;

public class TopListActivity extends BaseActivity implements View.OnClickListener {
    private TextView totalTitle;
    private View totalLine;
    private TextView monthTitle;
    private View monthLine;
    private TextView dayTitle;
    private View dayLine;
    private RecyclerView recyclerView;
    private TopListAdapter topListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        totalTitle = findViewById(R.id.total_title);
        totalLine = findViewById(R.id.total_line);
        monthTitle = findViewById(R.id.month_title);
        monthLine = findViewById(R.id.month_line);
        dayTitle = findViewById(R.id.day_title);
        dayLine = findViewById(R.id.day_line);


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


        List<TopListDto> topListDtos = new ArrayList<>();
        topListDtos.add(new TopListDto("资源一", 30L, 1L));
        topListDtos.add(new TopListDto("资源为", 50L, 4L));
        topListAdapter.setData(topListDtos);
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
    }
}