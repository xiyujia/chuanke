package com.example.chuanke.chuanke.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.example.chuanke.chuanke.MainActivity;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.util.LocationUtil;

public class DeviceActivity extends AppCompatActivity implements LocationSource {
    private MapView mapView;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private LocationUtil locationUtil;
    BottomSheetBehavior behavior;
    RelativeLayout bottomSheet;

    private static final int LOCATION_CODE = 1;
    private LocationManager lm;//【位置管理】
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        init();

        //底部抽屉栏展示地址
//        bottomSheet = findViewById(R.id.bottom_sheet);
//        behavior = BottomSheetBehavior.from(bottomSheet);
//        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, @BottomSheetBehavior.State int newState) {
//                String state = "null";
//                switch (newState) {
//                    case 1:
//                        state = "STATE_DRAGGING";//过渡状态此时用户正在向上或者向下拖动bottom sheet
//                        break;
//                    case 2:
//                        state = "STATE_SETTLING"; // 视图从脱离手指自由滑动到最终停下的这一小段时间
//                        break;
//                    case 3:
//                        state = "STATE_EXPANDED"; //处于完全展开的状态
//
//                        break;
//                    case 4:
//                        state = "STATE_COLLAPSED"; //默认的折叠状态
//                        break;
//                    case 5:
//                        state = "STATE_HIDDEN"; //下滑动完全隐藏 bottom sheet
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
////                Log.d("BottomSheetDemo", "slideOffset:" + slideOffset);
//            }
//        });

        quanxian();

        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(38.881, 121.53)));
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        setLocationCallBack();

        //设置定位监听
        aMap.setLocationSource(this);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //显示定位层并可触发，默认false
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(38.881, 121.53)));
    }

    private void setLocationCallBack() {
        locationUtil = new LocationUtil();
        locationUtil.setLocationCallBack(new LocationUtil.ILocationCallBack() {
            @Override
            public void callBack(String str, double lat, double lgt, AMapLocation aMapLocation) {

                //根据获取的经纬度，将地图移动到定位位置
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lgt)));
                mListener.onLocationChanged(aMapLocation);
                //添加定位图标
                aMap.addMarker(locationUtil.getMarkerOption(str, lat, lgt));
                aMap.removecache();

                addpoint(38.881, 121.53);
                addpoint(38.85, 121.533);
                addpoint(38.81, 121.538);
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

    //加点
    public void addpoint(Double v, Double v1) {
//设置坐标点
        LatLng point1 = new LatLng(v, v1);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.poi_marker_pressed));
        markerOptions.position(point1);
        markerOptions.title("终端的位置");
        aMap.addMarker(markerOptions);
    }

    public void quanxian() {
        lm = (LocationManager) DeviceActivity.this.getSystemService(DeviceActivity.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(DeviceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.e("BRG", "没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(DeviceActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//                        Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {

                // 有权限了，去放肆吧。
//                        Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("BRG", "系统检测到未开启GPS定位服务");
            Toast.makeText(DeviceActivity.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(DeviceActivity.this, "定位权限被禁止，相关地图功能无法使用！", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}
