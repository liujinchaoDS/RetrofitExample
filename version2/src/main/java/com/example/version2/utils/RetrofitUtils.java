package com.example.version2.utils;


import com.example.version2.data.Response;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class RetrofitUtils {

     /*
    * https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java
    * http://stackoverflow.com/questions/29958881/download-progress-with-rxjava-okhttp-and-okio-in-android
    * 下载文件的进度
    * */


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
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
