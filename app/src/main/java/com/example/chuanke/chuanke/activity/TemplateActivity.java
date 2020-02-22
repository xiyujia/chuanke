package com.example.chuanke.chuanke.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.TemplateListAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.example.chuanke.chuanke.model.TemplateListModel;
import com.example.chuanke.chuanke.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class TemplateActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<TemplateBean> filesList=new ArrayList<>();
    private TemplateListAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_template;
    }

    @Override
    public void initView() {
        topBar.setText("全部模板");

//        TemplateListModel  templateListModel = new TemplateListModel();
//        templateListModel.getTemplateList(new BaseListener<List<TemplateBean>>() {
//            @Override
//            public void onResponse(List<TemplateBean> templatelist) {
//                        filesList = templatelist;
//            }
//
//            @Override
//            public void onFail(String msg) {
//                Toast.makeText(TemplateActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
//            }
//        });

        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/template","");
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONArray jsonArray = JSONArray.parseArray(result);
                filesList = jsonArray.toJavaList(TemplateBean.class);

                recyclerView=findViewById(R.id.recyclerview);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter=new TemplateListAdapter(TemplateActivity.this,filesList);
                recyclerView.setAdapter(adapter);
            }
        }
    };

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
