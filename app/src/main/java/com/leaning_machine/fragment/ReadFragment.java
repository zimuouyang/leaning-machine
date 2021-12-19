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
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.layout.LoadingAlertDialog;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadFragment extends Fragment {
    private RefreshLayout refreshLayout;
    private List<ResourceDto> resourceDtos;
    private boolean hasData;
    private int currentPage = 1;
    private ResourceAdapter resourceAdapter;
    private RecyclerView recyclerView;
    private LoadingAlertDialog dialog;

    public ReadFragment() {
        // Required empty public constructor
    }


    public static ReadFragment newInstance(String param1, String param2) {
        ReadFragment fragment = new ReadFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCloudResources(1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.resource_list);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshFooter(new ClassicsFooter((getActivity())));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hasData) {
                    getCloudResources(++currentPage);
                }
            }
        });
        initReadAdapter();
    }

    private void initReadAdapter( ) {
        resourceAdapter = new ResourceAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(resourceAdapter);
        resourceDtos = new ArrayList<>();
        resourceAdapter.setData(resourceDtos);
    }

    public void getCloudResources(int page) {
        showProgress();
        CommonApiService.instance.terminalResources(page, 5).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseDto<PageInfo<ResourceDto>>>() {
            @Override
            public void onCompleted() {
                dismissWindowDialog();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseDto<PageInfo<ResourceDto>> pageInfoBaseDto) {
                if (pageInfoBaseDto.getBusinessCode() == Constant.SUCCESS) {
                    hasData = !pageInfoBaseDto.getResult().isLastPage();
                    currentPage = pageInfoBaseDto.getResult().getPageNum();
                    resourceDtos.addAll(pageInfoBaseDto.getResult().getList());
                    resourceAdapter.setData(resourceDtos);
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

    private void showProgress() {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new LoadingAlertDialog(getActivity());
        dialog.show(getString(R.string.msg_loading));
    }

    private void dismissWindowDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}