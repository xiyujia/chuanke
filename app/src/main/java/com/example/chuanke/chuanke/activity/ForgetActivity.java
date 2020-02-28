package com.example.chuanke.chuanke.activity;

import android.graphics.Color;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.example.chuanke.chuanke.util.Phone;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class ForgetActivity extends BaseActivity{
    private int time;

    @BindView(R.id.btn_captcha)
    Button btn_captcha;//验证码

    @BindView(R.id.et_mobile)//邮箱
    EditText et_phone;

    String captcha;

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
        btn_captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String mobile = et_phone.getText().toString();
                //判断是否密码或者用户名为空
                if ("".equals(mobile) || !mobile.contains(".") || !mobile.contains("com") || !mobile.contains("@")) {
                    Toast.makeText(ForgetActivity.this,"请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject json = new JSONObject();
                    json.put("toemail", mobile);

                    //post方法登录是否成功
//                    HttpUtil.doJsonPost(handlerSms, Const.SERVER + "/sendSms", json.toJSONString());
                    HttpUtil.doJsonPost(captchaHandler, URL.BASE_URL + "api/mail/email", json.toJSONString());

                    btn_captcha.setClickable(false);
                    new CountDownTimer(60000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            btn_captcha.setText(String.format("%d秒后重新发送", millisUntilFinished / 1000));
                        }

                        public void onFinish() {
                            btn_captcha.setText("发送邮件");
                            btn_captcha.setClickable(true);
                        }
                    }.start();
                }
            }
        });
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

    Handler captchaHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String)msg.obj;
            JSONObject object = JSONObject.parseObject(result);
            String status = object.getString("error");
            String msg1 = object.getString("msg");
            if("1".equals(status)){
                Toast.makeText(ForgetActivity.this,msg1, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ForgetActivity.this,msg1, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
