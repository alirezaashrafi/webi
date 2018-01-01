package com.ashrafi.webi.enums;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

public enum Methods {
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD"),
    TRACE("TRACE"),
    GET("GET");


    private String value;

    Methods(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
