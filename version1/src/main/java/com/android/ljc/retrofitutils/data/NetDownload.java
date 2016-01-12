package com.android.ljc.retrofitutils.data;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

public class NetDownload {

    public interface NetDown {
        /**
         *
         * @param width 宽
         * @param height 高
         * @param mediaid Id
         * @param cb 回调
         */
        @GET("/Common/ImgLoad.ashx")
        void netDown(@Query("w") int width, @Query("h") int height, @Query("mediaid") int mediaid, Callback<Response> cb);

        /**
         *
         * @param width 宽
         * @param height 高
         * @param gurl 路径
         * @param cb 回调
         */
        @GET("/Common/ImgLoad.ashx")
        void netDown(@Query("w") int width, @Query("h") int height, @Query("gurl") String gurl, Callback<Response> cb);

        /**
         *
         * @param mediaid Id
         * @param cb 回调
         */
        @GET("/Common/ImgLoad.ashx")
        void netDown(@Query("mediaid") int mediaid, Callback<Response> cb);

        /**
         *
         * @param gurl 路径
         * @param cb 回调
         */
        @GET("/Common/ImgLoad.ashx")
        void netDown(@Query("gurl") String gurl, Callback<Response> cb);
    }
}
