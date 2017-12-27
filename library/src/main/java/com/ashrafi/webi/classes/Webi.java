package com.ashrafi.webi.classes;

import android.content.Context;

import com.ashrafi.webi.PostDataModel.Posts;
import com.ashrafi.webi.enums.Method;
import com.ashrafi.webi.interfaces.GetResponseTime;
import com.ashrafi.webi.interfaces.OnJsonArrayReceive;
import com.ashrafi.webi.interfaces.OnJsonObjectReceive;
import com.ashrafi.webi.interfaces.OnLog;
import com.ashrafi.webi.interfaces.OnResponse;

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
        return this;
    }

    public Webi from(Object url) {
        /** (webi library)
         *
         *  convert all objects to string
         *
         */
        webService.url = url.toString();
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * call connecting method
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
    public Webi jsonArrayRequest(OnJsonArrayReceive onJsonArrayReceive) {
        webiEvents.onJsonArrayReceive = onJsonArrayReceive;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * to get response in json format
     */
    public Webi jsonObjectRequest(OnJsonObjectReceive onJsonObjectReceive) {
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
     * (webi library github - by alireza ashrafi)
     * <p>
     * add key value post request
     */
    public Webi addPost(String key, String value) {
        Posts posts = new Posts();
        posts.setKey(key);
        posts.setValue(value);
        webService.postsList.add(posts);
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
        webService.postsList.add(posts);
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * add list of key value post
     */
    public Webi addPostList(List<Posts> bodies) {
        webService.postsList.addAll(bodies);
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * clear list of bodies
     */
    public Webi clearPosts() {
        webService.postsList.clear();
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * http from connection default setUseCaches
     */
    public Webi allowHttpCache(boolean httpCache) {
        webService.httpCache = httpCache;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * encryptCache base64 format
     */
    public Webi encryptCache(boolean encryptCache) {
        webService.encryptCache = encryptCache;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * if true will workOffline cache values but when false cache values is always Fixed
     */
    public Webi wordOffline(boolean offline) {
        webService.workOffline = offline;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * save response in static variable
     */
    public Webi ramCache(boolean ramCache) {
        webService.ramCache = ramCache;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * storage response in share pref
     */
    public Webi xmlCache(boolean xmlCache) {
        webService.xmlCache = xmlCache;
        return this;
    }


    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * storage response in sqlite database
     */
    public Webi sqlCache(boolean sqlCache) {
        webService.sqlCache = sqlCache;
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * set custom read time our default is 5s
     */
    public Webi readTimeOut(int readTimeOut) {
        webService.readTimeOut = readTimeOut;
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * set custom request time our default is 5s
     */
    public Webi connectTimeOut(int connectimeOut) {
        webService.connectTimeOut = connectimeOut;
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
     * set connection method e.g. POST GET ....
     */
    public Webi method(Method method) {
        webService.method = method.getValue();
        return this;
    }

    /**
     * (webi library github - by alireza ashrafi)
     * <p>
     * return connection method
     */
    public String getMethod() {
        return webService.method;
    }


    public Webi onLog(OnLog onLog) {
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
     * @param key is input of search method
     *            <p>
     *            search in posts list for find value
     */
    String getPost(String key) {
        String value = "";
        for (int i = 0; i < webService.postsList.size(); i++) {
            if (webService.postsList.get(i).getKey().equals(key)) {
                value = webService.postsList.get(i).getKey();
                break;
            }
        }
        return value;
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
        return webService.postsList;
    }

    Posts getPost(int index) {
        return webService.postsList.get(index);
    }

}

