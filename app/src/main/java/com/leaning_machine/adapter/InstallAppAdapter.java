package com.leaning_machine.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.application.GlideApp;
import com.leaning_machine.base.dto.AppDto;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.layout.CommonDialog;
import com.leaning_machine.model.App;
import com.leaning_machine.utils.DownloadUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class InstallAppAdapter extends RecyclerView.Adapter<InstallAppAdapter.MyViewHolder> {
    private Context context;
    private List<AppDto> list;
    private View inflater;
    private DownloadClick downloadClick;

    public InstallAppAdapter(Context context, List<AppDto> list) {
        this.context = context;
        this.list = list;
    }

    public InstallAppAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<AppDto> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void setDownloadClick(DownloadClick downloadClick) {
        this.downloadClick = downloadClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_install_app, parent, false);
        return new InstallAppAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppDto app = list.get(position);
        GlideApp.with(context).load(Constant.IMAGE_URI + app.getApkIconFileId()).error(R.mipmap.ic_launcher).into(holder.appImageView);
        holder.appNameText.setText(app.getAppName());
        holder.installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downloadClick != null) {
                    downloadClick.onDownload(app);
                }
            }
        });

        if (app.getReleaseNote() != null && !app.getReleaseNote().isEmpty()) {
            holder.appDesView.setVisibility(View.VISIBLE);
            holder.appDesView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonDialog.newBuilder().context(context).des(app.getReleaseNote()).build().show();
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView appImageView;
        ImageView appDesView;
        TextView appNameText;
        Button installButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            appImageView = itemView.findViewById(R.id.app_img);
            appNameText = itemView.findViewById(R.id.app_name);
            appDesView = itemView.findViewById(R.id.app_des);
            installButton = itemView.findViewById(R.id.install_btn);
        }
    }

    public void openApp(String packageName, Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            Toast.makeText(context, "未安装", Toast.LENGTH_LONG).show();
        } else {
            context.startActivity(intent);
        }
    }

    public interface DownloadClick {
        void onDownload(AppDto appDto);
    }
}
