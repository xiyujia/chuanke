package com.example.chuanke.chuanke.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.btn_login_login)
    Button btn_login;

    @BindView(R.id.tv_login_reg)
    TextView tv_reg;
    @BindView(R.id.tv_login_Reset)
    TextView tv_Reset;

    @BindView(R.id.iv_login_user)
    ImageView iv_user;
    @BindView(R.id.iv_login_key)
    ImageView iv_key;

    @BindView(R.id.et_login_user)
    EditText et_user;
    @BindView(R.id.et_login_key)
    EditText et_key;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setTopBarEnable(false);
    }

    @Override
    public void initEvent() {
        btn_login.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        tv_Reset.setOnClickListener(this);
        et_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_user.getText().toString().equals("")){
                    iv_user.setImageResource(R.drawable.user_input);
                    if(!et_key.getText().toString().equals("")){
                        iv_user.setImageResource(R.drawable.user_input);
                        iv_key.setImageResource(R.drawable.key_input);
                        btn_login.setTextColor(Color.parseColor("#059DD8"));
                    }else {
                        iv_key.setImageResource(R.drawable.key_empty);
                        btn_login.setTextColor(Color.parseColor("#999999"));
                    }
                }else {
                    iv_user.setImageResource(R.drawable.user_empty);
                    btn_login.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_key.getText().toString().equals("")){
                    iv_key.setImageResource(R.drawable.key_input);
                    if(!et_user.getText().toString().equals("")){
                        iv_user.setImageResource(R.drawable.user_input);
                        iv_key.setImageResource(R.drawable.key_input);
                        btn_login.setTextColor(Color.parseColor("#059DD8"));
                    }else{
                        iv_user.setImageResource(R.drawable.user_empty);
                        btn_login.setTextColor(Color.parseColor("#999999"));
                    }
                } else{
                    iv_key.setImageResource(R.drawable.key_empty);
                    btn_login.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
}
