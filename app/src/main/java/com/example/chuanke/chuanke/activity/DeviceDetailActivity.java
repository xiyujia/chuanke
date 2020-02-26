package com.example.chuanke.chuanke.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.BaseListener;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.ScreenDetailBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.example.chuanke.chuanke.zxing.activity.ResultActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class DeviceDetailActivity extends BaseActivity {

    private String sid;
    private String sprice;
    private ScreenDetailBean screenDetailBean;
    private ImageView iv_pic;
    private TextView name,price,hangye,address,fenbianlv,screensize;
    private LinearLayout ll_choose;
    public ImageLoader imageLoader;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_device_detail;
    }

    @Override
    public void initView() {
        topBar.setText("设备详情");

        iv_pic = findViewById(R.id.iv_pic);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        hangye = findViewById(R.id.hangye);
        address = findViewById(R.id.address);
        fenbianlv = findViewById(R.id.fenbianlv);
        screensize = findViewById(R.id.screensize);
        ll_choose = findViewById(R.id.ll_choose);
        ll_choose.setOnClickListener(this);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();

        if (null != extras) {
//			int width = extras.getInt("width");
//			int height = extras.getInt("height");

//			LayoutParams lps = new LayoutParams(width, height);
//			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//
//			mResultImage.setLayoutParams(lps);
            String result = extras.getString("sid");
            sid = result;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sid", sid);
            HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/screen", jsonObject.toJSONString());
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null && !result.equals("null") && !result.equals("")) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                screenDetailBean = jsonObject.toJavaObject(ScreenDetailBean.class);
                sprice = screenDetailBean.getSprice();
                String picUrl = URL.BASE_DEVICE_PIC_URL + screenDetailBean.getSpic();
//        imageLoader.DisplayImage(picUrl, activity, holder.imgOffer);

                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .imageScaleType(ImageScaleType.NONE)
                        .build();
                imageLoader.init(ImageLoaderConfiguration.createDefault(DeviceDetailActivity.this));
                imageLoader.displayImage(picUrl,iv_pic,options);
                name.setText(screenDetailBean.getSplace());
                if(screenDetailBean.getSstate() == 0){
                    hangye.setText("设备状态：设备异常");
                } else if(screenDetailBean.getSstate() == 1){
                    hangye.setText("设备状态：设备正常");
                } else {
                    hangye.setText("设备状态：正在播放");
                }
                price.setText("￥"+screenDetailBean.getSprice()+"/h");
                address.setText("地址："+screenDetailBean.getSplace());
                fenbianlv.setText("分辨率："+screenDetailBean.getSresolution());
                screensize.setText("屏幕尺寸：" + screenDetailBean.getSratio()+ "寸");
//                people.setText("");
            } else {

            }
        }
    };


    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ll_choose:
                Intent intent = new Intent(this,FileChooseActivity.class);
                intent.putExtra("sid",sid);
                intent.putExtra("sprice",sprice);
                startActivity(intent);
                break;
        }

    }
}
