package com.example.chuanke.chuanke.base;

import com.example.chuanke.chuanke.bean.LoginBean;
import com.example.chuanke.chuanke.bean.RegBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.example.chuanke.chuanke.base.URL.login;
import static com.example.chuanke.chuanke.base.URL.reg;

public interface Service {
    //登录
    @POST(login)
    Call<LoginBean> login(@Query("uname") String mobile, @Query("upassword") String pswd);

    //注册
    @POST(reg)
    Call<RegBean> reg(@Query("uname") String name, @Query("uphone") String phone, @Query("upassword") String pwd);
}
