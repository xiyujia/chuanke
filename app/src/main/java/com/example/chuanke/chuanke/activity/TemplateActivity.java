package com.example.chuanke.chuanke.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.HomeTemplateAdapter;
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

    private GridView recyclerView;
    private List<TemplateBean> filesList=new ArrayList<>();
//    private TemplateListAdapter adapter;
    private HomeTemplateAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_template;
    }

    @Override
    public void initView() {
        topBar.setText("全部模板");
        //发起请求
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/template","");
    }
    //开启处理请求响应结果线程
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONArray jsonArray = JSONArray.parseArray(result);
                filesList = jsonArray.toJavaList(TemplateBean.class);
                recyclerView=findViewById(R.id.gridview);
                adapter=new HomeTemplateAdapter(TemplateActivity.this,filesList);
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
