package com.example.chuanke.chuanke.base;

/**

* 作者：张恺

* 时间：2019/2/15 

* 类描述：

*/


public interface BaseListener<T> {
    void onResponse(T t);

    void onFail(String msg);
}
