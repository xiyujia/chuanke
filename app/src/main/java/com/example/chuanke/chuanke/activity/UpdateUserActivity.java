package com.example.chuanke.chuanke.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.util.HttpUtil;

public class UpdateUserActivity extends BaseActivity {

    private String uname;
    private String uphone;
    private String uemail;

    private EditText et_input;
    private Button btn_submit;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_update_user;
    }

    @Override
    public void initView() {
        topBar.setText("修改信息");
        et_input = findViewById(R.id.et_input);
        btn_submit = findViewById(R.id.btn_submit);
        Intent intent = getIntent();
        uname = intent.getStringExtra("uname");
        uphone = intent.getStringExtra("uphone");
        uemail = intent.getStringExtra("uemail");
        if("".equals(uemail)){
            topBar.setText("修改邮箱");
        } else if("".equals(uname)){
            topBar.setText("修改用户名");
        } else if("".equals(uphone)){
            topBar.setText("修改手机号");
        }
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(et_input.getText().toString().trim())){
                    if("".equals(uemail)){
                        uemail = et_input.getText().toString().trim();
                    } else if("".equals(uname)){
                        uname = et_input.getText().toString().trim();
                    } else if("".equals(uphone)){
                        uphone = et_input.getText().toString().trim();
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("uemail",uemail);
                    jsonObject.put("uname",uname);
                    jsonObject.put("uphone",uphone);
                    jsonObject.put("uid", MyApplication.uid);
                    HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/update/user",jsonObject.toJSONString());
                } else {
                    Toast.makeText(UpdateUserActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            JSONObject jsonObject = JSONObject.parseObject(result);
            String status = jsonObject.getString("status");
            if("1".equals(status)){
                Toast.makeText(UpdateUserActivity.this,"信息修改成功",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UpdateUserActivity.this,"出错了，请重试",Toast.LENGTH_SHORT).show();
            }
        }
    };

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
    public void onClick(View view) {

    }
}
