package com.leaning_machine.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.leaning_machine.Constant;
import com.leaning_machine.R;
import com.leaning_machine.base.dto.AppDto;
import com.leaning_machine.layout.CommonDialog;
import com.leaning_machine.model.App;

import java.util.ArrayList;
import java.util.List;

public class InstallAppAdapter extends RecyclerView.Adapter<InstallAppAdapter.MyViewHolder> {
    private Context context;
    private List<AppDto> list;
    private View inflater;

    public InstallAppAdapter(Context context, List<AppDto> list){
        this.context = context;
        this.list = list;
    }
    public InstallAppAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<AppDto> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_install_app,parent,false);
        return new InstallAppAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppDto app = list.get(position);
        Glide.with(context).load("http://8.142.131.31:8080/image/" + app.getApkIconFileId()).into(holder.appImageView);
        holder.appNameText.setText(app.getAppName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            Toast.makeText(context, "未安装", Toast.LENGTH_LONG).show();
        } else {
            context.startActivity(intent);
        }
    }
}
