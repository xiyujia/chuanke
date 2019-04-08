package com.example.chuanke.chuanke.activity;

import android.view.View;
import android.widget.Button;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;

public class OrderSuccessActivity extends BaseActivity {
    private Button bt_re;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_order_success;
    }

    @Override
    public void initView() {
//        setTopBarEnable(false);
        topBar.setText("下单成功");
    }

    @Override
    public void initEvent() {
        bt_re = (Button)findViewById(R.id.bt_return);
        bt_re.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_return:
                finish();
                break;
        }
    }
}
