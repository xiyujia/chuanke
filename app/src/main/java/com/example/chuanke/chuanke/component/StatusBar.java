package com.example.chuanke.chuanke.component;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.WindowManager;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

/**
 * 状态栏设置工具类
 */
public class StatusBar {

    public static ImmersionBar immersionBar;//沉浸式状态栏
    /**
     * 初始化状态栏
     */
    public static void initImmersionBar(Activity activity) {
        if (immersionBar == null) {
            immersionBar = ImmersionBar.with(activity);
            immersionBar
                    .keyboardEnable(true)//解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                    .navigationBarWithKitkatEnable(false) //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                    .init();
        }
    }

    /**
     * 全屏
     */
    public static void initFullScreen(Activity activity) {
        if(immersionBar == null){
            immersionBar = ImmersionBar.with(activity);
        }
        immersionBar.reset().fullScreen(true).init();
    }

    /**
     * 全屏
     */
    public static void initPlayFullScreen(Activity activity) {
        if(immersionBar == null){
            immersionBar = ImmersionBar.with(activity);
        }
        immersionBar.reset().hideBar(BarHide.FLAG_HIDE_STATUS_BAR).fullScreen(true).init();
    }

    /**
     * 详情见下一方法
     */
    public void initImmersionBarForTopBar(View topBar,Activity activity) {
        initImmersionBarForTopBar(topBar, true,activity);
    }

    /**
     * 初始化状态栏:可拉伸图片状态栏
     * 使用“延伸TopBar”方案
     *
     * @param topBar              顶部View
     * @param isStatusBarDarkFont 是否使用深色字体
     */
    public static void initImmersionBarForTopBar(View topBar, boolean isStatusBarDarkFont,Activity activity) {
        initImmersionBar(activity);
        float statusAlpha = isStatusBarDarkFont ? 0.2f : 0;
        immersionBar
                .titleBar(topBar)
                .statusBarDarkFont(isStatusBarDarkFont, statusAlpha)
                .init();
    }

    /**
     * 初始化状态栏:纯色状态栏
     * 使用“改变状态栏颜色”方案
     *
     * @param statusBarColor      状态栏颜色
     * @param isStatusBarDarkFont 是否使用深色字体
     */
    public static void initImmersionBarOfColorBar(@ColorRes int statusBarColor, boolean isStatusBarDarkFont,Activity activity) {
        initImmersionBar(activity);
        immersionBar
                .fitsSystemWindows(true)
                .statusBarColor(statusBarColor)
                .statusBarDarkFont(isStatusBarDarkFont, 0.2f)
                .init();
    }

    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 20) {

            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 21) {

            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
