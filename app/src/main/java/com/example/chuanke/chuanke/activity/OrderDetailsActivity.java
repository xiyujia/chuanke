package com.example.chuanke.chuanke.activity;
/**

 * 作者：炎炎

 * 时间：2019/4/9

 * 类描述：订单详情

 */

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.OrderDetailsAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.chuanke.chuanke.URL.orderDetailsUrl;


public class OrderDetailsActivity extends BaseActivity {
    private List<OrderBean> orderList=new ArrayList<>();
    private OrderDetailsAdapter adapter;

//    private String id;
//    private String URL=orderDetailsUrl;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        topBar.setText("订单详情");
        RecyclerView recyclerView=findViewById(R.id.rv_order_details);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        OrderBean bean=new OrderBean();
        orderList.add(bean);


        adapter=new OrderDetailsAdapter(orderList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
//        id  = getIntent().getStringExtra("oid");//获取传递的资源id
    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}
