package com.example.chuanke.chuanke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList!=null)
            return fragmentList.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (fragmentList!=null)
            return fragmentList.size();
        return 0;
    }
}
