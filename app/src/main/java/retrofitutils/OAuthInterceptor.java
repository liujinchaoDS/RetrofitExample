package retrofitutils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Base64;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import Utils.SharedPreferencesUtils;
import retrofit.RestAdapter;
import retrofitutils.NeedGetTokenUtils.NeedGetToken;
import retrofitutils.NeedGetTokenUtils.NeedGetTokenData;
import retrofitutils.data.Token;
import retrofitutils.data.Token.GetToken;


/**
 * OAuth应用层拦截器
 * <p/>
 * 实现了响应判断和重新获取Token
 * 也可使用一个ApplicationInterceptor负责重试请求+一个NetworkInterceptor负责添加头
 * （如果ApplicationInterceptor获取Token，ApplicationInterceptor判断响应，需要把Token及时更新到NetworkInterceptor）
 * （如果NetworkInterceptor获取Token，ApplicationInterceptor判断响应，需要ApplicationInterceptor也判断响应）
 */
public final class OAuthInterceptor implements Interceptor {
    /**
     * 用于获取token，保存token，获取配置的Ip，credentials
     */
    private Context context;

    /**
     * SharedPreferences保存时的关键字
     */
    private String sharedKey;

    /**
     * OAuth接口地址(使用资源Id获取)
     */
    private String oauthIp;

    /**
     * OAuth所需参数
     * (使用资源Id获取   或   通过"clientid:client_secret" Base64加密生成 )
     */
    private String credentials;

    /**
     * 是否需要获取Token
     */
    private NeedGetToken needGetToken;

    /**
     * AccessToken 通过SharedPreferences获取
     */
    private String accessToken;


    /**
     * @param context          Context
     * @param sharedKey        保存token的key
     * @param oauthIpResId     OAuth IP 地址Id
     * @param credentialsResId OAuth credentialsId
     */
    public OAuthInterceptor(@NonNull Context context, @NonNull String sharedKey, @StringRes int oauthIpResId, @StringRes int credentialsResId) {
        //使用ApplicationContext防止内存泄漏
        this.context = context.getApplicationContext();
        this.setOAuthInterceptor(context, sharedKey, oauthIpResId, this.context.getString(credentialsResId));
    }

    /**
     * @param context           Context
     * @param sharedKey         保存token的key
     * @param oauthIpResId      OAuth IP 地址Id
     * @param clientidResId     OAuth clientid Id
     * @param clientSecretResId OAuth clientSecret Id
     */
    public OAuthInterceptor(@NonNull Context context, @NonNull String sharedKey, @StringRes int oauthIpResId, @StringRes int clientidResId, @StringRes int clientSecretResId) {
        //使用ApplicationContext防止内存泄漏
        this.context = context.getApplicationContext();
        String credentials = getCredentials(context, clientidResId, clientSecretResId);
        this.setOAuthInterceptor(context, sharedKey, oauthIpResId, credentials);
    }


    /**
     * @param context      Context
     * @param sharedKey    保存token的key
     * @param oauthIpResId OAuth IP 地址Id
     * @param credentials  OAuth credentials
     */
    private void setOAuthInterceptor(Context context, String sharedKey, int oauthIpResId, String credentials) {
        this.context = context.getApplicationContext();
        this.sharedKey = sharedKey;
        this.credentials = credentials;
        this.oauthIp = this.context.getString(oauthIpResId);
        this.accessToken = SharedPreferencesUtils.getSharedPreferences(context).getString(this.sharedKey, "");
    }


    /**
     * 一个请求进入一次这个方法
     *
     * @param chain Chain
     * @return Response
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取Request
        Request request = chain.request();
        //为Request添加头
        request = buildRequest(request);
        //执行请求，得到Response
        Response response = chain.proceed(request);
        //获取得到Response状态码
        int code = response.code();
        //得到响应体
        ResponseBody responseBody = response.body();
        if (needGetToken != null) {
            NeedGetTokenData needGetTokenData = needGetToken.isNeedGetToken(code, responseBody);
            if (needGetTokenData.isNeedGetToken) {
                //使用Retrofit重新获取token
                RestAdapter restAdapter = RetrofitRestAdapterUtils.getOAuthInstance(oauthIp);
                GetToken getToken = restAdapter.create(GetToken.class);
                Token token = getToken.getToken(Token.getHeaderProperty(credentials), Token.OAUTH_GRANT_TYPE);
                //修改本地变量
                this.accessToken = token.access_token;
                // 手动修改headers
                request = buildRequest(request);
                //保存到SharedPreferences
                SharedPreferencesUtils.getEditor(context).putString(sharedKey, accessToken).apply();
                // 重试
                return chain.proceed(request);
            } else {
                if (needGetTokenData.response!=null){
                    //重构响应
                    return response.newBuilder().body(ResponseBody.create(responseBody.contentType(), needGetTokenData.response)).build();
                }else{
                    //直接返回响应
                    return response;
                }
            }
        } else {
            //返回响应
            return response;
        }
    }


    /**
     * 手动重构请求
     *
     * @param request Request
     * @return Request
     */
    private Request buildRequest(@NonNull Request request) {
        return request.newBuilder().header("Authorization", "Bearer " + accessToken).build();
    }

    /**
     * 根据clientidResId clientSecretResId 生成Credentials
     *
     * @param context           Context
     * @param clientidResId     clientid资源Id
     * @param clientSecretResId clientSecret资源Id
     * @return Credentials
     */
    private String getCredentials(Context context, int clientidResId, int clientSecretResId) {
        Context applicationContext = context.getApplicationContext();
        String temp = applicationContext.getString(clientidResId) + ":" + applicationContext.getString(clientSecretResId);
        //使用Base64.NO_WRAP 去除换行符
        return Base64.encodeToString(temp.getBytes(), Base64.NO_WRAP);
    }


    /**
     * 设置是否需要获取Token接口
     *
     * @param needGetToken NeedGetToken
     */
    public OAuthInterceptor setNeedGetToken(@Nullable NeedGetToken needGetToken) {
        this.needGetToken = needGetToken;
        return this;
    }


}
