package com.example.chuanke.chuanke.activity;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.bean.LoginBean;
import com.example.chuanke.chuanke.bean.NewOrderBean;
import com.example.chuanke.chuanke.model.LoginModel;
import com.example.chuanke.chuanke.model.NewOrderModel;

import butterknife.BindView;

/**

 * 作者：炎炎

 * 时间：2019/5/10

 * 类描述：下单

 */
public class NewOrderActivity extends BaseActivity {
    @BindView(R.id.newoStime)
    EditText startime;
    @BindView(R.id.newoEtime)
    EditText endtime;
    @BindView(R.id.newomoney)
    TextView money;
    @BindView(R.id.newotext)
    EditText text;
    @BindView(R.id.bt_new)
    Button bt_new;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_new_order;
    }

    @Override
    public void initView() {
        topBar.setText("新订单");

    }

    @Override
    public void initEvent() {
        bt_new.setOnClickListener(this);
        money.setText("10");
        startime.setText("2019-06-05 13:00:00");
        endtime.setText("2019-06-05 14:00:00");
        text.setText("4");
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
        final String ostartime = startime.getText().toString();
        final String oendtime = endtime.getText().toString();
        final String omoney = money.getText().toString();
        final String otext = text.getText().toString();
        switch (v.getId()) {
            case R.id.bt_new:
                new NewOrderModel().neworder(1,3,otext,1,"10",ostartime,oendtime, new BaseListener<NewOrderBean>() {

                    @Override
                    public void onResponse(NewOrderBean orderBean) {
                        if (orderBean.getStatus().equals("1")){
                            startActivity(OrderSuccessActivity.class);
                            finish();
                        }else {
                            showToast(orderBean.getMsg());
                        }
                    }

                    @Override
                    public void onFail(String msg) {

                    }

                });
                break;
        }
    }
}
