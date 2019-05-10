package com.example.chuanke.chuanke.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.bean.OrderBean;

import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    private Context mContext;
    private List<OrderBean> mList;

    public NewOrderAdapter(List<OrderBean> List) {
        this.mList=List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.neworder_layout,viewGroup,false);
        return new NewOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrderBean bean=mList.get(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView startime;
        TextView endtime;
        TextView money;
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startime=itemView.findViewById(R.id.newoStime);
            endtime = itemView.findViewById(R.id.newoEtime);
            money = itemView.findViewById(R.id.newomoney);
            text = itemView.findViewById(R.id.newotext);
        }
    }
}
