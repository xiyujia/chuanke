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

/**
 * Created by fu on 2019/3/16.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private Context mContext;
    private List<OrderBean> mList;
    public OrderListAdapter(List<OrderBean> List){this.mList= List;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_myorder,viewGroup,false);
        return new OrderListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrderBean bean = mList.get(i);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView schoolName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolName=itemView.findViewById(R.id.tv_school_name);
        }
    }
}
