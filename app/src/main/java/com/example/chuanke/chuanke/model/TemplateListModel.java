package com.example.chuanke.chuanke.model;

import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.BaseModel;
import com.example.chuanke.chuanke.bean.TemplateBean;

import java.util.List;

/**
 * Created by fu on 2019/5/27.
 */

public class TemplateListModel extends BaseModel<List<TemplateBean>> {
    public void getTemplateList(BaseListener<List<TemplateBean>> listener){
        call = service.getTemplateList();
        callEnqueue(call,listener);
    }
}
