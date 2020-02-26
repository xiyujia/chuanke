package com.example.chuanke.chuanke.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.OrderDetailsActivity;
import com.example.chuanke.chuanke.activity.TemplateDetailActivity;
import com.example.chuanke.chuanke.activity.UploadActivity;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.OrderBean;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by fu on 2019/3/16.
 */

public class HomeTemplateAdapter extends BaseAdapter {

    private Context mContext;
    private List<TemplateBean> templateBeans;
    private Activity activity;
    private TemplateList holder = null;

    public HomeTemplateAdapter(Activity activity, List<TemplateBean> templateBeans) {
        this.templateBeans = templateBeans;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return templateBeans.size();
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
            holder = new TemplateList();
            view = LayoutInflater.from(activity).inflate(R.layout.gridview_item, null);
            holder.imgOffer = view.findViewById(R.id.img);
            holder.textView = view.findViewById(R.id.text);
            holder.ll_item_whole = view.findViewById(R.id.ll_item_whole);
            view.setTag(holder);

        } else {
            holder = (HomeTemplateAdapter.TemplateList) view.getTag();
        }

        final TemplateBean templateBean = templateBeans.get(i);
        String picUrl = URL.BASE_TEMPLATE_PIC_URL+ templateBean.getTpic();
        holder.imgOffer.setImageURI(picUrl);
        holder.textView.setText(templateBean.getTname());
        holder.ll_item_whole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, TemplateDetailActivity.class);
                intent.putExtra("tid",templateBean.getTid());
                view.getContext().startActivity(intent);

            }
        });
        return view;
    }

    class TemplateList {
        private SimpleDraweeView imgOffer;
        private LinearLayout ll_item_whole;
        private TextView textView;
    }

}
