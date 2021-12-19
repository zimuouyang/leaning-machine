package com.leaning_machine.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.activity.WebViewActivity;
import com.leaning_machine.activity.WelcomeActivity;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{
    private Context context;
    private List<CheckTask> list;
    private View inflater;
    @SuppressLint("UseCompatLoadingForDrawables")
    private int[] drawables = new int[]{R.mipmap.item_icon_1, R.mipmap.item_icon_2, R.mipmap.item_icon_3, R.mipmap.item_icon_4 };

    public TaskAdapter(Context context, List<CheckTask> list){
        this.context = context;
        this.list = list;
    }
    public TaskAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<CheckTask> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_resource,parent,false);
        return new TaskAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CheckTask checkTask = list.get(position);
        holder.resourceImage.setImageDrawable(context.getResources().getDrawable(drawables[new Random().nextInt(4)]));
        holder.resourceTitle.setText(checkTask.getResource().getResourceName());
        holder.resourceDes.setText("日期：  " + simpleDateFormat.format(checkTask.getRecordDate()));
        if (checkTask.isRecorded()) {
            holder.readButton.setImageDrawable(context.getDrawable(R.mipmap.recorded_task));
        } else {
            if (checkTask.getRecordDate().getTime() > new Date().getTime()) {
                holder.readButton.setImageDrawable(context.getDrawable(R.mipmap.not_record));
            } else {
                holder.readButton.setImageDrawable(context.getDrawable(R.mipmap.record_task));
            }
        }
        holder.readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkTask.getRecordDate().getTime() > new Date().getTime()) {
                    return;
                }
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, checkTask.getResource().getAddress());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView resourceImage;
        private TextView resourceTitle;
        private TextView resourceDes;
        private ImageView readButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            resourceImage = itemView.findViewById(R.id.resource_img);
            resourceTitle = itemView.findViewById(R.id.resource_title);
            resourceDes = itemView.findViewById(R.id.resource_des);
            readButton = itemView.findViewById(R.id.read_btn);
        }
    }
}
