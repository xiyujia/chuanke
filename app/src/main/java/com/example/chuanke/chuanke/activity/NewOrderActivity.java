package com.example.chuanke.chuanke.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.NewOrderAdapter;
import com.example.chuanke.chuanke.adapter.OrderDetailsAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;
/**

 * 作者：炎炎

 * 时间：2019/5/10

 * 类描述：下单

 */
public class NewOrderActivity extends BaseActivity {
    private TabLayout tabLayout;
    private List<OrderBean> orderList=new ArrayList<>();
    private NewOrderAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_new_order;
    }

    @Override
    public void initView() {
        topBar.setText("新订单");
        RecyclerView recyclerView=findViewById(R.id.rv_new_order);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        OrderBean bean=new OrderBean();
        orderList.add(bean);


        adapter=new NewOrderAdapter(orderList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
//        orderList=new ArrayList<Fragment>();
//        fragmentList.add(new HomeFragment());
//        adapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
//        viewPager.setAdapter(adapter);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
//        tabLayout.getTabAt(0).setCustomView(R.layout.tab_file_fragment);
    }



    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}
