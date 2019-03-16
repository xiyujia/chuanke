package com.example.chuanke.chuanke.fragment;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.FileFragmentListAdapter;
import com.example.chuanke.chuanke.base.BaseFragment;
import com.example.chuanke.chuanke.bean.FileBean;
import com.example.chuanke.chuanke.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.widget.LinearLayout.HORIZONTAL;
import static com.youth.banner.BannerConfig.PADDING_SIZE;
/**

* 作者：张恺

* 时间：2019/2/28

* 类描述：文件fragment

*/

public class FileFragment extends BaseFragment  {
    private List<FileBean> filesList=new ArrayList<>();
    private FileFragmentListAdapter adapter;
    @Override
    public int getLayoutFile() {
        return R.layout.fragment_file;
    }

    @Override
    public void initSetting() {

    }

    @Override
    public void initView() {
        FloatingActionButton floatingActionButton=findViewById(R.id.floatbutton);
        RecyclerView recyclerView=findViewById(R.id.rv_file_fragment);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        for (int i=0;i<20;i++){
            FileBean bean=new FileBean();
            bean.setFineName("文件"+i);
            filesList.add(bean);
        }



        adapter=new FileFragmentListAdapter(filesList);
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("添加");
            }
        });
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
