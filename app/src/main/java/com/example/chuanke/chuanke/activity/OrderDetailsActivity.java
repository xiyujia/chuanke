package com.example.chuanke.chuanke.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.OrderDetailsAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.OrderBean;
import com.example.chuanke.chuanke.bean.ScreenDetailBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class OrderDetailsActivity extends BaseActivity {

    private int oid;
    private String orderDetailUrl = URL.BASE_URL + "api/Lists/orderDetail";
    OrderBean orderBean = new OrderBean();
    ScreenDetailBean screenDetailBean = new ScreenDetailBean();

    private TextView tvPlayTime;
    private TextView tvEndTime;
    private TextView tvPayState;
    private TextView tvPayMethod;
    private TextView tvPlace;
    private TextView tvScreenType;
    private TextView tvScreenSize,tv_devicestate;

    private ImageView ivAdMessage;
    private TextView tvFenbianlv;
    private LinearLayout ll_payfor;
    private LinearLayout ll_paymethod;

    private TextView tvOrderMoney;

    private ImageLoader imageLoader;


    @Override
    public int getLayoutFile() {
        return R.layout.activity_order_details;
    }

    @Override
    public void initView() {
        topBar.setText("订单详情");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        tvPlayTime = findViewById(R.id.tv_playtime);
        tvEndTime = findViewById(R.id.tv_endtime);
        tvPayState = findViewById(R.id.tv_paystate);
        tvPayMethod = findViewById(R.id.tv_pay_method);
        tvPlace = findViewById(R.id.tv_place);
        tvScreenType = findViewById(R.id.tv_device_type);
        tvScreenSize = findViewById(R.id.tv_screen_size);
        ivAdMessage = findViewById(R.id.iv_ad_message);
        tvFenbianlv = findViewById(R.id.tv_fenbianlv);
        tvOrderMoney = findViewById(R.id.tv_order_money);
        tvOrderMoney = findViewById(R.id.tv_order_money);
        tv_devicestate = findViewById(R.id.tv_devicestate);
        ll_payfor = findViewById(R.id.ll_payfor);
        ll_paymethod = findViewById(R.id.ll_paymethod);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        oid = getIntent().getIntExtra("oid",0);//获取传递的资源id
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("oid", oid);
        jsonObject.put("uid", MyApplication.uid);
        jsonObject.put("uid", 1);
        HttpUtil.doJsonPost(handler, orderDetailUrl, jsonObject.toJSONString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONObject json = JSONObject.parseObject(result);
                JSONObject order = json.getJSONObject("0");
                JSONObject screenDetail = json.getJSONArray("screenDetail").getJSONObject(0);
                orderBean = order.toJavaObject(OrderBean.class);
                screenDetailBean = screenDetail.toJavaObject(ScreenDetailBean.class);

                tvPlayTime.setText(orderBean.getOstarttime());
                tvEndTime.setText(orderBean.getOendtime());
                if("2".equals(orderBean.getOpaystate())){
                    tvPayState.setText("支付成功");
                    ll_paymethod.setVisibility(View.VISIBLE);
                    if(orderBean.getOpay() == "0"){
                        tvPayMethod.setText("支付宝");
                    } else {
                        tvPayMethod.setText("微信支付");
                    }
                } else {
                    tvPayState.setText("未支付");
                    tvPayState.setTextColor(0xffff883b);
                    ll_paymethod.setVisibility(View.GONE);
                    ll_payfor.setVisibility(View.VISIBLE);
                }
//                tvPayMethod.setText(orderBean.get());
                tvPlace.setText(screenDetailBean.getSplace());
                tvScreenType.setText(screenDetailBean.getStype());
                tvScreenSize.setText(screenDetailBean.getSratio());
                if(screenDetailBean.getSstate() == 0){
                    tv_devicestate.setText("设备异常");
                } else if(screenDetailBean.getSstate() == 1){
                    tv_devicestate.setText("设备正常");
                } else {
                    tv_devicestate.setText("正在播放");
                }

                String picUrl = URL.BASE_DEVICE_PIC_URL + screenDetailBean.getSpic();
//        imageLoader.DisplayImage(picUrl, activity, holder.imgOffer);

                imageLoader = ImageLoader.getInstance();

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .imageScaleType(ImageScaleType.NONE)
                        .build();
                imageLoader.init(ImageLoaderConfiguration.createDefault(OrderDetailsActivity.this));

                imageLoader.displayImage(picUrl,ivAdMessage,options);

                tvFenbianlv.setText(screenDetailBean.getSresolution());
                tvOrderMoney.setText("¥"+orderBean.getOsum());

            }
        }
    };

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}
