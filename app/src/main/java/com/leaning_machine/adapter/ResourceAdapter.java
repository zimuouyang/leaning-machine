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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leaning_machine.R;
import com.leaning_machine.activity.WebViewActivity;
import com.leaning_machine.base.dto.ResourceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.MyViewHolder>{
    private Context context;
    private List<ResourceDto> list;
    private View inflater;
    @SuppressLint("UseCompatLoadingForDrawables")
    private int[] drawables = new int[]{R.mipmap.item_icon_1, R.mipmap.item_icon_2, R.mipmap.item_icon_3, R.mipmap.item_icon_4 };

    public ResourceAdapter(Context context, List<ResourceDto> list){
        this.context = context;
        this.list = list;
    }
    public ResourceAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setData(List<ResourceDto> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context).inflate(R.layout.item_resource,parent,false);
        return new ResourceAdapter.MyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResourceDto resourceDto = list.get(position);
        holder.resourceImage.setImageDrawable(context.getResources().getDrawable(drawables[new Random().nextInt(4)]));
        holder.resourceTitle.setText(resourceDto.getResourceName());
        holder.resourceDes.setText(resourceDto.getDes());
        holder.readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(resourceDto.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
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
