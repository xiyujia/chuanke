package com.example.chuanke.chuanke.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.chuanke.chuanke.R;


/**
 * 作者：杨烨
 * 时间：2018.9.15
 * 类描述：BaseFragment,Fragment基类
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    /*---------------------------- 初始化系列方法 ----------------------------*/
    public View view = null;
    public Context context;

    /**
     * 加载布局
     */
    abstract public @LayoutRes
    int getLayoutFile();

    /**
     * 初始化设置
     */
    abstract public void initSetting();

    /**
     * 初始化控件(findViewById)
     */
    abstract public void initView();

    /**
     * 初始化事件(setEvent)
     */
    abstract public void initEvent();

    /**
     * 初始化数据(setData)
     */
    abstract public void initData();

    /**
     * 默认初始化
     */
    protected void init() {
        context = getContext();
        showLog("class");
    }

    /**
     * 创建View
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null) {
            view = inflater.inflate(getLayoutFile(), container, false);
            init();
            initSetting();
            initView();
            initEvent();
            initData();
        }
        return view;
    }

    /*---------------------------- startActivity系列方法 ----------------------------*/

    /**
     * 页面跳转
     *
     * @param clz，目标Activity
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 带数据的页面跳转
     *
     * @param clz，目标Activity
     */
    public void startActivity(Class<?> clz, Intent intent) {
        if (intent != null) {
            intent.setClass(context, clz);
        } else {
            intent = new Intent(context, clz);
        }
        startActivity(intent);
    }


    /**
     * 带回调的页面跳转
     *
     * @param cls，目标Activity
     * @param requestCode，请求码
     */
    public void startActivityForResult(Class<?> cls, Intent intent, int requestCode) {
        if (intent != null) {
            intent.setClass(context, cls);
        } else {
            intent = new Intent(context, cls);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带回调的页面跳转 的回调方法
     *
     * @param requestCode，请求码
     * @param resultCode，返回码
     * @param data，返回数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * finish关闭页面
     */
    public void finish() {
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.anim_activity_left_in_slow, R.anim.anim_activity_right_out);
    }

    /*---------------------------- 输出toast和log ----------------------------*/

    /**
     * 输出Toast
     */
    public void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 输出Log
     */
    public void showLog(String text) {
        Log.d("ClassName:" + getClass().getName(), text);
    }

    /*---------------------------- 简化view.findViewById ----------------------------*/

    /**
     * 简化view.findViewById
     */
    public <T extends View> T findViewById(@IdRes int id) {
        return view.findViewById(id);
    }

    /*---------------------------- 隐藏/显示软键盘 ----------------------------*/

    /**
     * 隐藏软键盘
     */
    public void hideSoftKeyBoard() {
        Activity activity = (Activity) context;
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyBoard() {
        Activity activity = (Activity) context;
        if (activity == null) return;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /*---------------------------- 获取R.string内容 ----------------------------*/

    /**
     * 获取R.string内容
     *
     * @param id，string的id
     */
    @NonNull
    public String getStringForR(@StringRes int id) throws Resources.NotFoundException {
        return context.getResources().getString(id);
    }

    /**
     * 获取R.string内容
     *
     * @param id，string的id
     * @param formatArgs，参数列表
     */
    @NonNull
    public String getStringForR(@StringRes int id, Object... formatArgs) throws Resources.NotFoundException {
        return context.getResources().getString(id, formatArgs);
    }

    /*---------------------------- 获取R.dimens内容 ----------------------------*/

    /**
     * 获取R.dimens内容
     *
     * @param id，dimens的id
     * @return
     * @throws Resources.NotFoundException 特殊:获取sp:textView.setText(0,getDimensForR(R.dimens.xxx));
     */
    @NonNull
    public float getDimensForR(@DimenRes int id) throws Resources.NotFoundException {
        return context.getResources().getDimension(id);
    }
}
