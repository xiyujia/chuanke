package com.example.chuanke.chuanke.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chuanke.chuanke.R;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;


import butterknife.ButterKnife;

/**

* 作者：张恺

* 时间：2019/1/26

* 类描述：activity基类

*/


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public Intent intent;
    public ImmersionBar immersionBar;//沉浸式状态栏

    public TopBar topBar;
    private LinearLayout rootView;

    public boolean topBarEnable=true;  //是否启动topbar

    /**
     * 加载布局
     */
    abstract public @LayoutRes
    int getLayoutFile();

    /**
     * 初始化控件(findViewById)
     */
    abstract public void initView();

    /**
     * 初始化事件(setEvent)
     */
    abstract public void initEvent();

    /**
     * 初始化事件(setEvent)
     */
    abstract public void initData();

    /**
     * 创建Activity和初始化
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        topBar=findViewById(R.id.topbar);
        initSetting();
        ButterKnife.bind(this);
        showLog("class");
        initView();
        if (!topBarEnable) {
            topBar.setVisibility(View.GONE);
        } else {
            initImmersionBarForTopBar(topBar);
        }
        initEvent();
        initData();
    }

    protected abstract void initSetting();

    public void setTopBarEnable(boolean topBarEnable) {
        this.topBarEnable = topBarEnable;
    }

    /**
     * 初始化topbar
     */
    protected void init(){ setContentView(R.layout.activity_base);
        rootView=findViewById(R.id.root);
        View layout = LayoutInflater.from(this).inflate(getLayoutFile(), null);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        rootView.addView(layout);
        showLog("class");
        intent = getIntent();
    }


    public Context getContext() {
        return this;
    }

    /**
     * 显示Toast
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Log
     */
    public void showLog(String text) {
        Log.d(getClass().getName(), text);
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 初始化状态栏
     */
    private void initImmersionBar() {
        if (immersionBar == null) {
            immersionBar = ImmersionBar.with(this);
            immersionBar
                    .keyboardEnable(true)//解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                    .navigationBarWithKitkatEnable(false) //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                    .init();
        }
    }

    /**
     * 全屏
     */
    protected void initFullScreen() {
        if(immersionBar == null){
            immersionBar = ImmersionBar.with(this);
        }
        immersionBar.reset().fullScreen(true).init();
    }

    /**
     * 全屏
     */
    protected void initPlayFullScreen() {
        if(immersionBar == null){
            immersionBar = ImmersionBar.with(this);
        }
        immersionBar.reset().hideBar(BarHide.FLAG_HIDE_STATUS_BAR).fullScreen(true).init();
    }



    /**
     * 详情见下一方法
     */
    protected void initImmersionBarForTopBar(View topBar) {
        initImmersionBarForTopBar(topBar, true);
    }

    /**
     * 初始化状态栏:可拉伸图片状态栏
     * 使用“延伸TopBar”方案
     *
     * @param topBar              顶部View
     * @param isStatusBarDarkFont 是否使用深色字体
     */
    protected void initImmersionBarForTopBar(View topBar, boolean isStatusBarDarkFont) {
        initImmersionBar();
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
    protected void initImmersionBarOfColorBar(@ColorRes int statusBarColor, boolean isStatusBarDarkFont) {
        initImmersionBar();
        immersionBar
                .fitsSystemWindows(true)
                .statusBarColor(statusBarColor)
                .statusBarDarkFont(isStatusBarDarkFont, 0.2f)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null) {
            immersionBar.destroy();
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyBoard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView()
                    .getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftKeyBoard(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        //退出动画效果
        overridePendingTransition(R.anim.anim_activity_left_in_slow, R.anim.anim_activity_right_out);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取R.string内容
     *
     * @param id R.string的id
     */
    @NonNull
    public String getStringText(@StringRes int id) throws Resources.NotFoundException {
        return getContext().getResources().getString(id);
    }

    /**
     * 获取R.string内容
     *
     * @param id         R.string的id
     * @param formatArgs string的参数列表
     */
    @NonNull
    public String getStringText(@StringRes int id, Object... formatArgs) throws Resources.NotFoundException {
        return getContext().getResources().getString(id, formatArgs);
    }
    /**
     * 获取R.dimens内容
     *
     * @param id，dimens的id
     * @return
     * @throws Resources.NotFoundException 特殊:获取sp:textView.setText(0,getDimensForR(R.dimens.xxx));
     */
    public float getDimensForR(@DimenRes int id) throws Resources.NotFoundException {
        return getContext().getResources().getDimension(id);
    }
}