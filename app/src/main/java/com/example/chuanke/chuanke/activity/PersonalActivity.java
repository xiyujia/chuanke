package com.example.chuanke.chuanke.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chuanke.chuanke.R;
import com.example.chuanke.chuanke.base.BaseActivity;
import com.example.chuanke.chuanke.base.MyApplication;
import com.example.chuanke.chuanke.base.URL;
import com.example.chuanke.chuanke.base.UserBean;
import com.example.chuanke.chuanke.util.HttpUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalActivity extends BaseActivity {


    private SimpleDraweeView iv_portrait;
    private LinearLayout ll_username;
    private LinearLayout ll_bind_phone;
    private LinearLayout ll_bind_wechat;
    private TextView tv_username;
    private TextView my_phone_state;
    private TextView my_wx_state;
    private Button signout;
    private String updatePicUrl = URL.BASE_URL + "api/update/userimage";
    private String updateUserUrl = URL.BASE_URL + "api/update/user";
    private boolean isSaveSuccess;

    private File tempImg1;
    private File tempImg;

    @Override
    public int getLayoutFile() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView() {
        topBar.setText("个人资料");
        tv_username = findViewById(R.id.tv_username);
        my_phone_state = findViewById(R.id.my_phone_state);
        my_wx_state = findViewById(R.id.my_wx_state);
        iv_portrait = findViewById(R.id.iv_portrait);
        iv_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopueWindow();
            }
        });
        signout = findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalActivity.this, LoginActivity.class);
                HomeActivity.instance.finish();
                startActivity(intent);
                MyApplication.uid = 1;
                PersonalActivity.this.finish();
            }
        });
        ll_username = findViewById(R.id.ll_username);

        ll_bind_phone = findViewById(R.id.ll_bind_phone);

        ll_bind_wechat = findViewById(R.id.ll_bind_wechat);

    }

    @Override
    public void initEvent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", MyApplication.uid);
        HttpUtil.doJsonPost(handler, URL.BASE_URL + "api/user/getuser", jsonObject.toJSONString());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if (!"".equals(result) && null != result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                final UserBean userBean = jsonObject.toJavaObject(UserBean.class);
                tv_username.setText(userBean.getUname());
                my_phone_state.setText(userBean.getUphone());
                my_wx_state.setText(userBean.getUemail());
                iv_portrait.setImageURI(URL.BASE_USER_PIC_URL + userBean.getUpic());
                ll_username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PersonalActivity.this,UpdateUserActivity.class);
                        intent.putExtra("uname","")
                                .putExtra("uphone",userBean.getUphone())
                                .putExtra("uemail",userBean.getUemail());
                        startActivity(intent);
                    }
                });
                ll_bind_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PersonalActivity.this,UpdateUserActivity.class);
                        intent.putExtra("uname",userBean.getUname())
                                .putExtra("uphone","")
                                .putExtra("uemail",userBean.getUemail());
                        startActivity(intent);
                    }
                });
                ll_bind_wechat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PersonalActivity.this,UpdateUserActivity.class);
                        intent.putExtra("uname",userBean.getUname())
                                .putExtra("uphone",userBean.getUphone())
                                .putExtra("uemail","");
                        startActivity(intent);
                    }
                });
            }
        }
    };
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
                .addFormDataPart("upic", file.getName(), requestBody)//包含文件名字和内容,此处文件名必须用file.getName()
                .addFormDataPart("uid",MyApplication.uid+"")
                .build();

        Request request = new Request.Builder()
                .url(updatePicUrl)
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
                            try{
                                JSONObject jsonObject = JSONObject.parseObject(result);
                                int status = jsonObject.getIntValue("status");
                                if (status == 1) {
                                    isSaveSuccess = true;
                                    Toast.makeText(getApplicationContext(),"保存成功！", Toast.LENGTH_SHORT).show();
                                    initEvent();
                                } else {
                                    isSaveSuccess = false;
                                    Toast.makeText(getApplicationContext(), "保存失败！请稍后再试！", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "保存失败!后台出错了！", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
            }
        });
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
//                camera();
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
    //调取相册功能：
    private void photos() {
        Intent getImage = new Intent(Intent.ACTION_PICK, null);
        getImage.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//这是图片类型
        startActivityForResult(getImage, 2);
    }


    //    requestCode请求码，resultCode结果码，data是传递的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1://调用相机拍照完成
                    if (data != null) {
                        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                        Bitmap map = null;
                        try {
                            /*缩略图信息是储存在返回的intent中的Bundle中的，
                             * 对应Bundle中的键为data，因此从Intent中取出
                             * Bundle再根据data取出来Bitmap即可*/
                            Bundle extras = data.getExtras();
                            map = (Bitmap) extras.get("data");
//                            map = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getExtras());
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            map.compress(Bitmap.CompressFormat.JPEG, 10, bos);
                            bos.flush();
                            bos.close();
                            tempImg = file;
                            startPhotoZoom(Uri.fromFile(file),500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                            startPhotoZoom(data.getData(),500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://裁剪完成，最终要上传的文件已经赋给lastImg
                    if (data != null) {
                        //调用压缩
//                         scaleImage(data.getData());
                        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
                        Bitmap map = null;
                        try {
                            map = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            map.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                            bos.flush();
                            bos.close();
                            tempImg = file;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        uploadOk(tempImg);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    //    调取拍照功能：
    private void camera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
        if(takePhotoIntent.resolveActivity(getPackageManager())!=null){//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
//            tempImg1 = new File(PersonalActivity.this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
            startActivityForResult(takePhotoIntent,1);//启动相机
        }
//        try {
//            tempImg1 = new File(PersonalActivity.this.getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
//            if (!tempImg1.exists()) {
//                boolean b = tempImg1.createNewFile();
//                if (b) {
//                    Log.i("tempImg1path",tempImg1.getAbsolutePath());
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempImg1));
//                    startActivityForResult(intent, 1);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", size);//图片输出大小
        intent.putExtra("outputY", size);
        intent.putExtra("output", Uri.fromFile(tempImg));
        intent.putExtra("outputFormat", "jpg");// 返回格式
        startActivityForResult(intent, 3);
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

    @Override
    public void onResume() {
        super.onResume();
        initEvent();
    }

//    private void takeCamera(int num) {
//
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            photoFile = createImageFile();
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photoFile));
//            }
//        }
//
//        startActivityForResult(takePictureIntent, num);//跳转界面传回拍照所得数据
//    }

    @Override
    public void initData() {

    }

    @Override
    protected void initSetting() {

    }

    @Override
    public void onClick(View v) {

    }
}
