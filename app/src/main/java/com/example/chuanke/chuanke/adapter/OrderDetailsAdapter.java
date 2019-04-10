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
import com.example.chuanke.chuanke.bean.OrderBean;

import java.util.List;


public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<OrderBean> mList;

    public OrderDetailsAdapter(List<OrderBean> List) {
        this.mList=List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.order_layout,viewGroup,false);
        return new OrderDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrderBean bean=mList.get(i);
//        viewHolder.orderid.setText(bean.getOid());
//        viewHolder.orderstate.setText(bean.getOstate());
//        viewHolder.orderStime.setText(bean.getOstarttime());
//        viewHolder.orderEtime.setText(bean.getOendtime());
//        viewHolder.ordernum.setText(bean.getOsum());
//        viewHolder.ordermoney.setText(bean.getOpay());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView im_order_details;
        TextView orderid;
        TextView orderstate;
        TextView orderStime;
        TextView orderEtime;
        TextView ordernum;
        TextView orderprice;
        TextView ordermoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            im_order_details=itemView.findViewById(R.id.im_order_details);
            orderid=itemView.findViewById(R.id.oid);
            orderstate=itemView.findViewById(R.id.ostate);
            orderStime=itemView.findViewById(R.id.oStime);
            orderEtime=itemView.findViewById(R.id.oEtime);
            ordernum=itemView.findViewById(R.id.onum);
            orderprice=itemView.findViewById(R.id.oprice);
            ordermoney=itemView.findViewById(R.id.omoney);
        }
    }
}