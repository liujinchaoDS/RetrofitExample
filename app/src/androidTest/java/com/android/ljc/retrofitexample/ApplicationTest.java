package com.android.ljc.retrofitexample;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofitutils.RestAdapterConstructor;
import retrofitutils.RetrofitRestAdapterUtils;
import retrofitutils.data.BaseRetrofit;
import retrofitutils.user.Login;
import retrofitutils.user.Login.LoginService;
import retrofitutils.user.Login.User;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);



    }


    public void testRRA(){
        RetrofitRestAdapterUtils.getUserSystemInstance(getContext());
    }

    public void testLogin(){
        RestAdapter restAdapter = RetrofitRestAdapterUtils.getUserSystemInstance(getContext());
        LoginService loginService =restAdapter.create(LoginService.class);
        Callback<BaseRetrofit<User>> cb = new Callback<BaseRetrofit<User>>() {
            @Override
            public void success(BaseRetrofit<User> loginBaseRetrofit, Response response) {
                Log.e("user",loginBaseRetrofit.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("user",error.toString());
            }
        };
        loginService.login("18636909627","111111",cb);

    }
}