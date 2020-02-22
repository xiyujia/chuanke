package com.example.chuanke.chuanke.base;

import com.example.chuanke.chuanke.bean.LoginBean;
import com.example.chuanke.chuanke.bean.NewOrderBean;
import com.example.chuanke.chuanke.bean.RegBean;
import com.example.chuanke.chuanke.bean.TemplateBean;
import com.example.chuanke.chuanke.bean.UpLoadBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import static com.example.chuanke.chuanke.base.URL.login;
import static com.example.chuanke.chuanke.base.URL.neworder;
import static com.example.chuanke.chuanke.base.URL.reg;
import static com.example.chuanke.chuanke.base.URL.tamplateList;
import static com.example.chuanke.chuanke.base.URL.upLoad;

public interface Service {
    //登录
    @POST(login)
    Call<LoginBean> login(@Query("uname") String mobile, @Query("upassword") String pswd);

    //注册
    @POST(reg)
    Call<RegBean> reg(@Query("uname") String name, @Query("uphone") String phone, @Query("upassword") String pwd);

    //下单
    @POST(neworder)
    Call<NewOrderBean> neworder(@Query("uid") int uid, @Query("sid") int sid, @Query("fid") String fid, @Query("opay") int opay, @Query("osum")String sum, @Query("ostarttime")String ostarttime, @Query("oendtime")String oendtime);

    //模板
    @POST(tamplateList)
    Call<List<TemplateBean>> getTemplateList();

    @Multipart
    @POST(upLoad)
    Observable<UpLoadBean> upload(@Part("key") RequestBody requestBody, @Part MultipartBody.Part file);
}
