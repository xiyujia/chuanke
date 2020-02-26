package com.example.chuanke.chuanke.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.CollectActivity;
import com.example.chuanke.chuanke.activity.MyorderActivity;
import com.example.chuanke.chuanke.activity.PersonalActivity;
import com.example.chuanke.chuanke.base.BaseFragment;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.base.UserBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import static com.example.chuanke.chuanke.component.StatusBar.initImmersionBarOfColorBar;

public class MineFragment extends BaseFragment {

    private ImageView ll_persion;
    private LinearLayout ll_my_order;
    private LinearLayout ll_setting;
    private LinearLayout ll_my_template;
    private TextView tv_username;
    private SimpleDraweeView iv_portrait;

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initSetting() {

    }

    @Override
    public void initView() {
        ll_persion = findViewById(R.id.ll_mine_persion);
        ll_my_order = findViewById(R.id.ll_my_order);
        ll_setting = findViewById(R.id.ll_setting);
        ll_my_template = findViewById(R.id.ll_my_template);
        tv_username = findViewById(R.id.tv_username);
        iv_portrait = findViewById(R.id.iv_portrait);
    }

    @Override
    public void initEvent() {
        ll_persion.setOnClickListener(this);
        ll_my_order.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_my_template.setOnClickListener(this);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/user/getuser", jsonObject.toJSONString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (!"".equals(result) && null != result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                UserBean userBean = jsonObject.toJavaObject(UserBean.class);
                tv_username.setText(userBean.getUname());
                iv_portrait.setImageURI(URL.BASE_USER_PIC_URL + userBean.getUpic());
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/user/getuser", jsonObject.toJSONString());
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mine_persion:
                startActivity(PersonalActivity.class);
                break;
            case R.id.ll_my_order:
                startActivity(MyorderActivity.class);
                break;
            case R.id.ll_setting:
                startActivity(PersonalActivity.class);
                break;
            case R.id.ll_my_template:
                startActivity(CollectActivity.class);
                break;
        }
    }
}
