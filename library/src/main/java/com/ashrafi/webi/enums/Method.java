package com.ashrafi.webi.enums;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

public enum Method {
    POST("POST"),
    GET("GET");


    private String value;

    Method(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
