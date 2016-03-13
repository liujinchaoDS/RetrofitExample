package com.example.version2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.version2.data.ArticleCategory;
import com.example.version2.data.ArticleListDTO;
import com.example.version2.data.Emoij;
import com.example.version2.data.HomeRequest;
import com.example.version2.data.PersonalConfigs;
import com.example.version2.data.PersonalInfo;
import com.example.version2.data.RemindDTO;
import com.example.version2.data.VersionDto;
import com.example.version2.utils.ApiWrapper;
import com.example.version2.utils.IRetrofit;
import com.example.version2.utils.RetrofitUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

public class MainActivity extends BaseActivity {

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = RetrofitUtils.getInstance(this);
    }


    public void emoij(View view) {
        boolean flag = false;
        if (flag) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
                        Call<Emoij> emojis = iRetrofit.emojis();
                        Response<Emoij> execute = emojis.execute();
                        Emoij body = execute.body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
//            Observable<Emoij> obemoijs = iRetrofit.obemoijs();
            Call<Emoij> emojis = iRetrofit.emojis();
            Callback<Emoij> callback = new Callback<Emoij>() {
                @Override
                public void onResponse(Call<Emoij> call, Response<Emoij> response) {

                }

                @Override
                public void onFailure(Call<Emoij> call, Throwable t) {

                }
            };
            emojis.enqueue(callback);
        }
    }


    private void observable() {
        IRetrofit iRetrofit = retrofit.create(IRetrofit.class);
        iRetrofit.obemoijs().subscribe(new Action1<Emoij>() {
            @Override
            public void call(Emoij emoij) {

            }


        });
    }

    void getSms() {
//        Observable
//                .just("1", "2")
//                .interval(2, TimeUnit.SECONDS)
//                .subscribe(new Subscriber<Long>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i(TAG, "AsyncSubject onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "AsyncSubject onError" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(Long s) {
//                        Log.i(TAG, "AsyncSubject onNext" + s);
//                    }
//                });
        ApiWrapper manager = new ApiWrapper();
        manager.getSmsCode2("15813351726")
                .retry(2)
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        Log.i(TAG, "call " + integer);
                        if (throwable instanceof ConnectException && integer < 3)
                            return true;
                        else
                            return false;
                    }
                })
                .subscribe(newSubscriber(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "call " + s);
                    }
                }));
    }


    /**
     * 分类id
     */
    long categoryId;

    void getArticleList() {
        final ApiWrapper wrapper = new ApiWrapper();
        wrapper.getArticleCategory()
                //可以在doOnNext处理数据
                .doOnNext(new Action1<List<ArticleCategory>>() {
                    @Override
                    public void call(List<ArticleCategory> articleCategories) {
                        categoryId = articleCategories.get(0).getId();
                    }
                })
                //设置请求次数
                .retry(new Func2<Integer, Throwable, Boolean>() {
                    @Override
                    public Boolean call(Integer integer, Throwable throwable) {
                        Log.e(TAG, "call " + integer);
                        if (throwable instanceof SocketTimeoutException && integer < 2)
                            return true;
                        else
                            return false;
                    }
                })
                .flatMap(new Func1<List<ArticleCategory>, Observable<List<ArticleListDTO>>>() {
                    @Override
                    public Observable<List<ArticleListDTO>> call(List<ArticleCategory> articleCategories) {
                        return wrapper.getArticleList(categoryId, 1);
                    }
                })
                .subscribe(newSubscriber(new Action1<List<ArticleListDTO>>() {
                    @Override
                    public void call(List<ArticleListDTO> articleList) {
                        for (ArticleListDTO article : articleList) {
                            Log.i(TAG, article.getId() + " " + article.getTitle() + " " + article.getIntro());
                        }
                    }
                }));
        ;
    }


    void getHome() {
        //同时请求多个接口
        ApiWrapper wrapper = new ApiWrapper();
        //将多个接口的返回结果结合成一个对象
        Observable.zip(wrapper.checkVersion(), wrapper.getPersonalInfo(), wrapper.getPersonalConfigs(),
                new Func3<VersionDto, PersonalInfo, PersonalConfigs, HomeRequest>() {
                    @Override
                    public HomeRequest call(VersionDto versionDto, PersonalInfo personalInfo, PersonalConfigs personalConfigs) {
                        HomeRequest request = new HomeRequest();
                        request.setVersionDto(versionDto);
                        request.setPersonalInfo(personalInfo);
                        request.setPersonalConfigs(personalConfigs);
                        return request;
                    }
                })
                .subscribe(newSubscriber(new Action1<HomeRequest>() {
                    @Override
                    public void call(HomeRequest request) {
                        Log.i(TAG, "versionDto--" + request.getVersionDto().toString());
                        Log.i(TAG, "personalInfo--" + request.getPersonalInfo().toString());
                        Log.i(TAG, "PersonalConfigs--" + request.getPersonalConfigs().toString());
                    }
                }))
        ;
//        Subscription s1 = wrapper
//                .checkVersion()
//                .subscribe(newSubscriber(new Action1<VersionDto>() {
//                    @Override
//                    public void call(VersionDto dto) {
//                        Log.i(TAG, "checkVersion--" + dto.toString());
//                    }
//                }));
//        mCompositeSubscription.add(s1);
    }


    void updatePersonalInfo() {
        ApiWrapper wrapper = new ApiWrapper();
        String path = "/storage/emulated/0/Tencent/QQfile_recv/111355.60083131_1280.jpg";
        wrapper.updatePersonalInfo(path)
                .subscribe(newSubscriber(new Action1<PersonalInfo>() {
                    @Override
                    public void call(PersonalInfo personalInfo) {
                        Log.i(TAG, "updatePersonalInfo---" + personalInfo.avatar);
                        //设置圆形头像
                    }
                }));

    }

    void commentProduct() {
        ApiWrapper wrapper = new ApiWrapper();
        long orderId = 511;
        long productId = 9;
        String content = "xixi";
        List<String> paths = Arrays.asList("/storage/emulated/0/UCDownloads/640.jpg",
                "/storage/emulated/0/Pictures/Screenshots/Screenshot_2016-01-11-16-34-44.jpeg");
        wrapper.commentProduct(orderId, productId, content, paths)
                .subscribe(newSubscriber(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
//                        Log.i(TAG, "")
                    }
                }));
    }


    void getNotification() {
        ApiWrapper wrapper = new ApiWrapper();
        Subscription subscription = wrapper.getNotificationList()
                .doOnNext(new Action1<List<RemindDTO>>() {
                    @Override
                    public void call(List<RemindDTO> remindDTOs) {
//                        Collections.
                    }
                })
                .subscribe(newSubscriber(new Action1<List<RemindDTO>>() {
                    @Override
                    public void call(List<RemindDTO> remindDTOs) {

                    }
                }));
        mCompositeSubscription.add(subscription);
    }
}
