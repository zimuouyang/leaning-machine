package com.leaning_machine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.adapter.InstallAppAdapter;
import com.leaning_machine.base.dto.AppDto;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.PageInfo;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.layout.SpaceItemDecoration;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppManagerActivity extends BaseActivity implements View.OnClickListener {
    private static String TAG = AppManagerActivity.class.getSimpleName();

    private TextView installTitle;
    private View installLine;
    private TextView uninstallTitle;
    private View uninstallLine;
    private boolean isInatall = true;
    private RelativeLayout uninstallAppLayout;
    private RelativeLayout appLayout;
    private RefreshLayout refreshLayout;
    private List<AppDto> appDtos;
    private boolean hasData;
    private int currentPage = 1;
    private InstallAppAdapter installAppAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApp(1);
    }

    @Override
    public void initView() {
        installTitle = findViewById(R.id.install);
        installLine = findViewById(R.id.install_line);
        uninstallTitle = findViewById(R.id.uninstall);
        uninstallLine = findViewById(R.id.uninstall_line);
        uninstallAppLayout = findViewById(R.id.uninstall_app_layout);
        appLayout = findViewById(R.id.app_layout);


        installTitle.setOnClickListener(this);
        uninstallTitle.setOnClickListener(this);

        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.setting_center).setOnClickListener(this);

        recyclerView = findViewById(R.id.app_list);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshFooter(new ClassicsFooter((this)));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hasData) {
                    getApp(++currentPage);
                }
            }
        });
        initReadAdapter();

    }

    private void initReadAdapter( ) {
        installAppAdapter = new InstallAppAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(installAppAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));

        appDtos = new ArrayList<>();
        installAppAdapter.setData(appDtos);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_manager;
    }


    private void getApp(int page) {
        CommonApiService.instance.getApps(page, 5).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseDto<PageInfo<AppDto>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseDto<PageInfo<AppDto>> pageInfoBaseDto) {
                if (pageInfoBaseDto.getBusinessCode() == Constant.SUCCESS) {

                    hasData = !pageInfoBaseDto.getResult().isLastPage();
                    currentPage = pageInfoBaseDto.getResult().getPageNum();
                    appDtos.addAll(pageInfoBaseDto.getResult().getList());
                    installAppAdapter.setData(appDtos);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                finish();
                break;
            case R.id.install:
                changeLayout(true);
                break;
            case R.id.uninstall:
                changeLayout(false);
                break;
            case R.id.setting_center:
                uninstallApk(AppManagerActivity.this);
                break;

        }
    }

    public void uninstallApk(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        context.startActivity(intent);
    }

    private void changeLayout(boolean isInstall) {
        if (this.isInatall == isInstall) {
            return;
        }
        this.isInatall = isInstall;
        installTitle.setTextSize(isInstall ? 20 : 14);
        installTitle.setTextColor(getResources().getColor(isInstall ? R.color.select_read_text : R.color.un_select_text));
        installLine.setVisibility(isInstall ? View.VISIBLE : View.GONE);
        uninstallTitle.setTextSize(isInstall ? 14 : 20);
        uninstallTitle.setTextColor(getResources().getColor(isInstall ? R.color.un_select_text : R.color.select_read_text));
        uninstallLine.setVisibility(isInstall ? View.GONE : View.VISIBLE);
        uninstallAppLayout.setVisibility(isInstall ? View.GONE : View.VISIBLE);
        appLayout.setVisibility(isInstall ? View.VISIBLE : View.GONE);

    }
}