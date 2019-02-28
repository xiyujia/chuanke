package com.example.chuanke.chuanke.base;




import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**

* 作者：张恺

* 时间：2019/2/15

* 类描述：

*/


public class BaseModel<T> {
    public Retrofit retrofit;
    public Service service;
    public Call<T> call;

    public BaseModel() {
        retrofit = new Retrofit.Builder().baseUrl(URL.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(Service.class);
    }

    public void callEnqueue(Call<T> call, final BaseListener<T> listener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.body() != null && response.isSuccessful())
                    listener.onResponse(response.body());
                else
                    listener.onFail("失败");
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onFail(t.getMessage());
            }
        });
    }
}