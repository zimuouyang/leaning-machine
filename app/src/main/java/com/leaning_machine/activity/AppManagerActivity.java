package com.leaning_machine.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
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
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.layout.DownloadCircleDialog;
import com.leaning_machine.layout.SpaceItemDecoration;
import com.leaning_machine.utils.AppUtils;
import com.leaning_machine.utils.DownloadUtils;
import com.leaning_machine.utils.FormatUtils;
import com.leaning_machine.utils.SdUtils;
import com.leaning_machine.utils.ToastUtil;
import com.leaning_machine.utils.Utils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.progressmanager.body.ProgressInfo;
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
    DownloadCircleDialog dialogProgress;
    private Long appId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        dialogProgress = new DownloadCircleDialog(this);
    }

    private void initReadAdapter( ) {
        installAppAdapter = new InstallAppAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(installAppAdapter);
        recyclerView.addItemDecoration(new SpaceItemDecoration(15));

        appDtos = new ArrayList<>();
        installAppAdapter.setData(appDtos);

        installAppAdapter.setDownloadClick(new InstallAppAdapter.DownloadClick() {
            @Override
            public void onDownload(AppDto appDto) {
                appId = appDto.getId();
//                addDownloadHistory();
                showNewVersion(Constant.DOWNLOAD_URI + appDto.getFileId());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_manager;
    }


    private void getApp(int page) {
        showProgress();
        CommonApiService.instance.getApps(page, 5).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BaseDto<PageInfo<AppDto>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dismissWindowDialog();
            }

            @Override
            public void onNext(BaseDto<PageInfo<AppDto>> pageInfoBaseDto) {
                dismissWindowDialog();
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
                } else if (pageInfoBaseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(AppManagerActivity.this);
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

    private void showNewVersion(String url) {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(data -> {
                    downloadApk(AppManagerActivity.this, url);

                })
                .onDenied(data -> Log.e("zzz", "未获得权限" + data.toString())).start();
    }

    public void downloadApk(final Activity context, String down_url) {
        dialogProgress.show();
        DownloadUtils.getInstance().download(down_url, SdUtils.getDownloadPath(), "QQ.apk", new DownloadUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                dialogProgress.dismiss();
                ToastUtil.showShort("恭喜你下载成功，开始安装！");
                String successDownloadApkPath = SdUtils.getDownloadPath() + "QQ.apk";
                addDownloadHistory();
                installApkO(AppManagerActivity.this, successDownloadApkPath);
            }
            @Override
            public void onDownloading(ProgressInfo progressInfo) {
                dialogProgress.setProgress(progressInfo.getPercent());
                boolean finish = progressInfo.isFinish();
                if (!finish) {
                    long speed = progressInfo.getSpeed();
                    dialogProgress.setMsg("(" + (speed > 0 ? FormatUtils.formatSize(context, speed) : speed) + "/s)正在下载...");
                } else {
                    dialogProgress.setMsg("下载完成！");
                }
            }
            @Override
            public void onDownloadFailed() {
                dialogProgress.dismiss();
                ToastUtil.showShort("下载失败！");
            }
        });

    }
    // 3.下载成功，开始安装,兼容8.0安装位置来源的权限
    private void installApkO(Context context, String downloadApkPath) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限
            boolean haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                AppUtils.installApk(context, downloadApkPath);
            } else {
                Uri packageUri = Uri.parse("package:"+ AppUtils.getAppPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageUri);
                startActivityForResult(intent,10086);
            }
        } else {
            AppUtils.installApk(context, downloadApkPath);
        }
    }
    //4.开启了安装未知来源应用权限后，再次进行步骤3的安装。
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086) {
            String successDownloadApkPath = SdUtils.getDownloadPath() + "QQ.apk";
            installApkO(AppManagerActivity.this, successDownloadApkPath);
        }
    }

    private void addDownloadHistory() {
        CommonApiService.instance.saveDownloadHistory(appId).subscribeOn(Schedulers.newThread()).subscribe(new DefaultObserver<BaseDto>() {
            @Override
            public void onNext(BaseDto baseDto) {
                super.onNext(baseDto);
                if (baseDto.getBusinessCode() == Constant.INVALID_CODE) {
                    Utils.goToLogin(getApplicationContext());
                }
            }
        });
    }
}