package com.example.chuanke.chuanke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chuanke.chuanke.activity.ForgetActivity;
import com.example.chuanke.chuanke.activity.LoginActivity;
import com.example.chuanke.chuanke.activity.PersonalActivity;
import com.example.chuanke.chuanke.activity.RegActivity;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.TopBar;

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
            }
    }
}
