package com.example.chuanke.chuanke;

import android.view.View;
import android.widget.Button;

import com.example.chuanke.chuanke.activity.ForgetActivity;
import com.example.chuanke.chuanke.activity.HomeActivity;
import com.example.chuanke.chuanke.activity.LoginActivity;
import com.example.chuanke.chuanke.activity.MyorderActivity;
import com.example.chuanke.chuanke.activity.OrderDetailsActivity;
import com.example.chuanke.chuanke.activity.PersonalActivity;
import com.example.chuanke.chuanke.activity.RegActivity;
import com.example.chuanke.chuanke.activity.TemplateActivity;
import com.example.chuanke.chuanke.base.BaseActivity;

import butterknife.BindView;

/**

* 作者：张恺

* 时间：2019/1/26 

* 类描述：

*/

public class MainActivity extends BaseActivity {
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.reg)
    Button reg;
    @BindView(R.id.forget)
    Button forget;
    @BindView(R.id.personal)
    Button personal;
    @BindView(R.id.home)
    Button home;
    @BindView(R.id.template)
    Button template;
    @BindView(R.id.myorder)
    Button myorder;
    @BindView(R.id.orderSuccess)
    Button orderSuccess;
    @BindView(R.id.orderDetails)
    Button orderDetails;
    @BindView(R.id.orderNew)
    Button newOreder;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setTopBarEnable(false);
    }

    @Override
    public void initEvent() {
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
        forget.setOnClickListener(this);
        personal.setOnClickListener(this);
        home.setOnClickListener(this);
        template.setOnClickListener(this);
        myorder.setOnClickListener(this);
        orderSuccess.setOnClickListener(this);
        orderDetails.setOnClickListener(this);
        newOreder.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivity(LoginActivity.class);
                break;
            case R.id.reg:
                startActivity(RegActivity.class);
                break;
            case R.id.forget:
                startActivity(ForgetActivity.class);
                break;
            case R.id.personal:
                startActivity(PersonalActivity.class);
                break;
            case R.id.home:
                startActivity(HomeActivity.class);
                break;
            case R.id.template:
                startActivity(TemplateActivity.class);
                break;
            case R.id.myorder:
                startActivity(MyorderActivity.class);
                break;
            case R.id.orderDetails:
                startActivity(OrderDetailsActivity.class);
                break;
            }
    }
}
