package com.example.chuanke.chuanke.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;

/**

* 作者：张恺

* 时间：2019/1/28

* 类描述：个人资料

*/

public class PersonalActivity extends BaseActivity {


    @Override
    public int getLayoutFile() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView() {
        topBar.setText("个人资料");
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
