package com.example.chuanke.chuanke.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.bean.TemplateBean;

import java.util.List;

/**
 * Created by fu on 2019/5/27.
 */

public class TemplateDetailsAdapter extends RecyclerView.Adapter<TemplateDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<TemplateBean> mList;

    public TemplateDetailsAdapter(List<TemplateBean> List) {
        this.mList=List;
    }

    @NonNull
    @Override
    public TemplateDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_template_details,viewGroup,false);
        return new TemplateDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateDetailsAdapter.ViewHolder viewHolder, int i) {
       TemplateBean templateBean  = mList.get(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView temp_img;
        TextView name_tv;
        TextView type_tv;
        TextView proportion_tv;
        TextView price_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            temp_img = itemView.findViewById(R.id.temp_img);
            name_tv = itemView.findViewById(R.id.name_tv);
            type_tv = itemView.findViewById(R.id.type_tv);
            proportion_tv = itemView.findViewById(R.id.proportion_tv);
            price_tv = itemView.findViewById(R.id.price_tv);

        }
    }
}
