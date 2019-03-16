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

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.ViewHolder> {
    private Context mContext;
    private List<TemplateBean> mList;

    public TemplateListAdapter(List<TemplateBean> List) {
        this.mList=List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_template,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TemplateBean bean=mList.get(i);
        viewHolder.textView.setText(bean.getTname());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_template_img);
            textView=itemView.findViewById(R.id.tv_template);
        }
    }
}
