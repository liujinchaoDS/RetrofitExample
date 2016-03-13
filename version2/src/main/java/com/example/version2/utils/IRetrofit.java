package com.example.version2.utils;

import com.example.version2.data.Emoij;
import com.example.version2.data.PersonalInfo;
import com.example.version2.data.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


public interface IRetrofit {


    @GET("emojis")
    Call<Emoij> emojis();

    @GET("emojis")
    Observable<Emoij> obemoijs();

    /**
     * 获取个人信息
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("api/gravida/personal/info.json")
    Observable<Response<PersonalInfo>> getPersonalInfo(@Field("id") String id);


}
