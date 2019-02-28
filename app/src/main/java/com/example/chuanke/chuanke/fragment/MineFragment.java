package com.example.chuanke.chuanke.fragment;

import android.view.View;
import android.widget.LinearLayout;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.PersonalActivity;
import com.example.chuanke.chuanke.base.BaseFragment;

public class MineFragment extends BaseFragment {

    LinearLayout ll_persion;
    @Override
    public int getLayoutFile() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initSetting() {

    }

    @Override
    public void initView() {
        ll_persion=findViewById(R.id.ll_mine_persion);
    }

    @Override
    public void initEvent() {
        ll_persion.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.ll_mine_persion:
                startActivity(PersonalActivity.class);
                break;
        }
    }
}
