package com.ashrafi.webi.classes;

import java.util.HashMap;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

final class CacheRam {
    private static HashMap<String, String> caches = new HashMap<>();
    private String key = "";
    protected CacheRam(String key){
        HashKey hash = new HashKey(key);
        this.key = hash.build();
    }
    protected void put( String value) {
        if (isExist()){
            caches.remove(key);
        }
        caches.put(key, value);
    }

    protected String get() {
        return caches.get(key);
    }

    protected boolean isExist(){
        return caches.containsKey(key);
    }
}
