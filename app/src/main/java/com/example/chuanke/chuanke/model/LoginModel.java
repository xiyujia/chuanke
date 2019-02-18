package com.example.chuanke.chuanke.model;

import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.BaseModel;
import com.example.chuanke.chuanke.bean.LoginBean;

public class LoginModel extends BaseModel<LoginBean> {
    public void login(String mobile, String pswd, final BaseListener listener) {
        call = service.login(mobile, pswd);
        callEnqueue(call, listener);
    }


    }

