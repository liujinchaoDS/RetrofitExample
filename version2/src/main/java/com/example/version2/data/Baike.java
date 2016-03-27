package com.example.version2.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Baike {

    public int id;
    public int subLemmaId;
    public int newLemmaId;
    public String key;
    public String desc;
    public String title;
    public String image;
    public String src;
    public int imageHeight;
    public int imageWidth;
    public String isSummaryPic;
    @SerializedName("abstract")
    public String abstractX;
    public String url;
    public String wapUrl;
    public int hasOther;
    public String totalUrl;
    public String logo;
    public String copyrights;
    public String customImg;
    public List<CardEntity> card;
    public List<Integer> moduleIds;
    public List<String> catalog;
    public List<String> wapCatalog;

    public static class CardEntity {
        public String key;
        public String name;
        public List<String> value;
        public List<String> format;
    }
}
