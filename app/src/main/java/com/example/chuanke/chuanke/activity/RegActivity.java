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

public class RegActivity extends BaseActivity {

    @BindView(R.id.btn_reg_reg)
    Button btn_reg;

    @BindView(R.id.tv_reg_reg)
    TextView tv_reg;
    @BindView(R.id.tv_reg_Agreement)
    TextView tv_Agreement;

    @BindView(R.id.iv_reg_user)
    ImageView iv_user;
    @BindView(R.id.iv_reg_key)
    ImageView iv_key;
    @BindView(R.id.iv_reg_key1)
    ImageView iv_key1;
    @BindView(R.id.iv_reg_phone)
    ImageView iv_phone;

    @BindView(R.id.et_reg_user)
    EditText et_user;
    @BindView(R.id.et_reg_key)
    EditText et_key;
    @BindView(R.id.et_reg_key1)
    EditText et_key1;
    @BindView(R.id.et_reg_phone)
    EditText et_phone;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_reg;
    }

    @Override
    public void initView() {
        topBar.setText("注册");
        tv_reg.setText("点击\"注册\"按钮,即表示同意");
    }

    @Override
    public void initEvent() {
        tv_Agreement.setOnClickListener(this);
        btn_reg.setOnClickListener(this);
        et_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et_user.getText().toString().equals("")&&!et_key.getText().toString().equals("")&&
                        !et_key1.getText().toString().equals("")&&
                        !et_phone.getText().toString().equals("")){

                    setInput();
                }else {
                    setImage();
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
                if(!et_user.getText().toString().equals("")&&!et_key.getText().toString().equals("")&&
                        !et_key1.getText().toString().equals("")&&
                        !et_phone.getText().toString().equals("")){

                    setInput();
                }else {
                    setImage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_key1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et_user.getText().toString().equals("")&&!et_key.getText().toString().equals("")&&
                        !et_key1.getText().toString().equals("")&&
                        !et_phone.getText().toString().equals("")){

                    setInput();
                }else {
                    setImage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!et_user.getText().toString().equals("")&&!et_key.getText().toString().equals("")&&
                        !et_key1.getText().toString().equals("")&&
                        !et_phone.getText().toString().equals("")){

                    setInput();
                }else {
                    setImage();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setImage() {
        if (!et_user.getText().toString().equals("")){
            iv_user.setImageResource(R.drawable.user_input);
        }else
            iv_user.setImageResource(R.drawable.user_empty);
        if(!et_key.getText().toString().equals("")){
            iv_key.setImageResource(R.drawable.key_input);
        }else
            iv_key.setImageResource(R.drawable.key_empty);
        if (!et_key1.getText().toString().equals("")){
            iv_key1.setImageResource(R.drawable.key_input);
        }else
            iv_key1.setImageResource(R.drawable.key_empty);
        if (!et_phone.getText().toString().equals("")){
            iv_phone.setImageResource(R.drawable.phone_input);
        }else
            iv_phone.setImageResource(R.drawable.phone_empty);
        btn_reg.setTextColor(Color.parseColor("#999999"));
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
    private void setInput(){
        iv_user.setImageResource(R.drawable.user_input);
        iv_key.setImageResource(R.drawable.key_input);
        iv_key1.setImageResource(R.drawable.key_input);
        iv_phone.setImageResource(R.drawable.phone_input);
        btn_reg.setTextColor(Color.parseColor("#059DD8"));
    }
}
