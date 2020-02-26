package com.example.chuanke.chuanke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String opay;
    private double osum;
    private OrderBean orderBean = new OrderBean();
    private ScreenDetailBean screenDetailBean = new ScreenDetailBean();

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
        oid = getIntent().getIntExtra("oid",-1);//获取传递的资源id
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("oid", oid);
        jsonObject.put("uid", MyApplication.uid);
        HttpUtil.doJsonPost(handler, orderDetailUrl, jsonObject.toJSONString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                final JSONObject json = JSONObject.parseObject(result);
                if(!"".equals(json.getString("error")) && null != json.getString("error")){
                    Toast.makeText(OrderDetailsActivity.this,json.getString("msg"),Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                JSONObject order = json.getJSONObject("0");
                JSONObject screenDetail = json.getJSONArray("screenDetail").getJSONObject(0);
                orderBean = order.toJavaObject(OrderBean.class);
                screenDetailBean = screenDetail.toJavaObject(ScreenDetailBean.class);
                osum = orderBean.getOsum();
                tvPlayTime.setText(orderBean.getOstarttime());
                tvEndTime.setText(orderBean.getOendtime());
                if("1".equals(orderBean.getOpaystate())){
                    tvPayState.setText("支付成功");
                    tvPayState.setTextColor(0xff85E266);
                    ll_paymethod.setVisibility(View.VISIBLE);
                    ll_payfor.setVisibility(View.GONE);
                    if("1".equals(orderBean.getOpay())){
                        tvPayMethod.setText("支付宝");
                    } else {
                        tvPayMethod.setText("微信支付");
                    }
                } else if("0".equals(orderBean.getOpaystate())){
                    opay = orderBean.getOpay();
                    tvPayState.setText("未支付");
                    tvPayState.setTextColor(0xffff883b);
                    ll_paymethod.setVisibility(View.GONE);
                    ll_payfor.setVisibility(View.VISIBLE);
                    ll_payfor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tipClick();
                        }
                    });
                } else if("2".equals(orderBean.getOpaystate())){
                    tvPayState.setText("已取消");
                    tvPayState.setTextColor(0xffff883b);
                    ll_paymethod.setVisibility(View.GONE);
                    ll_payfor.setVisibility(View.GONE);
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

                String picUrl = URL.BASE_FILE_PIC_URL + orderBean.getOpic();
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

    Handler payHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                final JSONObject json = JSONObject.parseObject(result);
                String status = json.getString("error");
                if("1".equals(status)){
                    Toast.makeText(OrderDetailsActivity.this,"支付成功！",Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid", oid);
                    jsonObject.put("uid", MyApplication.uid);
                    HttpUtil.doJsonPost(handler, orderDetailUrl, jsonObject.toJSONString());
                } else {
                    Toast.makeText(OrderDetailsActivity.this,"出错了！稍后再试吧！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 支付确认对话框
     */
    public void tipClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认支付");
        builder.setMessage("请确认支付￥"+ osum +"元?");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(false);
        //设置正面按钮
        builder.setPositiveButton("立即支付", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Toast.makeText(OrderDetailsActivity.this, "你点击了是的", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                JSONObject jsonObject = new JSONObject();
                int uid = MyApplication.uid;
                uid = 1;
                jsonObject.put("uid",uid);
                jsonObject.put("oid",oid);
                jsonObject.put("opay",opay);
                HttpUtil.doJsonPost(payHandler,URL.BASE_URL + "api/Add/payOrder",jsonObject.toJSONString());
            }
        });
        //设置反面按钮
        builder.setNegativeButton("稍后支付", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(OrderDetailsActivity.this, "你点击了不是", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        //对话框显示的监听事件
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });
        //对话框消失的监听事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        //显示对话框
        dialog.show();
    }
    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}
