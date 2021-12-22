package com.leaning_machine.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.leaning_machine.activity.WebViewActivity;
import com.leaning_machine.activity.WelcomeActivity;
import com.leaning_machine.base.dto.BaseDto;
import com.leaning_machine.base.dto.CheckRecord;
import com.leaning_machine.base.dto.CheckTask;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.base.dto.TerminalDetail;
import com.leaning_machine.common.service.CommonApiService;
import com.leaning_machine.domain.DefaultObserver;
import com.leaning_machine.utils.SharedPreferencesUtils;
import com.leaning_machine.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder>{
    private Context context;
    private List<CheckTask> list;
    private View inflater;
    @SuppressLint("UseCompatLoadingForDrawables")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
        holder.readButton.setTag(position);
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
                if (!checkTask.isRecorded()) {
                    //是当前打卡日期
                    if (simpleDateFormat.format(checkTask.getRecordDate()).equals(simpleDateFormat.format(new Date()))) {
                        addCheckRecord(checkTask, (int)holder.readButton.getTag());
                    } else {
                        Toast.makeText(context, "还未到打卡时间", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    openLink(checkTask);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void addCheckRecord(CheckTask checkTask, int position) {
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setCheckTask(checkTask);
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(checkTask.getResource().getId());
        checkRecord.setResource(resourceDto);
        checkRecord.setRecordDate(Utils.getDateString());
        TerminalDetail terminalDetail = new TerminalDetail();
        terminalDetail.setId(SharedPreferencesUtils.getLong(context, Constant.TERMINAL_ID, 0));
        checkRecord.setTerminalUser(terminalDetail);
        CommonApiService.instance.addRecord(checkRecord).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DefaultObserver<BaseDto>() {
            @Override
            public void onCompleted() {
                super.onCompleted();
                openLink(checkTask);
            }

            @Override
            public void onNext(BaseDto baseDto) {
                super.onNext(baseDto);
                if (baseDto.getBusinessCode() == 200) {
                    checkTask.setRecorded(true);
                    list.set(position, checkTask);
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void openLink(CheckTask checkTask) {
        Uri uri = Uri.parse(checkTask.getResource().getAddress());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
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
