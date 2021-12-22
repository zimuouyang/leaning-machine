package com.leaning_machine.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.base.dto.ResourceDto;
import com.leaning_machine.model.TodayUse;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.MyViewHolder>{
    private Context context;
    private List<TodayUse> list;
    private View inflater;
    @SuppressLint("UseCompatLoadingForDrawables")
    private int[] drawables = new int[]{R.mipmap.item_icon_1, R.mipmap.item_icon_2, R.mipmap.item_icon_3, R.mipmap.item_icon_4 };

    public TodayAdapter(Context context, List<TodayUse> list){
        this.context = context;
        this.list = list;
    }
    public TodayAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<TodayUse> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_today_learn,parent,false);
        return new TodayAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TodayUse resourceDto = list.get(position);
        holder.appImage.setImageDrawable(context.getResources().getDrawable(drawables[new Random().nextInt(4)]));
        holder.appName.setText(resourceDto.getName());
        holder.learnTime.setText(Utils.parseSecondTime(resourceDto.getUseTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView appImage;
        private TextView appName;
        private TextView learnTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            appName = itemView.findViewById(R.id.app_name);
            learnTime = itemView.findViewById(R.id.learn_time);
            appImage = itemView.findViewById(R.id.app_img);
        }
    }
}
