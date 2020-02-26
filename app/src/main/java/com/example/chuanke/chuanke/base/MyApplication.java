package com.example.chuanke.chuanke.base;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**

* 作者：张恺

* 时间：2018/10/2

* 类描述：Application

*/

public class MyApplication extends Application {

    public static int uid=1;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

    }
}
