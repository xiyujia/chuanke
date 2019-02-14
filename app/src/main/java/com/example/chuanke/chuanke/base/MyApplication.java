package com.example.chuanke.chuanke.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**

* 作者：张恺

* 时间：2018/10/2

* 类描述：Application

*/

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
