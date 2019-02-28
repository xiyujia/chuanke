package com.example.chuanke.chuanke.fragment;

import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseFragment;
import com.example.chuanke.chuanke.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment {
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private ArrayList< String > images =  new  ArrayList <>();
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
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
//        banner.setBannerAnimation(Transformer.DepthPage);
        banner.start();

        //初始化数据

    }

    @Override
    public void initEvent() {

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

        int[] to={R.id.img,R.id.text};

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

    }
}
