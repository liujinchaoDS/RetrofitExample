package com.example.version2.utils;


import android.content.Context;

import com.example.version2.R;
import com.example.version2.data.Response;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {

    private static Retrofit retrofit;

    public static Retrofit getInstance(Context context) {
        return getInstance(context.getString(R.string.address_host));
    }

    public static IRetrofit getService(Context context) {
        return getInstance(context).create(IRetrofit.class);
    }

    public static APIService getService() {
        return getInstance("https://api.github.com/").create(APIService.class);
    }

    public static Retrofit getInstance(String baseUrl) {
        if (retrofit == null) {
            //必须使用Builder，直接new OkHttpClient，无法添加Interceptor，因为interceptors()返回的列表不允许改变
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.interceptors().add(new HttpLoggingInterceptor());
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            OkHttpClient client = builder.build();
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create()).build();
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        }
        return retrofit;
    }


    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (response.code == Response.SUCCESS) {
                    subscriber.onNext(response.data);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new APIException(response.code, response.message));
                }
            }
        });
    }


    /**
     * http://www.jianshu.com/p/e9e03194199e
     * 木有十分懂，，
     *
     * @param <T>
     * @return
     */
    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 当{@link IRetrofit}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    protected RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    protected RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }


}
