package com.example.chuanke.chuanke.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.barlibrary.ImmersionBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class TemplateDetailActivity extends AppCompatActivity {

    private SimpleDraweeView iv_pic;
    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_tratio;
    private TextView tv_price;
    private ImageView iv_back;
    private ImageView iv_collect;
    private LinearLayout ll_choose;
    private DisplayImageOptions options ;
    private ImageLoader imageLoader;

    private int tid;
    private String oprateType;

    private TemplateBean templateBean;

    public ImmersionBar immersionBar;//沉浸式状态栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);
        initImmersionBarOfColorBar(R.color.white, true);
        initView();
        initEvent();
    }

    public void initView() {
        tv_name = findViewById(R.id.name);
        tv_type = findViewById(R.id.tv_type);
        tv_tratio = findViewById(R.id.tv_tratio);
        tv_price = findViewById(R.id.tv_price);
        iv_pic = findViewById(R.id.iv_pic);
        iv_back = findViewById(R.id.iv_back);
        iv_collect = findViewById(R.id.iv_collect);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_choose = findViewById(R.id.ll_choose);
    }

    public void initEvent() {
        tid = getIntent().getIntExtra("tid", -1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tid", tid);
        jsonObject.put("uid",MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/template", jsonObject.toJSONString());
        ll_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TemplateDetailActivity.this, UploadActivity.class);
                intent.putExtra("tid", tid);
                oprateType = "template";
                intent.putExtra("oprateType", oprateType);
                startActivity(intent);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null && !result.equals("null") && !result.equals("")) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                templateBean = jsonObject.toJavaObject(TemplateBean.class);
                if(templateBean.getColletState() == 1){
                    iv_collect.setImageResource(R.drawable.collect_selected);
                } else {
                    iv_collect.setImageResource(R.drawable.collect_unselected);
                }
                tv_name.setText("标题："+templateBean.getTname());
                tv_type.setText("类型："+templateBean.getTtype());
                tv_tratio.setText("比例："+templateBean.getTtratio());
                tv_price.setText("价格："+templateBean.getTprice());
                imageLoader = ImageLoader.getInstance();
                options = new DisplayImageOptions.Builder()
                        .imageScaleType(ImageScaleType.NONE)
                        .build();
                imageLoader.init(ImageLoaderConfiguration.createDefault(TemplateDetailActivity.this));
                String picUrl = URL.BASE_TEMPLATE_PIC_URL + templateBean.getTpic();
                imageLoader.displayImage(picUrl,iv_pic,options);

                iv_collect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("uid", MyApplication.uid);
                        jsonObject1.put("tid",tid);
                        if(templateBean.getColletState() != 1){
                            HttpUtil.doJsonPost(handlerAddCollect, URL.BASE_URL + "api/add/collect", jsonObject1.toJSONString());
                        } else {
                            HttpUtil.doJsonPost(handlerDelCollect, URL.BASE_URL + "api/delete/collect", jsonObject1.toJSONString());
                        }

                    }
                });
            }
        }
    };
    Handler handlerDelCollect = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String)msg.obj;
            JSONObject jsonObject = JSONObject.parseObject(result);
            String msg1 = jsonObject.getString("msg");
            Toast.makeText(TemplateDetailActivity.this,msg1,Toast.LENGTH_SHORT).show();
            iv_collect.setImageResource(R.drawable.collect_unselected);
            refresh();
        }
    };
    Handler handlerAddCollect = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String)msg.obj;
            JSONObject jsonObject = JSONObject.parseObject(result);
            String msg1 = jsonObject.getString("msg");
            Toast.makeText(TemplateDetailActivity.this,msg1,Toast.LENGTH_SHORT).show();
            iv_collect.setImageResource(R.drawable.collect_selected);
            refresh();
        }
    };

    public void refresh(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tid", tid);
        jsonObject.put("uid",MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/template", jsonObject.toJSONString());
    }


    /**
     * 初始化状态栏:纯色状态栏
     * 使用“改变状态栏颜色”方案
     *
     * @param statusBarColor      状态栏颜色
     * @param isStatusBarDarkFont 是否使用深色字体
     */
    protected void initImmersionBarOfColorBar(@ColorRes int statusBarColor, boolean isStatusBarDarkFont) {
        if (immersionBar == null) {
            immersionBar = ImmersionBar.with(this);
            immersionBar
                    .keyboardEnable(true)//解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                    .navigationBarWithKitkatEnable(false) //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                    .init();
        }
        immersionBar
                .fitsSystemWindows(true)
                .statusBarColor(statusBarColor)
                .statusBarDarkFont(isStatusBarDarkFont, 0.2f)
                .init();
    }
}
