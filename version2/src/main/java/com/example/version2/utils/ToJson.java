package com.example.version2.utils;

import com.google.gson.Gson;

public class ToJson {

    static Gson gson = new Gson();

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
