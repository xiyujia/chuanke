package com.example.chuanke.chuanke.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.EditFileActivity;
import com.example.chuanke.chuanke.activity.OrderDetailsActivity;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.ViewHolder> {
    private Context mContext;
    private List<TemplateBean> mList;
    public ImageLoader imageLoader;
    public Activity activity;

    public TemplateListAdapter(Activity activity, List<TemplateBean> List) {
        this.mList=List;
        imageLoader = ImageLoader.getInstance();
        this.activity = activity;
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
        final String imgUrl = "http://39.102.40.194/chuanke/public/static/Templateimages/" + bean.getTpic();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.NONE)
                .build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
        imageLoader.displayImage(imgUrl,viewHolder.imageView,options);
        viewHolder.ll_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditFileActivity.class);
                intent.putExtra("imgUrl",imgUrl);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout ll_template;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_template_img);
            textView=itemView.findViewById(R.id.tv_template);
            ll_template=itemView.findViewById(R.id.ll_template);
        }
    }
}
