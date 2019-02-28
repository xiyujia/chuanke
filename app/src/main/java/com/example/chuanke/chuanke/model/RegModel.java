package com.example.chuanke.chuanke.model;

import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.BaseModel;
import com.example.chuanke.chuanke.bean.RegBean;

import retrofit2.http.Query;

public class RegModel extends BaseModel<RegBean> {
    public void reg(String name, String phone, String pwd ,BaseListener<RegBean> listener){
        call = service.reg(name, phone,pwd);
        callEnqueue(call, listener);
    }
}
