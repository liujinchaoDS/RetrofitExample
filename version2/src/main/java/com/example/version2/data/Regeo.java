package com.example.version2.data;

import com.example.version2.utils.ToJson;

import java.util.List;

public class Regeo extends ToJson{

    public String status;
    public DataEntity data;

    public static class DataEntity extends ToJson{
        public String province;
        public String code;
        public String tel;
        public String cityadcode;
        public String areacode;
        public String timestamp;
        public String pos;
        public String result;
        public String message;
        public String desc;
        public String city;
        public String districtadcode;
        public String district;
        public String country;
        public String provinceadcode;
        public String version;
        public String adcode;
        public List<CrossListEntity> cross_list;
        public List<RoadListEntity> road_list;
        public List<PoiListEntity> poi_list;
    }

    public static class CrossListEntity extends Entity {
        public String weight;
        public String level;
        public String crossid;
        public String width;
    }

    public static class RoadListEntity extends Entity {
        public String level;
        public String width;
        public String roadid;
    }

    public static class PoiListEntity extends Entity {
        public String tel;
        public String weight;
        public String typecode;
        public String address;
        public String type;
        public String poiid;
    }

    public static class Entity extends ToJson{
        public String longitude;
        public String latitude;
        public String distance;
        public String direction;
        public String name;
    }
}
