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

import com.leaning_machine.R;
import com.leaning_machine.model.App;

import java.util.ArrayList;
import java.util.List;

public class ContentAppAdapter extends RecyclerView.Adapter<ContentAppAdapter.MyViewHolder> {
    private Context context;
    private List<App> list;
    private View inflater;

    public ContentAppAdapter(Context context, List<App> list){
        this.context = context;
        this.list = list;
    }
    public ContentAppAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<App> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_app,parent,false);
        return new ContentAppAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        App app = list.get(position);
        if (!app.isRemote()) {
            holder.appImageView.setImageDrawable(context.getDrawable(app.getDrawableId()));
        }
        holder.appNameText.setText(app.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openApp(app.getPackageName(), context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView appImageView;
        TextView appNameText;

        public MyViewHolder(View itemView) {
            super(itemView);
            appImageView = itemView.findViewById(R.id.app_img);
            appNameText = itemView.findViewById(R.id.app_name);
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
