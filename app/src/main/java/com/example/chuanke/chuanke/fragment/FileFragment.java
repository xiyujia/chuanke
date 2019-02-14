package com.example.chuanke.chuanke.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseFragment;
import com.example.chuanke.chuanke.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.widget.LinearLayout.HORIZONTAL;
import static com.youth.banner.BannerConfig.PADDING_SIZE;

public class FileFragment extends BaseFragment  {
    private  ArrayList < String > images =  new  ArrayList <>();
    @Override
    public int getLayoutFile() {
        return R.layout.fragment_file;
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
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerAnimation(Transformer.DepthPage);

        banner.start();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
