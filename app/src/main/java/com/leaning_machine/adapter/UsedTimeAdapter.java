package com.leaning_machine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.db.entity.UsedTimeEntity;
import com.leaning_machine.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class  UsedTimeAdapter extends RecyclerView.Adapter<UsedTimeAdapter.MyViewHolder>{
    private Context context;
    private List<UsedTimeEntity> list;
    private View inflater;

    public UsedTimeAdapter(Context context, List<UsedTimeEntity> list){
        this.context = context;
        this.list = list;
    }
    public UsedTimeAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<UsedTimeEntity> data) {
        this.list = data;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_total_time,parent,false);
        return new MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UsedTimeEntity usedTimeEntity = list.get(position);
        holder.dateView.setText(usedTimeEntity.getDate());

        holder.totalView.setText(Utils.parseDate(usedTimeEntity.getTotalLength()));
        holder.xue_pin_du.setText(Utils.parseDate(usedTimeEntity.getPinDuLength()));
        holder.mo_er_duo.setText(Utils.parseDate(usedTimeEntity.getErDuoLength()));
        holder.liu_li_shuo.setText(Utils.parseDate(usedTimeEntity.getLiuLiLength()));
        holder.ai_yue_du.setText(Utils.parseDate(usedTimeEntity.getYueDuLength()));
        holder.qin_lian_xi.setText(Utils.parseDate(usedTimeEntity.getLianXiLength()));
        holder.bei_dan_ci.setText(Utils.parseDate(usedTimeEntity.getDanCiLength()));
        holder.language.setText(Utils.parseDate(usedTimeEntity.getLanguageLength()));
        holder.math.setText(Utils.parseDate(usedTimeEntity.getMathLength()));
        holder.others.setText(Utils.parseDate(usedTimeEntity.getOtherLength()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView dateView;
        TextView totalView;
        TextView xue_pin_du;
        TextView mo_er_duo;
        TextView liu_li_shuo;
        TextView ai_yue_du;
        TextView qin_lian_xi;
        TextView bei_dan_ci;
        TextView language;
        TextView math;
        TextView others;

        public MyViewHolder(View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.date);
            totalView = itemView.findViewById(R.id.total_length);
            xue_pin_du = itemView.findViewById(R.id.xue_pin_du);
            mo_er_duo = itemView.findViewById(R.id.mo_er_duo);
            liu_li_shuo = itemView.findViewById(R.id.liu_li_shuo);
            ai_yue_du = itemView.findViewById(R.id.ai_yue_du);
            qin_lian_xi = itemView.findViewById(R.id.qin_lian_xi);
            bei_dan_ci = itemView.findViewById(R.id.bei_dan_ci);
            language = itemView.findViewById(R.id.language);
            math = itemView.findViewById(R.id.math);
            others = itemView.findViewById(R.id.others);
        }
    }
}
