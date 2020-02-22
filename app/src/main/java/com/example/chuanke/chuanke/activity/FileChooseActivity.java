package com.example.chuanke.chuanke.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.MainActivity;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.adapter.FileFragmentListAdapter;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.FileBean;
import com.example.chuanke.chuanke.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class FileChooseActivity extends BaseActivity {

    private List<FileBean> filesList = new ArrayList<>();
    private FileFragmentListAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    GridLayoutManager gridLayoutManager;

    int uid;
    int sid;
    int fid;
    String sprice;

    int tempDelPosition;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_file_choose;
    }

    @Override
    public void initView() {
        topBar.setText("选择文件");

        floatingActionButton = findViewById(R.id.floatbutton);
        recyclerView = findViewById(R.id.rv_file_fragment);

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        sid = intent.getIntExtra("sid",-1);
        sprice = intent.getStringExtra("sprice");
        uid = MyApplication.uid;
        uid = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/file", jsonObject.toJSONString());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),UploadActivity.class);
                intent.putExtra("oprateType","add")
                        .putExtra("sid",sid).putExtra("sprice",sprice);
                startActivity(intent);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null) {
                JSONArray item = JSONArray.parseArray(result);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                filesList = item.toJavaList(FileBean.class);
                gridLayoutManager = new GridLayoutManager(getContext(), 1);
                recyclerView.setLayoutManager(gridLayoutManager);

                adapter = new FileFragmentListAdapter(FileChooseActivity.this, filesList);
                recyclerView.setAdapter(adapter);
                // 设置item及item中控件的点击事件
                adapter.setOnItemClickListener(MyItemClickListener);

            }
        }
    };

    @Override
    protected void initSetting() {

    }

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
                    Intent intent1 = new Intent(FileChooseActivity.this,PlayTimeActivity.class);
                    intent1.putExtra("sid",sid).putExtra("fid",fid).putExtra("sprice",sprice);
                    startActivity(intent1);
                    Toast.makeText(getContext(), "你点击了toufang按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Intent intent = new Intent(getContext(),UploadActivity.class);
                    intent.putExtra("fid",fid);
                    intent.putExtra("oprateType","update")
                            .putExtra("sid",sid).putExtra("sprice",sprice);
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
        uid = 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/Lists/file", jsonObject.toJSONString());
    }

    @Override
    public void onClick(View view) {

    }
}
