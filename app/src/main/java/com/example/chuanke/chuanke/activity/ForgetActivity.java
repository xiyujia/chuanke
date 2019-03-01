package com.example.chuanke.chuanke.activity;

import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.util.Phone;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**

* 作者：张恺

* 时间：2019/1/28

* 类描述：忘记密码

*/

public class ForgetActivity extends BaseActivity {
    private int time;
    @BindView(R.id.tv_forget_send)
    TextView tv_send;

    @BindView(R.id.et_forget_phone)
    EditText et_phone;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_forget;
    }

    @Override
    public void initView() {
        topBar.setText("重置密码");
    }

    @Override
    public void initEvent() {
        tv_send.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {
        final String mobile = et_phone.getText().toString();
        switch (v.getId()){
            case R.id.tv_forget_send:
                if (Phone.isMobile(mobile)){
                    time = 60;
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (time <= 0) {
                                            tv_send.setText("验证码");
                                            tv_send.setClickable(true);
                                            tv_send.setTextColor(Color.parseColor("#000000"));
                                            timer.cancel();
                                        } else {
                                            tv_send.setText(time-- + "s");
                                            tv_send.setTextColor(Color.parseColor("#999999"));
                                            tv_send.setClickable(false);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }, 0, 1000);

            }
                break;
        }
    }
}
