package com.example.version2.utils;

import com.example.version2.data.Baike;
import com.example.version2.data.GeoCoding;
import com.example.version2.data.IpLookup;
import com.example.version2.data.Regeo;
import com.example.version2.data.Sug;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface RxApis {


    @GET("http://gc.ditu.aliyun.com/geocoding")
    Observable<GeoCoding> geocoding(@Query("a") String name);

    @GET("http://ditu.amap.com/service/regeo")
    Observable<Regeo> regeo(@Query("longitude") double longitude, @Query("latitude") double latitude);

    @GET("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json")
    Observable<IpLookup> ipLookup();

    @GET("https://suggest.taobao.com/sug?code=utf-8")
    Observable<Sug> sug(@Query("q") String q);

    @GET("http://baike.baidu.com/api/openapi/BaikeLemmaCardApi?scope=103&format=json&appid=379020&bk_length=600")
    Observable<Baike> baiKe(@Query("bk_key") String key);


}
