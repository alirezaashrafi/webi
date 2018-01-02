package com.ashrafi.webi.classes;


import android.os.Bundle;

import com.ashrafi.webi.PostDataModel.Get;
import com.ashrafi.webi.PostDataModel.Header;
import com.ashrafi.webi.PostDataModel.Posts;
import com.ashrafi.webi.enums.Methods;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class WebiConfig {

    public WebiConfig() {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        if (headers== null) {
            headers = new ArrayList<>();
        }
        if (gets == null) {
            gets = new ArrayList<>();
        }
    }


    protected static int retry = 1;
    protected static String url = "";
    protected static String method = "POST";
    protected static String token = null;
    protected static int connectTimeOut = 5000;
    protected static int readTimeOut = 5000;
    protected static Proxy proxy;
    protected static List<Posts> posts;
    protected static List<Header> headers;
    protected static List<Get> gets;
    protected static boolean ramCache = false;
    protected static boolean xmlCache = false;
    protected static boolean sqlCache = false;
    protected static boolean workOffline = false;
    protected static boolean httpCache = true;
    protected static boolean encryptCache = false;


    public int getDefaultRetry() {
        return retry;
    }

    public String getDefaultUrl() {
        return url;
    }

    public String getDefaultMethod() {
        return method;
    }

    public String getDefaultToken() {
        return token;
    }

    public int getDefaultConnectTimeOut() {
        return connectTimeOut;
    }

    public int getDefaultReadTimeOut() {
        return readTimeOut;
    }

    public Proxy getDefaultProxy() {
        return proxy;
    }

    public List<Posts> getDefaultPosts() {
        return posts;
    }

    public List<Header> getDefaultHeaders() {
        return headers;
    }

    public List<Get> getDefaultGets() {
        return gets;
    }

    public boolean getDefaultRamCache() {
        return ramCache;
    }

    public boolean getDefaultXmlCache() {
        return xmlCache;
    }

    public boolean getDefaultSqlCache() {
        return sqlCache;
    }

    public boolean getDefaultWorkOffline() {
        return workOffline;
    }

    public boolean getDefaultHttpCache() {
        return httpCache;
    }

    public boolean getDefaultEncryptCache() {
        return encryptCache;
    }


    public WebiConfig setDefaultBearerToken(String token) {
        WebiConfig.token = token;
        return this;
    }

    public WebiConfig setDefaultPosts(List<Posts> posts) {
        WebiConfig.posts = posts;
        return this;
    }

    public WebiConfig setDefaultHeaders(List<Header> headers) {
        WebiConfig.headers = headers;
        return this;
    }

    public WebiConfig setDefaultGets(List<Get> gets) {
        WebiConfig.gets = gets;
        return this;
    }

    public WebiConfig setDefaultHttpCache(boolean httpCache) {
        WebiConfig.httpCache = httpCache;
        return this;
    }

    public WebiConfig setDefaultEncryptCache(boolean encryptCache) {
        WebiConfig.encryptCache = encryptCache;
        return this;
    }


    public WebiConfig setDefaultUrl(String url) {
        WebiConfig.url = url;
        return this;
    }

    public WebiConfig setDefaultUrl(Object url) {
        WebiConfig.url = url.toString();
        return this;
    }


    public WebiConfig setDefaultMethod(Methods method) {
        WebiConfig.method = method.getValue();
        return this;
    }


    public WebiConfig setDefaultRetry(int retry) {
        WebiConfig.retry = retry;
        return this;
    }

    public WebiConfig setDefaultConnectTimeOut(int connectTimeOut) {
        WebiConfig.connectTimeOut = connectTimeOut;
        return this;
    }

    public WebiConfig setDefaultReadTimeOut(int readTimeOut) {
        WebiConfig.readTimeOut = readTimeOut;
        return this;
    }

    public WebiConfig setDefaultRamCache(boolean ramCache) {
        WebiConfig.ramCache = ramCache;
        return this;
    }

    public WebiConfig setDefaultXmlCache(boolean xmlCache) {
        WebiConfig.xmlCache = xmlCache;
        return this;
    }

    public WebiConfig setDefaultSqlCache(boolean sqlCache) {
        WebiConfig.sqlCache = sqlCache;
        return this;
    }

    public WebiConfig setDefaultWorkOffline(boolean workOffline) {
        WebiConfig.workOffline = workOffline;
        return this;
    }


    public WebiConfig setDefaultProxy(String host, int port) {
        WebiConfig.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        return this;
    }

    public WebiConfig setDefaultProxy(String host, int port, final String username, final String password) {
        WebiConfig.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        System.setProperty("http.proxyHost", host);
        System.setProperty("http.proxyPort", String.valueOf(port));
        System.setProperty("http.proxyUser", username);
        System.setProperty("http.proxyPassword", password);

        Authenticator.setDefault(
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password.toCharArray());
                    }
                }
        );

        return this;
    }

    public static WebiConfig init() {

        WebiConfig webiConfig = new WebiConfig();
        return webiConfig;
    }

}