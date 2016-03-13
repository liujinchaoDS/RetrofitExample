package com.example.version2.utils;

import com.example.version2.data.Response;

/**
 * 自定义异常，当接口返回的{@link Response#code}不为{@link Response#SUCCESS}时，需要抛出此异常
 * eg：登陆时验证码错误；参数未传递等
 */

public class APIException extends Exception {
    public int code;
    public String message;

    public APIException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}