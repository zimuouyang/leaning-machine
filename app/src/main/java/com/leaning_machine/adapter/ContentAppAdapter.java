package com.leaning_machine.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.application.GlideApp;
import com.leaning_machine.base.dto.LearnTime;
import com.leaning_machine.db.GlobalDatabase;
import com.leaning_machine.db.dao.UsedPackageDao;
import com.leaning_machine.db.entity.UsedPackageEntity;
import com.leaning_machine.layout.CommonDialog;
import com.leaning_machine.layout.PasswordDialog;
import com.leaning_machine.model.App;
import com.leaning_machine.model.AppType;
import com.leaning_machine.model.UsedMax;
import com.leaning_machine.model.UsingApp;
import com.leaning_machine.model.VoiceType;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ContentAppAdapter extends RecyclerView.Adapter<ContentAppAdapter.MyViewHolder> {
    private Context context;
    private List<App> list;
    private View inflater;
    private AppType appType = AppType.LOCAL;

    public ContentAppAdapter(Context context, List<App> list){
        this.context = context;
        this.list = list;
    }
    public ContentAppAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public ContentAppAdapter(Context context, AppType appType){
        this.context = context;
        this.list = new ArrayList<>();
        this.appType = appType;
    }

    public void setData(List<App> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(appType == AppType.LOCAL ? R.layout.item_app : R.layout.item_expand_app,parent,false);
        return new ContentAppAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        App app = list.get(position);
        if (!app.isRemote()) {
            holder.appImageView.setImageDrawable(context.getDrawable(app.getDrawableId()));
        } else {
            GlideApp.with(context).load(Constant.IMAGE_URI + app.getImgUrl()).error(R.mipmap.ic_launcher).into(holder.appImageView);
        }
        holder.appNameText.setText(app.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isTeacher = SharedPreferencesUtils.getInt(context, Constant.ROLE, 0) == 1;
                if (app.getPackageName().equals(context.getString(R.string.package_name_xue_xi_qiang_guo))) {
                    if (isTeacher) {
                        openApp(app.getPackageName(), context);
                    } else {
                        PasswordDialog passwordDialog = new PasswordDialog.Builder().context(context).type(VoiceType.PASSWORD).build();
                        passwordDialog.setPasswordClick(new PasswordDialog.PasswordClick() {
                            @Override
                            public void onSuccess() {
                                openApp(app.getPackageName(), context);
                            }
                        });
                        passwordDialog.show();
                    }
                } else {
                    openApp(app.getPackageName(), context);
                }
            }
        });

        if (app.getDetail() != null && !app.getDetail().isEmpty()) {
            holder.appDesView.setVisibility(View.VISIBLE);
            holder.appDesView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonDialog.newBuilder().context(context).des(app.getDetail()).build().show();
                }
            });
        } else {
            holder.appDesView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView appImageView;
        ImageView appDesView;
        TextView appNameText;

        public MyViewHolder(View itemView) {
            super(itemView);
            appImageView = itemView.findViewById(R.id.app_img);
            appNameText = itemView.findViewById(R.id.app_name);
            appDesView = itemView.findViewById(R.id.app_des);
        }
    }

    public void openApp(String packageName, Context context) {
        if (!Utils.checkAppInstalled(context, packageName)) {
            Toast.makeText(context, "未安装", Toast.LENGTH_LONG).show();
            return;
        }
        Observable.just(Boolean.TRUE).flatMap(new Func1<Boolean, Observable<UsedMax>>() {
            @Override
            public Observable<UsedMax> call(Boolean aBoolean) {
                //今日已使用超过时间
                LearnTime todayUse = SharedPreferencesUtils.getObject(context, Constant.SP_TODAY_USE_TIME, LearnTime.class, null);
                if (todayUse != null) {
                    if (todayUse.getCreateDate().equals(Utils.getDateString()) && todayUse.getTotal() > Constant.TWO_HOURS) {
                        return Observable.just(UsedMax.DAY_MAX);
                    }
                }
                UsedPackageDao usedPackageDao = GlobalDatabase.getInstance(context.getApplicationContext()).usedPackageDao();
                UsedPackageEntity usedPackageEntity = usedPackageDao.getUsedTimeEntity(Utils.getDateString(), packageName);
                if (usedPackageEntity != null && usedPackageEntity.getTime() > Constant.HALF_HOURS) {
                    //如果未超过15分钟，不可以打开
                    if ((System.currentTimeMillis() - usedPackageEntity.getLastUseTime()) <  Constant.FIFTEEN_MINUTES) {
                        return Observable.just(UsedMax.DAY_MAX);
                    }
                }
                return Observable.just(UsedMax.NORMAL);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<UsedMax>() {
            @Override
            public void call(UsedMax aBoolean) {
                switch (aBoolean) {
                    case NORMAL:
                        startThirdApp(packageName);
                        break;
                    case DAY_MAX:
                        showDayMaxDialog();
                        break;
                    case SINGLE_APP_MAX:
                        showSingleAppMaxDialog();
                        break;
                }
            }
        });
    }

    private void showDayMaxDialog() {
        new PasswordDialog.Builder().context(context).type(VoiceType.TODAY).build().show();
    }

    private void showSingleAppMaxDialog() {
        new PasswordDialog.Builder().context(context).type(VoiceType.MINUTE).build().show();
    }

    private void startThirdApp(String packageName) {
        PackageManager packageManager = context.getPackageManager();
        UsingApp usingApp = new UsingApp();
        usingApp.setStartTime(System.currentTimeMillis());
        usingApp.setPackageName(packageName);
        SharedPreferencesUtils.putObject(context.getApplicationContext(), Constant.USING_PACKAGE, usingApp);
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            context.startActivity(intent);
        }
    }
}
