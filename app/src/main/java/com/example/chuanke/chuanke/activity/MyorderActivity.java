package com.example.chuanke.chuanke.activity;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.OrderListAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.OrderBean;
import com.example.chuanke.chuanke.util.CustomListView;
import com.example.chuanke.chuanke.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class MyorderActivity extends BaseActivity implements CustomListView.ILoadMoreListener {
    private List<OrderBean> orderList = new ArrayList<>();
    private OrderListAdapter adapter;
    private OrderBean orderBean = new OrderBean();
    private OrderListAdapter orderListAdapter;
    private CustomListView recyclerView;
    private TabLayout tab_layout;
    private TextView tv_null;

    private int playState = -1;//待播放0，正在播放1，播放完成2
    private int payState = 0;//待付款0，已取消2
    private JSONObject params = new JSONObject();//请求订单类型传参
    private String url1 = "api/Lists/findOrderByPayState";
    private String url2 = "api/Lists/findOrderByPlayState";
    private String url = url1;


    @Override
    public int getLayoutFile() {
        return R.layout.activity_myorder;
    }

    @Override
    public void initView() {
        topBar.setText("我的订单");
        recyclerView = findViewById(R.id.rv_myorder_list);
        tab_layout = findViewById(R.id.tab_layout);
        tv_null = findViewById(R.id.tv_null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        params.put("uid", MyApplication.uid);
        initTabLayout();
    }


    private void initTabLayout() {
        List<String> orderList = new ArrayList<>();
        orderList.add("待付款");
        orderList.add("待播放");
        orderList.add("正在播放");
        orderList.add("播放完成");
        orderList.add("已取消");
        tab_layout.removeAllTabs();
        for (int i = 0; i < orderList.size(); i++) {
            TabLayout.Tab tab = tab_layout.newTab();
            View view = LayoutInflater.from(this).inflate(R.layout.circle_second_tab_item, null);
            TextView tv_title = view.findViewById(R.id.tv_title);
            tv_title.setText(orderList.get(i));
            tv_title.setTextSize(13);
            if (i == 0) {
                tv_title.setTextColor(Color.WHITE);
                tv_title.setBackgroundResource(R.drawable.message_type_selected);
                payState = 0;
                params.put("opaystate", payState);
                params.remove("oplaystate");
                HttpUtil.doJsonPost(handlerOrderList, URL.BASE_URL + url, JSONObject.toJSONString(params));
            }
            tab.setCustomView(view);
            tab_layout.addTab(tab);
        }
        tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_title.setTextColor(Color.WHITE);
                tv_title.setBackgroundResource(R.drawable.message_type_selected);

                setSupply();
                HttpUtil.doJsonPost(handlerOrderList, URL.BASE_URL + url, JSONObject.toJSONString(params));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView tv_title = view.findViewById(R.id.tv_title);
                tv_title.setTextColor(getResources().getColor(R.color.gray));
                tv_title.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setSupply() {
        if (tab_layout.getSelectedTabPosition() == 0) {
            payState = 0;
            url = url1;
            params.put("opaystate", payState);
            params.remove("oplaystate");
        } else if (tab_layout.getSelectedTabPosition() == 1) {
            playState = 0;
            url = url2;
            params.put("oplaystate", playState);
            params.remove("opaystate");
        } else if (tab_layout.getSelectedTabPosition() == 2) {
            playState = 1;
            url = url2;
            params.put("oplaystate", playState);
            params.remove("opaystate");
        } else if (tab_layout.getSelectedTabPosition() == 3) {
            playState = 2;
            url = url2;
            params.put("oplaystate", playState);
            params.remove("opaystate");
        } else if (tab_layout.getSelectedTabPosition() == 4) {
            payState = 2;
            url = url1;
            params.put("opaystate", payState);
            params.remove("oplaystate");
        }
    }

    //创建一个handler
    Handler handlerOrderList = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONArray item = JSONArray.parseArray(result);
                if(item.size() != 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_null.setVisibility(View.GONE);
                    orderList.clear();
                    orderList = item.toJavaList(OrderBean.class);

                    orderListAdapter = new OrderListAdapter(MyorderActivity.this, orderList);
                    recyclerView.setAdapter(orderListAdapter);
                    recyclerView.deferNotifyDataSetChanged();
                } else {
                    tv_null.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void loadMore() {
    }
}
