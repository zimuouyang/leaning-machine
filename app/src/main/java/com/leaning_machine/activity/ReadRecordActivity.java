package com.leaning_machine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.ResourceAdapter;
import com.leaning_machine.adapter.TaskAdapter;
import com.leaning_machine.adapter.UsedTimeAdapter;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalAuthDto;
import com.leaning_machine.common.HttpClient;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.fragment.ReadFragment;
import com.leaning_machine.fragment.TaskFragment;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

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
    private boolean isRead = true;
    private ReadFragment readFragment;
    private TaskFragment taskFragment;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setFragment(0);
        }
    }

    @Override
    public void initView() {
        readTitle = findViewById(R.id.read);
        readLine = findViewById(R.id.read_line);
        recordTitle = findViewById(R.id.record);
        recordLine = findViewById(R.id.record_line);


        readTitle.setOnClickListener(this);
        recordTitle.setOnClickListener(this);
        findViewById(R.id.person_center).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_read_record;
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

    public void setFragment(int index) {
        //获取Fragment管理器
        FragmentManager mFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
        //隐藏所有Fragment
        hideFragments(mTransaction);
        switch (index){
            case 1:
                //显示对应Fragment
                if(taskFragment == null){
                    taskFragment = new TaskFragment();
                    mTransaction.add(R.id.fragment_container, taskFragment,
                            "task_fragment");
                }else {
                    mTransaction.show(taskFragment);
                }
                break;
            case 0:
                if(readFragment == null){
                    readFragment = new ReadFragment();
                    mTransaction.add(R.id.fragment_container, readFragment,
                            "patient_info_fragment");
                }else {
                    mTransaction.show(readFragment);
                }
                break;
            default:
                break;
        }
        //提交事务
        mTransaction.commitAllowingStateLoss();
        currentIndex = index;
    }

    private void hideFragments(FragmentTransaction transaction) {
        if(readFragment != null){
            transaction.hide(readFragment);
        }
        if(taskFragment != null){
            transaction.hide(taskFragment);
        }
    }

    private void changeLayout(boolean isRead) {
        if (this.isRead == isRead) {
            return;
        }
        this.isRead = isRead;
        readTitle.setTextSize(isRead ? 20 : 14);
        readTitle.setTextColor(getResources().getColor(isRead ? R.color.select_read_text : R.color.un_select_text));
        readLine.setVisibility(isRead ? View.VISIBLE : View.GONE);
        recordTitle.setTextSize(isRead ? 14 : 20);
        recordTitle.setTextColor(getResources().getColor(isRead ? R.color.un_select_text : R.color.select_read_text));
        recordLine.setVisibility(isRead ? View.GONE : View.VISIBLE);
        if (isRead) {
           setFragment(0);
        } else {
            setFragment(1);
        }
    }
}