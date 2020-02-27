package com.example.chuanke.chuanke.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.MyServer;
import com.example.chuanke.chuanke.base.TopBar;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.bean.FileBean;
import com.example.chuanke.chuanke.bean.UpLoadBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.barlibrary.ImmersionBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint("NewApi")
public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private ProgressDialog prgDialog;
    private ImageView iv_upload;
    private ImageView iv_back;
    private ImageView iv_save;
    private LinearLayout ll_submit;
    private DisplayImageOptions options ;
    private ImageLoader imageLoader;
    public ImmersionBar immersionBar;//沉浸式状态栏

    private File tempImg;
    private File tempImg1;
    private File lastImg;

    private String url = URL.BASE_URL+"api/add/file";

    private String oprateType;//操作类型：add新建，update修改
    private int sid=-1;//是否带设备类型：-1不带，否则带
    private int fid=-1;//是否带设备类型：-1不带，否则带
    private boolean isSaveSuccess;//是否带设备类型：-1不带，否则带

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        initImmersionBarOfColorBar(R.color.white, true);
        iv_upload=findViewById(R.id.iv_upload);
        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopueWindow();
            }
        });

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.NONE)
                .build();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        ll_submit=findViewById(R.id.ll_submit);
        ll_submit.setOnClickListener(this);
        iv_save=findViewById(R.id.iv_save);
        iv_save.setOnClickListener(this);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        et_title = findViewById(R.id.et_title);

        prgDialog= new ProgressDialog(this);
        prgDialog.setCancelable(false);

        Intent intent = getIntent();
        sid = intent.getIntExtra("sid",-1);
        oprateType = intent .getStringExtra("oprateType");

        if("add".equals(oprateType.trim())){
            url = URL.BASE_URL+"api/add/file";
        } else if("update".equals(oprateType.trim())){
            url = URL.BASE_URL+"api/update/file";
            Intent intent1 = getIntent();
            fid=intent1.getIntExtra("fid",-1);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fid",fid);
            String fileDetailUrl = URL.BASE_URL + "api/Lists/file";
            if(fid != -1){
                HttpUtil.doJsonPost(handler,fileDetailUrl,jsonObject.toJSONString());
            }
        }

//        editTextName = (EditText) findViewById(R.id.editText);
//        findViewById(R.id.choose_image).setOnClickListener(this);
//        findViewById(R.id.upload_image).setOnClickListener(this);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (result != null && !result.equals("null") && !result.equals("")) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                FileBean fileBean = jsonObject.toJavaObject(FileBean.class);
                et_title.setText(fileBean.getFname());
                imageLoader.displayImage(URL.BASE_FILE_PIC_URL+fileBean.getFpic(),iv_upload,options);
//                iv_upload.setImageURI(URL.BASE_FILE_PIC_URL+fileBean.getFpic());
//                lastImg = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            }
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_save:
                if(fid ==-1){
                    if(null != tempImg && !et_title.getText().toString().trim().equals("")){
                        new Thread(new Runnable() {  //开启线程上传文件
                            @Override
                            public void run() {
                                url = URL.BASE_URL+"api/add/file";
                                uploadOk(tempImg);
                            }
                        }).start();
                    }

                } else {
                    if(null != tempImg && !et_title.getText().toString().trim().equals("")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                url = URL.BASE_URL + "api/update/file";
                                uploadOk(tempImg);
                            }
                        }).start();
                    } else {
                        Toast.makeText(this,"请重新选择图片",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_submit:
                if(fid ==-1){
                    if(null != lastImg && !et_title.getText().toString().trim().equals("")){
                        new Thread(new Runnable() {  //开启线程上传文件
                            @Override
                            public void run() {
                                url = URL.BASE_URL+"api/add/file";
                                uploadOk(lastImg);
                                if(isSaveSuccess){
                                    Intent intent = new Intent();
                                    intent.putExtra("fid",fid);
                                }
                            }
                        }).start();
                    }

                } else {
                    if(null != lastImg && !et_title.getText().toString().trim().equals("")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                url = URL.BASE_URL + "api/update/file";
                                uploadOk(lastImg);
                            }
                        }).start();
                    }
                }
                break;
        }
    }

