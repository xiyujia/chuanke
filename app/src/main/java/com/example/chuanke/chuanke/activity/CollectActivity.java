package com.example.chuanke.chuanke.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.HomeTemplateAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.example.chuanke.chuanke.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity {


    private GridView recyclerView;
    private TextView tv_null;
    private List<TemplateBean> filesList=new ArrayList<>();
    //    private TemplateListAdapter adapter;
    private HomeTemplateAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        topBar.setText("收藏模板");
        recyclerView=findViewById(R.id.gridview);
        tv_null = findViewById(R.id.tv_null);
        //发起请求，传递参数，开启响应线程
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/collect",jsonObject.toJSONString());
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                    tv_null.setVisibility(View.GONE);
                    try{
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = JSONArray.parseArray(result);
                        filesList = jsonArray.toJavaList(TemplateBean.class);

                        adapter=new HomeTemplateAdapter(CollectActivity.this,filesList);
                        recyclerView.setAdapter(adapter);
                    }catch (Exception e){
                        tv_null.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        //发起请求，传递参数，开启响应线程
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/collect", jsonObject.toJSONString());
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
