package com.leaning_machine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.ResourceAdapter;
import com.leaning_machine.adapter.UsedTimeAdapter;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadRecordActivity extends BaseActivity implements View.OnClickListener {
    private TextView readTitle;
    private View readLine;
    private TextView recordTitle;
    private View recordLine;
    private RecyclerView recyclerView;
    private ResourceAdapter resourceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCloudResources();
    }

    @Override
    public void initView() {
        readTitle = findViewById(R.id.read);
        readLine = findViewById(R.id.read_line);
        recordTitle = findViewById(R.id.record);
        recordLine = findViewById(R.id.record_line);
        recyclerView = findViewById(R.id.resource_list);

        readTitle.setOnClickListener(this);
        recordTitle.setOnClickListener(this);
        findViewById(R.id.person_center).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);

        resourceAdapter = new ResourceAdapter(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(resourceAdapter);


        List<ResourceDto> resourceDtos = new ArrayList<>();
        resourceDtos.add(new ResourceDto("资源一", "描述一"));
        resourceDtos.add(new ResourceDto("资源二", "描二"));
        resourceAdapter.setData(resourceDtos);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_record;
    }

    public void getCloudResources() {
        CommonApiService.instance.terminalResources(1, 10).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<ResourceDto>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ResourceDto> resourceDtos) {

//                if (terminalAuthDto.getBusinessCode() == 200) {
//
//                } else {
//
//                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.read:
                changeLayout(true);
                break;
            case R.id.record:
                changeLayout(false);
                break;
            case R.id.person_center:
                startActivity(new Intent(this, PersonCenterActivity.class));
                break;
            case R.id.play:
                finish();
                break;
        }
    }

    private void changeLayout(boolean isRead) {
        readTitle.setTextSize(isRead ? 20 : 14);
        readTitle.setTextColor(getResources().getColor(isRead ? R.color.select_read_text : R.color.un_select_text));
        readLine.setVisibility(isRead ? View.VISIBLE : View.GONE);
        recordTitle.setTextSize(isRead ? 14 : 20);
        recordTitle.setTextColor(getResources().getColor(isRead ? R.color.un_select_text : R.color.select_read_text));
        recordLine.setVisibility(isRead ? View.GONE : View.VISIBLE);
    }
}