package com.example.chuanke.chuanke.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.OrderListAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

public class MyorderActivity extends BaseActivity {
    private List<OrderBean> orderList=new ArrayList<>();
    private OrderListAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_myorder;
    }

    @Override
    public void initView() {
        topBar.setText("我的订单");
        RecyclerView recyclerView=findViewById(R.id.rv_myorder_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        for (int i=0;i<20;i++){
            OrderBean bean=new OrderBean();
            bean.setOendtime("文件"+i);
            orderList.add(bean);
        }
        adapter=new OrderListAdapter(orderList);
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
