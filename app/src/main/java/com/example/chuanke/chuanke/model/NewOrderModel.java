package com.example.chuanke.chuanke.model;

import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.BaseModel;
import com.example.chuanke.chuanke.bean.NewOrderBean;

public class NewOrderModel extends BaseModel<NewOrderBean>{
    public void neworder(int uid, int sid,int fid,int opay,String osum,String ostarttime,String oendtime, BaseListener<NewOrderBean> listener) {
        call = service.neworder(uid, sid,fid,opay,osum,ostarttime,oendtime);
        callEnqueue(call, listener);
    }
}