//    调取拍照功能：
    private void camera() {
        try {
            tempImg1 = new File(UploadActivity.this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            if (!tempImg1.exists()) {
                boolean b = tempImg1.createNewFile();
                if (b) {
                    Log.i("tempImg1path",tempImg1.getAbsolutePath());
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImg1));
                    startActivityForResult(intent, 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    requestCode请求码，resultCode结果码，data是传递的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1://调用相机拍照完成
                    if (tempImg1 != null) {
                        //调用裁剪功能或者压缩
                        scaleImage(Uri.fromFile(tempImg1));
                    }
                    break;
                case 2://相册相片选择完成
                    if (data != null) {
                        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                        Bitmap map = null;
                        try {
                            map = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            map.compress(Bitmap.CompressFormat.JPEG, 10, bos);
                            bos.flush();
                            bos.close();
                            tempImg = file;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //调用裁剪，有点问题：裁剪完成后响应参数intent无值
//                        startPhotoZoom(data.getData(),500);

                        //把定义的最终文件赋值并显示出来。
                        lastImg = file;
                        iv_upload.setImageBitmap(map);

                    }
                    break;
                case 3://裁剪完成，最终要上传的文件已经赋给lastImg
                    if (data != null) {
                        //调用压缩
//                         scaleImage(data.getData());
                        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                        Bitmap map = null;
                        try {
                            map = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(tempImg));
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//                            map.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                            bos.flush();
                            bos.close();
                            lastImg = file;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iv_upload.setImageBitmap(map);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 裁剪图片
     * 裁剪有系统的裁剪方式，也有自定义的裁剪方式
     * 下面是系统裁剪：
     */
    public void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
//        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
//        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", size);//图片输出大小
        intent.putExtra("outputY", size);
        intent.putExtra("output", Uri.fromFile(tempImg));
        intent.putExtra("outputFormat", "jpg");// 返回格式
        startActivityForResult(intent, 3);
    }

//调取相册功能：
    private void photos() {
        Intent getImage = new Intent(Intent.ACTION_PICK, null);
        getImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//这是图片类型
        startActivityForResult(getImage, 2);
    }

    private void scaleImage(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width > 800 || height > 800) {
                if (width > height) {
                    float scaleRate = (float) (800.0 / width);
                    width = 800;
                    height = (int) (height * scaleRate);
                    Bitmap map = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    saveBitmap(map);
                } else {
                    float scaleRate = (float) (800.0 / height);
                    height = 800;
                    width = (int) (width * scaleRate);
                    Bitmap map = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    saveBitmap(map);
                }
            } else {
                saveBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmap(Bitmap map) {
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            map.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
//            updateImg(file);//上传图片接口
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     */
    private void uploadOk(File file) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        //设置上传文件以及文件对应的MediaType类型
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        //MultipartBody文件上传
        /**区别：
         * addFormDataPart:   上传key:value形式
         * addPart:  只包含value数据
         */
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//设置文件上传类型
//                .addFormDataPart("key", "img")//文件在服务器中保存的文件夹路径
//                .addFormDataPart("file", file.getName(), requestBody)//包含文件名字和内容
                .addFormDataPart("fpic", file.getName(), requestBody)//包含文件名字和内容,此处文件名必须用file.getName()
                .addFormDataPart("uid",MyApplication.uid+"")
                .addFormDataPart("fid",fid+"")
                .addFormDataPart("fname",et_title.getText().toString().trim())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(result != null && !result.equals("null") && !result.equals("")){
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            int status = jsonObject.getIntValue("status");
                            if (status == 1) {
                                fid = jsonObject.getIntValue("fid");
                                isSaveSuccess = true;
                                Toast.makeText(getApplicationContext(),"保存成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                isSaveSuccess = false;
                                Toast.makeText(getApplicationContext(), "保存失败！请稍后再试！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }


    //修改调用新增接口，此接口暂时不用
    private void updateFile(File file) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyServer myServer = retrofit.create(MyServer.class);

        //文件封装
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                "file", file.getName(), requestBody);

        //文本封装
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"),"img");

        final Observable<UpLoadBean> upload = myServer.upload(requestBody1,filePart);

        upload.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpLoadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpLoadBean upLoadBean) {
                        if (upLoadBean != null && upLoadBean.getCode() == 200) {
                            Log.d("lzj", upLoadBean.getData().getUrl());
                            success(upLoadBean);
                        } else {
                            Toast.makeText(UploadActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //此方法也不用
    private void success(UpLoadBean upLoadBean) {
        Toast.makeText(this, upLoadBean.getRes(), Toast.LENGTH_SHORT).show();
//        Glide.with(this).load(upLoadBean.getData().getUrl()).into(img);
    }


    private void showPopueWindow(){
        View popView = View.inflate(this,R.layout.popupwindow_camera_need,null);
        TextView bt_album = popView.findViewById(R.id.btn_pop_album);
        TextView bt_camera = popView.findViewById(R.id.btn_pop_camera);
        TextView bt_cancle = popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels*1/3;

        final PopupWindow popupWindow = new PopupWindow(popView,weight,height);
//        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(false);

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
                photos();
                popupWindow.dismiss();

            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takeCamera(RESULT_CAMERA_IMAGE);
                camera();
                popupWindow.dismiss();

            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM,0,50);
    }

    /**
     * 初始化状态栏:纯色状态栏
     * 使用“改变状态栏颜色”方案
     *
     * @param statusBarColor      状态栏颜色
     * @param isStatusBarDarkFont 是否使用深色字体
     */
    protected void initImmersionBarOfColorBar(@ColorRes int statusBarColor, boolean isStatusBarDarkFont) {
        if (immersionBar == null) {
            immersionBar = ImmersionBar.with(this);
            immersionBar
                    .keyboardEnable(true)//解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                    .navigationBarWithKitkatEnable(false) //是否可以修改安卓4.4和emui3.1手机导航栏颜色，默认为true
                    .init();
        }
        immersionBar
                .fitsSystemWindows(true)
                .statusBarColor(statusBarColor)
                .statusBarDarkFont(isStatusBarDarkFont, 0.2f)
                .init();
    }
}
