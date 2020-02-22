package com.example.chuanke.chuanke.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.FileBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class FileFragmentListAdapter extends RecyclerView.Adapter<FileFragmentListAdapter.ViewHolder>  implements View.OnClickListener{
    private Context mContext;
    private List<FileBean> mList;
    private Activity activity;

    private int uid;

    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public FileFragmentListAdapter(Activity activity, List<FileBean> List) {
        this.mList = List;
        this.activity = activity;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.NONE)
                .build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.file_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final FileBean bean = mList.get(i);
        viewHolder.fileName.setText(bean.getFname());
        viewHolder.tv_file_time.setText(bean.getFupdatetime());
        String imgUrl = URL.BASE_FILE_PIC_URL + bean.getFpic();
//        imageLoader.displayImage(imgUrl, viewHolder.iv_file_img, options);
        viewHolder.iv_file_img.setImageURI(imgUrl);
        viewHolder.itemView.setTag(i);
        viewHolder.tv_file_put.setTag(i);
        viewHolder.tv_file_delete.setTag(i);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        PRACTISE,
        ITEM
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.recyclerview:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
    //  删除数据
    public void removeData(int position) {
        mList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView iv_file_img;
        TextView fileName;
        TextView tv_file_time;
        TextView tv_file_put;
        TextView tv_file_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_file_img = itemView.findViewById(R.id.iv_file_img);
            fileName = itemView.findViewById(R.id.tv_file_name);
            tv_file_time = itemView.findViewById(R.id.tv_file_time);
            tv_file_put = itemView.findViewById(R.id.tv_file_put);
            tv_file_delete = itemView.findViewById(R.id.tv_file_delete);

            // 为ItemView添加点击事件
            tv_file_put.setOnClickListener(FileFragmentListAdapter.this);
            tv_file_delete.setOnClickListener(FileFragmentListAdapter.this);
            itemView.setOnClickListener(FileFragmentListAdapter.this);
        }
    }
}
