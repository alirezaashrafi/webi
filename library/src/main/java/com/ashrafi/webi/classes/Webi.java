package com.ashrafi.webi.classes;

import android.content.Context;

import com.ashrafi.webi.PostDataModel.Get;
import com.ashrafi.webi.PostDataModel.Header;
import com.ashrafi.webi.PostDataModel.Posts;
import com.ashrafi.webi.enums.Methods;
import com.ashrafi.webi.interfaces.GetResponseTime;
import com.ashrafi.webi.interfaces.OnFailed;
import com.ashrafi.webi.interfaces.OnJsonArrayReceive;
import com.ashrafi.webi.interfaces.OnJsonObjectReceive;
import com.ashrafi.webi.interfaces.OnLog;
import com.ashrafi.webi.interfaces.OnResponse;
import com.ashrafi.webi.interfaces.OnRetry;
import com.ashrafi.webi.interfaces.OnTimeOut;
import com.ashrafi.webi.interfaces.OnXmlReceive;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;

/**
 * Created by AlirezaAshrafi on 12/26/2017.
 */

public class Webi {


    public static String ONLINE = "online";
    public static String SQL = "sql";
    public static String RAM = "ram";
    public static String XML = "xml";


    private WebService webService;
    private WebiEvents webiEvents;

    /**
     * (webi library)
     * <p>
     * init webi
     * <p>
     * used to instantiate context based objects ApplicationContext will be used
     */


    public static Webi with(Context context) {


        Webi webi = new Webi();
        webi.webiEvents = new WebiEvents(context);
        webi.webService = new WebService(context, webi, webi.webiEvents);

        return webi;
    }

    /**
     * (webi library)
     * <p>
     * init request link address
     */

    public Webi from(String url) {
        webService.url = url;
        webService.is_url_seted = true;
        return this;
    }

