package com.example.chuanke.chuanke.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.DeviceActivity;

import com.example.chuanke.chuanke.activity.EditFileActivity;
import com.example.chuanke.chuanke.base.BaseFragment;
import com.example.chuanke.chuanke.util.GlideImageLoader;
import com.example.chuanke.chuanke.zxing.activity.CaptureActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment {
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private ArrayList< String > images =  new  ArrayList <>();
    ImageView iv_scan,iv_decice,iv_template;
    @Override
    public int getLayoutFile() {
        return R.layout.fragment_home;
    }

    @Override
    public void initSetting() {
        images.add("http://seopic.699pic.com/photo/00005/5186.jpg_wh1200.jpg");
        images.add("http://seopic.699pic.com/photo/50010/0719.jpg_wh1200.jpg");
        images.add("http://seopic.699pic.com/photo/50009/9449.jpg_wh1200.jpg");
    }

    @Override
    public void initView() {
        Banner banner = (Banner) findViewById(R.id.banner);
        gridView = (GridView) findViewById(R.id.gridview);
        iv_scan=findViewById(R.id.iv_fg_home_scan);
        iv_decice=findViewById(R.id.iv_fg_home_decice);
        iv_template=findViewById(R.id.iv_fg_home_template);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
//        banner.setBannerAnimation(Transformer.DepthPage);
        banner.start();

        //初始化数据

    }

    @Override
    public void initEvent() {
        iv_scan.setOnClickListener(this);
        iv_decice.setOnClickListener(this);
        iv_template.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //图标
        int icno[] = { R.drawable.cat, R.drawable.cat, R.drawable.cat,
                R.drawable.cat, R.drawable.cat, R.drawable.cat};
        //图标下的文字
        String name[]={"推荐1","推荐2","推荐3","推荐4","推荐5","推荐6"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text",name[i]);
            dataList.add(map);
        }
        String[] from={"img","text"};

        int[] to={R.id.img, R.id.text};

        adapter=new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_fg_home_scan:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    startActivity(CaptureActivity.class);
                }
                break;
            case R.id.iv_fg_home_decice:
                startActivity(DeviceActivity.class);
                break;
            case R.id.iv_fg_home_template:
                startActivity(EditFileActivity.class);
                break;
        }
    }
}
