package com.android.ljc.retrofitutils;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class NeedGetTokenUtils {

    private static NeedGetToken needGetToken;

    public static NeedGetToken getInstance() {
        if (needGetToken == null) {
            needGetToken = new NeedGetToken() {
                @Override
                public NeedGetTokenData isNeedGetToken(int code, ResponseBody responseBody) {
                    NeedGetTokenData needGetTokenData = new NeedGetTokenData();
                    //如果状态码为400
                    if (code == 400) {
                        try {
                            //得到错误信息
                            String message = responseBody.string();
                            //保存错误信息
                            needGetTokenData.response = message;
                            //如果错误信息是以下几种
                            if (message.contains("The message expired") || message.contains("Invalid access token") || message.contains("Message signature was incorrect") || message.contains("Missing access token")) {
                                needGetTokenData.isNeedGetToken = true;
                                return needGetTokenData;
                            } else {
                                //重构响应(响应体已被使用，不重构会报错)  返回
                                needGetTokenData.isNeedGetToken = false;
                                return needGetTokenData;
                            }
                        } catch (IOException e) {
                            needGetTokenData.isNeedGetToken = false;
                            return needGetTokenData;
                        }
                    } else {
                        needGetTokenData.isNeedGetToken = false;
                        return needGetTokenData;
                    }
                }
            };
        }
        return needGetToken;
    }

    /**
     * 判断是否需要获取Token
     */
    public interface NeedGetToken {
        NeedGetTokenData isNeedGetToken(int code, ResponseBody responseBody);
    }

    /**
     * 判断响应结构
     */
    public static class NeedGetTokenData {
        /**
         * 是否需要重新获取
         */
        public boolean isNeedGetToken;
        /**
         * 返回用于重构的响应消息
         */
        public String response;
    }
}


