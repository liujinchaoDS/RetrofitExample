package com.android.ljc.retrofitexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofitutils.RetrofitRestAdapterUtils;
import retrofitutils.data.BaseRetrofit;
import retrofitutils.user.Login.LoginService;
import retrofitutils.user.Login.User;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void test(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RestAdapter restAdapter = RetrofitRestAdapterUtils.getUserSystemInstance(MainActivity.this);
                LoginService loginService =restAdapter.create(LoginService.class);
                Callback<BaseRetrofit<User>> cb = new Callback<BaseRetrofit<User>>() {
                    @Override
                    public void success(BaseRetrofit<User> loginBaseRetrofit, Response response) {
                        Log.e("user",loginBaseRetrofit.toString());
                        ((Button)findViewById(R.id.button)).setText(loginBaseRetrofit.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("user", error.toString());
                    }
                };
                loginService.login("18636909627","111111",cb);
            }
        }).start();

    }
}
