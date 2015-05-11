package retrofitutils;


import android.content.Context;

import com.android.ljc.retrofitexample.R;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofitutils.data.Token;

public class RetrofitRestAdapterUtils {

    private static RestAdapter userSystemAdapter;

    public static RestAdapter getUserSystemInstance(Context context) {
        if (userSystemAdapter == null) {
            OkHttpClient client = new OkHttpClient();
//            networkInterceptors() 网络层拦截器 每次调用一次 方法中只能执行一次请求
//            interceptors() 应用层拦截器 方法中可以执行多次请求
            client.interceptors().add(new OAuthInterceptor(context, Token.USER_SHARED_TOKEN, R.string.userOAuthIp, R.string.userCredentials).setNeedGetToken(NeedGetTokenUtils.getInstance()));
            userSystemAdapter = RestAdapterConstructor.getRestAdapter(context.getApplicationContext().getString(R.string.userSystemIp), new OkClient(client));
        }
        return userSystemAdapter;
    }


    /**
     * 得到OAuth的RestAdapter
     *
     * @param endPoint 域名
     * @return RestAdapter
     */
    public static RestAdapter getOAuthInstance(String endPoint) {
        return getOAuthInstance(endPoint, LogLevel.FULL);
    }

    /**
     * 得到OAuth的RestAdapter
     *
     * @param endPoint 域名
     * @param logLevel LogLevel
     * @return RestAdapter
     */
    public static RestAdapter getOAuthInstance(String endPoint, LogLevel logLevel) {
        Client client = new OkClient(new OkHttpClient());
        return new RestAdapter.Builder().setEndpoint(endPoint).setClient(client).setLogLevel(logLevel).build();
    }


}
