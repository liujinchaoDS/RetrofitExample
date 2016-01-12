package com.android.ljc.retrofitutils;


import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.Client;

public class RestAdapterConstructor {


    /**
     * 构造一个RestAdapter
     *
     * @param endPoint 域名（IP+port）
     * @param client   Client
     * @return RestAdapter
     */
    public static RestAdapter getRestAdapter(String endPoint, Client client) {
        return getRestAdapter(endPoint, client, LogLevel.NONE);
    }

    /**
     * 构造一个RestAdapter
     *
     * @param endPoint 域名（IP+port）
     * @param client   Client
     * @param logLevel LogLevel
     * @return RestAdapter
     */
    public static RestAdapter getRestAdapter(String endPoint, Client client, LogLevel logLevel) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(endPoint);
        if (client != null) {
            builder.setClient(client);
        }
        builder.setLogLevel(logLevel);
        return builder.build();
    }

}
