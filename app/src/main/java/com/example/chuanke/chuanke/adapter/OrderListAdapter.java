package com.example.chuanke.chuanke.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.OrderDetailsActivity;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.OrderBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

/**
 * Created by fu on 2019/3/16.
 */

public class OrderListAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderBean> mList;
    private Activity activity;
    OrderList holder = null;
//    private ImageLoader imageLoader;
//    private DisplayImageOptions options ;

    public OrderListAdapter(Activity activity, List<OrderBean> orderList) {
        this.mList = orderList;
        this.activity = activity;
//        imageLoader = new ImageLoader(activity.getApplicationContext());
//        imageLoader = ImageLoader.getInstance();
//        options = new DisplayImageOptions.Builder()
//                .imageScaleType(ImageScaleType.NONE)
//                .build();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new OrderList();
            view = LayoutInflater.from(activity).inflate(R.layout.item_myorder, null);
            holder.imgOffer = view.findViewById(R.id.iv_order_img);
            holder.tv_school_name = view.findViewById(R.id.tv_school_name);
//            holder.tv_play_status = view.findViewById(R.id.tv_play_status);
            holder.tv_device_address = view.findViewById(R.id.tv_device_address);
            holder.tv_order_starttime = view.findViewById(R.id.tv_order_starttime);
            holder.tv_order_endtime = view.findViewById(R.id.tv_order_endtime);
//            holder.tv_order_remark = view.findViewById(R.id.tv_order_remark);
            holder.tv_order_total = view.findViewById(R.id.tv_order_total);
//            holder.tv_order_price = view.findViewById(R.id.tv_order_price);
            holder.tv_order_again = view.findViewById(R.id.tv_order_again);
            holder.ll_item_whole  = view.findViewById(R.id.ll_item_whole);
            view.setTag(holder);

        } else {
            holder = (OrderListAdapter.OrderList) view.getTag();
        }

        final OrderBean orderBean = mList.get(i);
        String picUrl = URL.BASE_ORDER_PIC_URL+ orderBean.getFpic();
//        imageLoader.DisplayImage(picUrl, activity, holder.imgOffer);

//        imageLoader.displayImage(picUrl,holder.imgOffer,options);
        holder.imgOffer.setImageURI(picUrl);
        holder.tv_school_name.setText(orderBean.getFilename());
        holder.tv_device_address.setText(orderBean.getSplace());
        holder.tv_order_starttime.setText(orderBean.getOstarttime());
        holder.tv_order_endtime.setText(orderBean.getOendtime());
        holder.tv_order_total.setText(orderBean.getOsum()+"");
//        holder.tv_order_price.setText(orderBean.get);
        holder.tv_order_again.setText(orderBean.getOno());

        holder.ll_item_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("oid",orderBean.getOid());
                view.getContext().startActivity(intent);

            }
        });


        return view;
    }

    class OrderList {
        private SimpleDraweeView imgOffer;
        private TextView tv_school_name;
        //        private TextView tv_play_status;
        private TextView tv_device_address;
        private TextView tv_order_starttime;
        private TextView tv_order_endtime;
        //        private TextView tv_order_remark;
        private TextView tv_order_total;
        private TextView tv_order_price;
        private TextView tv_order_again;
        private LinearLayout ll_item_whole;
    }

}
