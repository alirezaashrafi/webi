package com.ashrafi.webi.enums;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

public enum Logs {

    ERROR("ERROR"),
    INFO("INFO"),
    WARN("WARN");


    private String log;

    Logs(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }



}