    public Webi from(Object url) {
        /** (webi library)
         *
         *  convert all objects to string
         *
         */
        webService.is_url_seted = true;

        webService.url = url.toString();
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * call connecting setMethod
     */
    public Webi onResponse(OnResponse onResponse) {
        webiEvents.onResponse = (onResponse);
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * to get response in json format
     */
    public Webi setOnJsonArrayReceive(OnJsonArrayReceive onJsonArrayReceive) {
        webiEvents.onJsonArrayReceive = onJsonArrayReceive;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * to get response in json format
     */
    public Webi setOnJsonObjectReceive(OnJsonObjectReceive onJsonObjectReceive) {
        webiEvents.onJsonObjectReceive = onJsonObjectReceive;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * start connecting to server
     */
    public Webi connect() {
        webService.begin();
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * add key value post request
     */
    public Webi addPost(Object key, Object value) {
        Posts posts = new Posts();
        posts.setKey(String.valueOf(key));
        posts.setValue(String.valueOf(value));
        webService.posts.add(posts);
        webService.is_posts_seted = true;
        return this;
    }

    public Webi addPost(Object key, Object value, Object description) {
        Posts posts = new Posts();
        posts.setKey(String.valueOf(key));
        posts.setValue(String.valueOf(value));
        posts.setDescription(String.valueOf(description));
        webService.posts.add(posts);
        webService.is_posts_seted = true;
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * add list of key value post
     */
    public Webi addPostList(List<Posts> bodies) {
        webService.posts.addAll(bodies);
        webService.is_posts_seted = true;
        return this;
    }

    public Webi setPostList(List<Posts> bodies) {
        webService.posts = (bodies);
        webService.is_posts_seted = true;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * clear list of bodies
     */
    public Webi clearPosts() {
        webService.posts.clear();
        return this;
    }

    public Webi clearGets() {
        webService.gets.clear();
        return this;
    }

    public Webi clearHeaders() {
        webService.gets.clear();
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * http from connection default setUseCaches
     */
    public Webi setAllowHttpCache(boolean httpCache) {
        webService.httpCache = httpCache;
        webService.is_httpCache_seted = true;
        return this;
    }

    public String getUrl() {
        return webService.url;
    }

    public String getFrom() {
        return webService.url;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * setEncryptCache base64 format
     */
    public Webi setEncryptCache(boolean encryptCache) {
        webService.encryptCache = encryptCache;
        webService.is_encryptCache_seted = true;

        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * if true will workOffline cache values but when false cache values is always Fixed
     */
    public Webi setWordOffline(boolean offline) {
        webService.workOffline = offline;
        webService.is_workOffline_seted = true;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * save response in static variable
     */
    public Webi setRamCache(boolean ramCache) {
        webService.is_ramCache_seted = true;
        webService.ramCache = ramCache;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * storage response in share pref
     */
    public Webi setXmlCache(boolean xmlCache) {
        webService.is_xmlCache_seted = true;
        webService.xmlCache = xmlCache;
        return this;
    }

    public Webi setXmlCache(boolean xmlCache, String key) {
        webService.is_xmlCache_seted = true;
        webService.xmlCache = xmlCache;
        webService.xmlCacheKey = key;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * storage response in sqlite database
     */
    public Webi setSqlCache(boolean sqlCache) {
        webService.is_sqlCache_seted = true;
        webService.sqlCache = sqlCache;
        return this;
    }

    public Webi setSqlCache(boolean sqlCache, String key) {
        webService.is_sqlCache_seted = true;
        webService.sqlCache = sqlCache;
        webService.sqlCacheKey = key;
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * set custom read time our default is 5s
     */
    public Webi setReadTimeOut(int readTimeOut) {
        webService.readTimeOut = readTimeOut;
        webService.is_readTimeOut_seted = true;
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * set custom request time our default is 5s
     */
    public Webi setConnectTimeOut(int connectimeOut) {
        webService.connectTimeOut = connectimeOut;
        webService.is_connectTimeOut_seted = true;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * return value of read time out
     */
    public int getReadTimeOut() {
        return webService.readTimeOut;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * return value of request time out
     */
    public int getConnectTimeOut() {
        return webService.connectTimeOut;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * set connection setMethod e.g. POST GET ....
     */
    public Webi setMethod(Methods method) {
        webService.method = method.getValue();
        webService.is_method_seted = true;
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * return connection setMethod
     */
    public String getMethod() {
        return webService.method;
    }


    public Webi setOnLogListener(OnLog onLog) {
        webiEvents.onLog = (onLog);
        return this;
    }

    public Webi getResponseTime(GetResponseTime getResponseTime) {
        webiEvents.getResponseTime = (getResponseTime);
        return this;
    }

    protected WebService getWebService() {
        return webService;
    }

    protected WebiEvents getWebiEvents() {
        return webiEvents;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     *
     * @param key is input of search setMethod
     *            <p>
     *            search in posts list for find value
     */
    String getPost(String key) {
        String value = "";
        for (int i = 0; i < webService.posts.size(); i++) {
            if (webService.posts.get(i).getKey().equals(key)) {
                value = webService.posts.get(i).getKey();
                break;
            }
        }
        return value;
    }

    public Webi setRetryTimes(int retry) {
        webService.retry = retry;
        webService.is_retry_seted = true;
        return this;
    }

    public int getRetryRemaining() {
        return webService.retry;
    }

    public boolean getRamCache() {
        return webService.ramCache;
    }

    public boolean getXmlCache() {
        return webService.xmlCache;
    }

    public boolean getSqlCache() {
        return webService.sqlCache;
    }

    public boolean getUpdateCaches() {
        return webService.workOffline;
    }

    public boolean getHttpCache() {
        return webService.httpCache;
    }

    public boolean getEncryptCache() {
        return webService.encryptCache;
    }

    public List<Posts> getPosts() {
        return webService.posts;
    }

    public Posts getPost(int index) {
        return webService.posts.get(index);
    }

    public Webi setProxy(String host, int port) {
        webService.is_proxy_seted = true;
        webService.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        return this;
    }

    public Webi setProxy(String host, int port, final String username, final String password) {
        webService.is_proxy_seted = true;
        webService.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
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

    public Proxy getProxy() {
        return webService.proxy;
    }

    public Webi setOnRetry(OnRetry onRetry) {
        webiEvents.onRetry = onRetry;
        return this;
    }

    public Webi setOnFailed(OnFailed onFailed) {
        webiEvents.onFailed = onFailed;
        return this;
    }

    public Webi setOnTimeOut(OnTimeOut onTimeOut) {
        webiEvents.onTimeOut = onTimeOut;
        return this;
    }

    public Webi setOnXmlRecevie(OnXmlReceive onXmlRecevie) {
        webiEvents.onXmlReceive = onXmlRecevie;
        return this;
    }

    public Webi addHeader(Object key, Object value) {
        Header header = new Header();
        header.setKey(String.valueOf(key));
        webService.is_headers_seted = true;
        header.setValue(String.valueOf(value));
        webService.headers.add(header);
        return this;
    }

    public Webi addHeader(Object key, Object value, Object description) {
        Header header = new Header();
        header.setKey(String.valueOf(key));
        header.setValue(String.valueOf(value));
        header.setDescription(String.valueOf(description));
        webService.headers.add(header);
        webService.is_headers_seted = true;
        return this;
    }

    public Webi addHeaderList(List<Header> headers) {
        webService.headers.addAll(headers);
        webService.is_headers_seted = true;
        return this;
    }

    public Webi setHeaderList(List<Header> headers) {
        webService.headers = headers;
        webService.is_headers_seted = true;
        return this;
    }

    public Webi addGet(Object key, Object value) {
        Get get = new Get();
        webService.is_gets_seted = true;
        get.setKey(String.valueOf(key));
        get.setValue(String.valueOf(value));
        webService.gets.add(get);
        return this;
    }

    public Webi addGet(Object key, Object value, Object description) {
        webService.is_gets_seted = true;
        Get get = new Get();
        get.setKey(String.valueOf(key));
        get.setValue(String.valueOf(value));
        get.setDescription(String.valueOf(description));
        webService.gets.add(get);
        return this;
    }

    public Webi addGetList(List<Get> gets) {
        webService.is_gets_seted = true;
        webService.gets.addAll(gets);
        return this;
    }

    public Webi setGetList(List<Get> gets) {
        webService.is_gets_seted = true;
        webService.gets = gets;
        return this;
    }

    public List<Header> getHeaders() {
        return webService.headers;
    }

    public Webi setBearerToken(String bearerToken) {
        webService.token = bearerToken;
        webService.is_token_seted = true;
        return this;
    }

    public String getBearerToken() {
        return webService.token;
    }

}

