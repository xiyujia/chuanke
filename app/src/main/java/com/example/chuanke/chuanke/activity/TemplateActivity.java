package com.example.chuanke.chuanke.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.TemplateListAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.bean.TemplateBean;

import java.util.ArrayList;
import java.util.List;

public class TemplateActivity extends BaseActivity {

    private List<TemplateBean> filesList=new ArrayList<>();
    private TemplateListAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_template;
    }

    @Override
    public void initView() {
        topBar.setText("全部模板");
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        for (int i=0;i<20;i++){
            TemplateBean bean=new TemplateBean();
            bean.setTname("模板"+i);
            filesList.add(bean);
        }

        adapter=new TemplateListAdapter(filesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}