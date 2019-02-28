package com.example.chuanke.chuanke.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.FragmentAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.fragment.FileFragment;
import com.example.chuanke.chuanke.fragment.HomeFragment;
import com.example.chuanke.chuanke.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**

* 作者：张恺

* 时间：2019/1/28

* 类描述：主页

*/

public class HomeActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentAdapter adapter;
    private List<Fragment> fragmentList;
    @Override
    public int getLayoutFile() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        tabLayout=findViewById(R.id.tab);
        topBarEnable=false;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new FileFragment());
        fragmentList.add(new MineFragment());
        adapter=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.getTabAt(0).setCustomView(R.layout.tab_file_fragment);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_home_fragment);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_mine_fragment);
    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}
