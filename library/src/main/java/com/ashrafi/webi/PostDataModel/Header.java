package com.ashrafi.webi.PostDataModel;

/**
 * Created by AlirezaAshrafi on 1/1/2018.
 */

public class Header {
    private String key = "";
    private String value = "";

    private String description = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
