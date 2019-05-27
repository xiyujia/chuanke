package com.example.chuanke.chuanke.activity;

import android.view.View;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.TemplateDetailsAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.bean.LoginBean;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.example.chuanke.chuanke.model.LoginModel;

import java.util.ArrayList;
import java.util.List;

public class TemplateDetailsActivity extends BaseActivity {
    private List<TemplateBean> templateList=new ArrayList<>();
    private TemplateDetailsAdapter adapter;
    @Override
    protected void initSetting() {

    }

    @Override
    public int getLayoutFile() {
        return R.layout.activity_template_details;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
               // startActivity(RegActivity.class);
                break;

        }
    }
}

