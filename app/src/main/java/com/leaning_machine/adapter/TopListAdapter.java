package com.leaning_machine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.base.dto.TopListModel;

import java.util.ArrayList;
import java.util.List;

public class TopListAdapter extends RecyclerView.Adapter<TopListAdapter.MyViewHolder>{
    private Context context;
    private List<TopListModel> list;
    private View inflater;

    public TopListAdapter(Context context, List<TopListModel> list){
        this.context = context;
        this.list = list;
    }
    public TopListAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<TopListModel> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_top_list,parent,false);
        return new TopListAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TopListModel topListDto = list.get(position);
        holder.nameText.setText(topListDto.getUserName());
        holder.timeText.setText(context.getString(R.string.study_time, topListDto.getTime() / 60));
        holder.rankingText.setText(String.valueOf(topListDto.getIndex()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView timeText;
        private TextView rankingText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name);
            timeText = itemView.findViewById(R.id.time);
            rankingText = itemView.findViewById(R.id.ranking);
        }
    }
}
