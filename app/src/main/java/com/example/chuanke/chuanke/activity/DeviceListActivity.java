package com.example.chuanke.chuanke.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.DeviceListAdapter;
import com.example.chuanke.chuanke.adapter.FileFragmentListAdapter;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.ScreenDetailBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.example.chuanke.chuanke.util.LocationUtil;
import com.gyf.barlibrary.ImmersionBar;

import org.json.JSONObject;

import java.util.List;

public class DeviceListActivity extends AppCompatActivity implements LocationSource {
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private LocationUtil locationUtil;
    CameraUpdate cameraUpdate;

    private RecyclerView recyclerView;
    private DeviceListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private ImageView iv_back;
    public ImmersionBar immersionBar;//沉浸式状态栏

    private static final int LOCATION_CODE = 1;
    private LocationManager lm;//【位置管理】
    private LatLng latLng;

    private ImageView iv_up_down;
    private TextView tv_up_down;
    private LinearLayout ll_up_down;
    private boolean isUp = true;
    private int fid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        initImmersionBarOfColorBar(R.color.white, true);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerview);

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_up_down = findViewById(R.id.iv_up_down);
        tv_up_down = findViewById(R.id.tv_up_down);
        ll_up_down = findViewById(R.id.ll_up_down);
        ll_up_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUp){
                    recyclerView.setVisibility(View.GONE);
                    tv_up_down.setText("点击查看设备列表");
                    iv_up_down.setImageResource(R.drawable.up);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_up_down.setText("点击关闭设备列表");
                    iv_up_down.setImageResource(R.drawable.down);
                }
                isUp = !isUp;
            }
        });
        Intent intent = getIntent();
        fid = intent.getIntExtra("fid",-1);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/screen", "");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONArray item = JSONArray.parseArray(result);
                List<ScreenDetailBean> screenDetailBeans = item.toJavaList(ScreenDetailBean.class);
                gridLayoutManager = new GridLayoutManager(DeviceListActivity.this, 1);
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter = new DeviceListAdapter(DeviceListActivity.this, screenDetailBeans,fid);
                recyclerView.setAdapter(adapter);

                init();
                quanxian();//申请手机定位权限，开启以后才能定位手机所在位置

                //设置位置
                latLng = new LatLng(Double.parseDouble(screenDetailBeans.get(0).getSlatitude()),
                        Double.parseDouble(screenDetailBeans.get(0).getSlongitude()));

                //改变可视区域为指定位置，这里设置了设备列表中第一个设备的位置为指定位置
                //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）这一步操作代替了init()方法。
                cameraUpdate= CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,15,0,30));
                aMap.moveCamera(cameraUpdate);//地图移向指定区域

//                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(Double.parseDouble(screenDetailBeans.get(0).getSlatitude()),
//                        Double.parseDouble(screenDetailBeans.get(0).getSlongitude()))));
                for (int i = 0; i<screenDetailBeans.size();i++){
                    //添加设备定位标记
                    addpoint(Double.parseDouble(screenDetailBeans.get(i).getSlatitude()),
                            Double.parseDouble(screenDetailBeans.get(i).getSlongitude()),
                            screenDetailBeans.get(i).getSplace());
                }

            }
        }
    };

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
//        setLocationCallBack();
        //设置定位监听
        aMap.setLocationSource(this);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //显示定位层并可触发，默认false
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(38.881, 121.53)));
    }

    //当前位置所在定位回调，也就是调用这个方法，会每秒中都监听并回到手持设备所在位置
    private void setLocationCallBack() {
        locationUtil = new LocationUtil();
        locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {

                //根据获取的经纬度，将地图移动到定位位置
//                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lgt)));
                mListener.onLocationChanged(aMapLocation);
                //添加定位图标
//                aMap.addMarker(locationUtil.getMarkerOption(str, lat, lgt));
                aMap.removecache();
            }
        });
    }

    //定位激活回调
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        locationUtil.startLocate(getApplicationContext());
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新绘制加载地图
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    //在地图上设置标记,v、v1:经纬度
    public void addpoint(Double v, Double v1, String title) {
//设置坐标点
        LatLng point1 = new LatLng(v, v1);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_pressed));
        markerOptions.position(point1);
        markerOptions.title(title);
        aMap.addMarker(markerOptions);
    }

    public void quanxian() {
        lm = (LocationManager) DeviceListActivity.this.getSystemService(DeviceListActivity.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(DeviceListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("BRG", "没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(DeviceListActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//                        Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {

                // 有权限了，去放肆吧。
//                        Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("BRG", "系统检测到未开启GPS定位服务");
            Toast.makeText(DeviceListActivity.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。

                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(DeviceListActivity.this, "定位权限被禁止，相关地图功能无法使用！", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    //设置上方信号栏为透明
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 20) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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
