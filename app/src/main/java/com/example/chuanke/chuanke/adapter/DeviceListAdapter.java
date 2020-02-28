package com.example.chuanke.chuanke.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.DeviceDetailActivity;
import com.example.chuanke.chuanke.activity.FileChooseActivity;
import com.example.chuanke.chuanke.activity.PlayTimeActivity;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.FileBean;
import com.example.chuanke.chuanke.bean.ScreenDetailBean;
import com.example.chuanke.chuanke.component.DateTimePickDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder>{
    private Context mContext;
    private List<ScreenDetailBean> mList;
    private Activity activity;

    private int fid;

    public DeviceListAdapter(Activity activity, List<ScreenDetailBean> List,int fid) {
        this.mList = List;
        this.activity = activity;
        this.fid = fid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final ScreenDetailBean screenDetailBean = mList.get(i);
        viewHolder.tv_device_address.setText(screenDetailBean.getSplace());
        viewHolder.tv_price.setText("￥"+screenDetailBean.getSprice() + "/h");
        String imgUrl = URL.BASE_DEVICE_PIC_URL + screenDetailBean.getSpic();
        viewHolder.iv_device_img.setImageURI(imgUrl);
        viewHolder.tv_file_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fid == -1){
                    Intent intent = new Intent(activity, FileChooseActivity.class);
                    intent.putExtra("sid",screenDetailBean.getSid()).putExtra("sprice",screenDetailBean.getSprice());
                    intent.putExtra("fid",fid);
                    view.getContext().startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, PlayTimeActivity.class);
                    intent.putExtra("sid",Integer.parseInt(screenDetailBean.getSid())).putExtra("sprice",screenDetailBean.getSprice());
                    intent.putExtra("fid",fid);
                    view.getContext().startActivity(intent);
                }
            }
        });
        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DeviceDetailActivity.class);
                intent.putExtra("sid",screenDetailBean.getSid());
                if(fid != -1){
                    intent.putExtra("fid",fid);
                }
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_device_img;
        TextView tv_device_address;
        TextView tv_price;
        TextView tv_file_put;
        TextView tv_type;
        LinearLayout ll_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_device_img = itemView.findViewById(R.id.iv_device_img);
            tv_device_address = itemView.findViewById(R.id.tv_device_address);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_file_put = itemView.findViewById(R.id.tv_file_put);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
}
