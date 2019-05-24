package com.example.chuanke.chuanke.model;

import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.BaseModel;
import com.example.chuanke.chuanke.bean.OrderBean;

public class NewOrderModel extends BaseModel<OrderBean>{
    public void neworder(int uid, int sid,int fid,String opay,int osum,String ostarttime,String oendtime, BaseListener<OrderBean> listener) {
        call = service.neworder(uid, sid,fid,opay,osum,ostarttime,oendtime);
        callEnqueue(call, listener);
    }
}
