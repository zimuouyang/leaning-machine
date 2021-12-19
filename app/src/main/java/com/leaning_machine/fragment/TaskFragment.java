package com.leaning_machine.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.ResourceAdapter;
import com.leaning_machine.adapter.TaskAdapter;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.common.service.CommonApiService;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TaskFragment extends Fragment {
    private RefreshLayout refreshLayout;
    private List<CheckTask> checkTasks;
    private boolean hasData;
    private int currentPage = 1;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;

    public TaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCheckTask(1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.task_list);
        refreshLayout = view.findViewById(R.id.task_refreshLayout);
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hasData) {
                    getCheckTask(++currentPage);
                }
            }
        });
        initTaskAdapter();
    }

    private void initTaskAdapter( ) {
        taskAdapter = new TaskAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(taskAdapter);
        checkTasks = new ArrayList<>();
        taskAdapter.setData(checkTasks);
    }

    public void getCheckTask(int page) {
        CommonApiService.instance.getCheckTasks(page, 5).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseDto<PageInfo<CheckTask>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseDto<PageInfo<CheckTask>> checks) {
                if (checks.getBusinessCode() == Constant.SUCCESS) {
                    hasData = !checks.getResult().isLastPage();
                    currentPage = checks.getResult().getPageNum();
                    checkTasks.addAll(checks.getResult().getList());
                    taskAdapter.setData(checkTasks);
                    if (!hasData) {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        refreshLayout.finishLoadMore();
                    }
                } else {
                    refreshLayout.finishLoadMore();
                }
            }
        });
    }
}