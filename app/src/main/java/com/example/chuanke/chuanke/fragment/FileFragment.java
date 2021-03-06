package com.example.chuanke.chuanke.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.activity.DeviceListActivity;
import com.example.chuanke.chuanke.activity.UploadActivity;
import com.example.chuanke.chuanke.adapter.FileFragmentListAdapter;
import com.example.chuanke.chuanke.base.BaseFragment;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.FileBean;
import com.example.chuanke.chuanke.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class FileFragment extends BaseFragment {
    private List<FileBean> filesList = new ArrayList<>();
    private FileFragmentListAdapter adapter;
    RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private RelativeLayout topbar_root;

    private GridLayoutManager gridLayoutManager;
    private TextView tv_null;

    int uid;
    String sid;
    int fid;
    String sprice;
    int tempDelPosition;

    @Override
    public int getLayoutFile() {
        return R.layout.fragment_file;
    }

    @Override
    public void initSetting() {

    }

    @Override
    public void initView() {
        topbar_root = findViewById(R.id.topbar_root);
//        initImmersionBarOfColorBar(R.color.white, true,this.getActivity());//沉浸式状态栏
//        initImmersionBarForTopBar(topbar_root,false,getActivity());
        setTranslucentStatus();
        floatingActionButton = findViewById(R.id.floatbutton);
        recyclerView = findViewById(R.id.rv_file_fragment);
        tv_null = findViewById(R.id.tv_null);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        floatingActionButton.setEnabled(true);
                    }
                }, 1000);// 1000毫秒执行，1秒
                Intent intent = new Intent(getContext(),UploadActivity.class);
                intent.putExtra("oprateType","add");
                startActivity(intent);
            }
        });

        adapter = new FileFragmentListAdapter(getActivity(), filesList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        uid = MyApplication.uid;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/file", jsonObject.toJSONString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONArray item = JSONArray.parseArray(result);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                if(item.size() != 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_null.setVisibility(View.GONE);
                    filesList = item.toJavaList(FileBean.class);
                    gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);

                    adapter = new FileFragmentListAdapter(getActivity(), filesList);
                    recyclerView.setAdapter(adapter);
                    // 设置item及item中控件的点击事件
                    adapter.setOnItemClickListener(MyItemClickListener);
                } else {
                    tv_null.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        }
    };

    /**
     * item＋item里的控件点击监听事件
     */
    private FileFragmentListAdapter.OnItemClickListener MyItemClickListener = new FileFragmentListAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, FileFragmentListAdapter.ViewName viewName, int position) {
            //viewName可区分item及item内部控件
            fid = filesList.get(position).getFid();
            switch (v.getId()) {
                case R.id.tv_file_delete:
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("uid", uid);
                    jsonObject.put("fid", fid);
                    HttpUtil.doJsonPost(handlerDelete, URL.BASE_URL + "api/delete/file", jsonObject.toJSONString());
                    tempDelPosition = position;
                    break;
                case R.id.tv_file_put:
                    Intent intent1 = new Intent(getContext(), DeviceListActivity.class);
                    intent1.putExtra("fid",fid);
                    startActivity(intent1);
                    break;
                default:
                    Intent intent = new Intent(getContext(),UploadActivity.class);
                    fid = filesList.get(position).getFid();
                    intent.putExtra("fid",fid);
                    intent.putExtra("oprateType","update");
                    startActivity(intent);
                    break;
            }
        }

        Handler handlerDelete = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String) msg.obj;
                if (result != null) {
                    JSONObject json = JSONObject.parseObject(result);
                    String msg2 = json.getString("msg");
                    Toast.makeText(getContext(), msg2, Toast.LENGTH_SHORT).show();
                    adapter.removeData(tempDelPosition);
                }
            }
        };

        @Override
        public void onItemLongClick(View v) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        uid = MyApplication.uid;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/file", jsonObject.toJSONString());
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < 20) {

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 21) {

            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
