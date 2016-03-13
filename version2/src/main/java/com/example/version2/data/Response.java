package com.example.version2.data;


public class Response<T> {

    public static final int SUCCESS = 0;

    public int code;
    public String message;
    public T data;

}
