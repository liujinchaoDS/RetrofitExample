package com.example.version2.utils;


import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxRetrofitUtils extends RetrofitUtils {

    private static Retrofit retrofit;
    private static Retrofit retrofit_noBaseUrl;

    private static Map<String, Object> map = new HashMap<>();

    //一个接口类定义多种返回类型（Call和Observable）时，要分开
    private synchronized static <T> T getInterface(Retrofit retrofit, Class<T> cls) {
        Object aClass = map.get(cls.getName());
        if (aClass == null) {
            aClass = retrofit.create(cls);
            map.put(cls.getName(), aClass);
        }
        return (T) aClass;
    }

    public static <T> T getInterface(Class<T> cls) {
        return getInterface(getRetrofit(), cls);
    }

    private static Retrofit getRetrofit() {
        if (retrofit_noBaseUrl == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.interceptors().add(new HttpLoggingInterceptor());
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            OkHttpClient client = builder.build();
            retrofit_noBaseUrl = new Retrofit.Builder().baseUrl("http://www.baidu.com").client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit_noBaseUrl;
    }

}
