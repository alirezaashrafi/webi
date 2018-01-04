package com.ashrafi.webi.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.ashrafi.webi.PostDataModel.Get;
import com.ashrafi.webi.PostDataModel.Header;
import com.ashrafi.webi.PostDataModel.Posts;
import com.ashrafi.webi.enums.Logs;
import com.ashrafi.webi.enums.Methods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

final class WebService {

    /*--*   log tag  *--*/
    private final String TAG = this.getClass().getName();

    protected int retry = 1;
    /*--*   request body array list  *--*/
    protected List<Posts> posts;
    protected List<Header> headers;
    protected List<Get> gets;

    /*--*  webi cache configs  *--*/
    protected boolean ramCache = false;
    protected boolean xmlCache = false;
    protected boolean sqlCache = false;
    protected boolean workOffline = false;
    protected boolean httpCache = true;
    protected boolean encryptCache = false;


    protected String sqlCacheKey = "";
    protected String xmlCacheKey = "";


    protected String url = "";
    protected String method = "POST";
    protected String token = null;

    protected Context context;
    protected String postData = "";


    protected int connectTimeOut = 5000;
    protected int readTimeOut = 5000;


    /*--*   class objects  *--*/
    private Webi webi;
    private WebiEvents webiEvents;

    /*--*   time variable  *--*/
    private long startTime = 0;
    private long elapsedTime = 0;
    private long responseTime = 0;


    /*--*   cacheing class objects  *--*/
    private CacheRam cacheRam;
    private CacheXml cacheXml;
    private CacheSql cacheSql;


    /*--*   key  *--*/
    private String key = "";

    protected Proxy proxy;


    protected boolean is_retry_seted = false;
    protected boolean is_url_seted = false;
    protected boolean is_method_seted = false;
    protected boolean is_token_seted = false;
    protected boolean is_connectTimeOut_seted = false;
    protected boolean is_readTimeOut_seted = false;
    protected boolean is_proxy_seted = false;
    protected boolean is_posts_seted = false;
    protected boolean is_headers_seted = false;
    protected boolean is_gets_seted = false;
    protected boolean is_ramCache_seted = false;
    protected boolean is_xmlCache_seted = false;
    protected boolean is_sqlCache_seted = false;
    protected boolean is_workOffline_seted = false;
    protected boolean is_httpCache_seted = false;
    protected boolean is_encryptCache_seted = false;

    //1
    WebService(Context context, Webi webi, WebiEvents webiEvents) {
        this.posts = new ArrayList<>();
        this.headers = new ArrayList<>();
        this.gets = new ArrayList<>();
        this.webiEvents = webiEvents;
        this.webi = webi;
        this.context = context;
        loadDefaults();
    }

    private void loadDefaults() {
        WebiConfig.init();
        if (!is_retry_seted) {
            this.retry = WebiConfig.retry;
        }

        if (!is_url_seted) {
            this.url = WebiConfig.url;
        }
        if (!is_method_seted) {
            this.method = WebiConfig.method;
        }
        if (!is_token_seted) {
            this.token = WebiConfig.token;
        }
        if (!is_connectTimeOut_seted) {
            this.connectTimeOut = WebiConfig.connectTimeOut;
        }
        if (!is_readTimeOut_seted) {
            this.readTimeOut = WebiConfig.readTimeOut;
        }
        if (!is_proxy_seted) {
            this.proxy = WebiConfig.proxy;
        }
        if (!is_posts_seted) {
            this.posts = WebiConfig.posts;
        }
        if (!is_headers_seted) {
            this.headers = WebiConfig.headers;
        }
        if (!is_gets_seted) {
            this.gets = WebiConfig.gets;
        }
        if (!is_ramCache_seted) {
            this.ramCache = WebiConfig.ramCache;
        }
        if (!is_xmlCache_seted) {
            this.xmlCache = WebiConfig.xmlCache;
        }
        if (!is_sqlCache_seted) {
            this.sqlCache = WebiConfig.sqlCache;
        }
        if (!is_workOffline_seted) {
            this.workOffline = WebiConfig.workOffline;
        }
        if (!is_httpCache_seted) {
            this.httpCache = WebiConfig.httpCache;
        }
        if (!is_encryptCache_seted) {
            this.encryptCache = WebiConfig.encryptCache;
        }
    }

    //2
    protected void begin() {
        startBackground();
    }


    //3
    private void startBackground() {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                configs();

                return null;
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //4
    private void configs() {
        //build string of post data
        postData = new PostDataBuilder().buildPostData(posts);

        //hash key
        key = new HashKey(url + postData).build();//hash


        //cache configs
        if (ramCache) {
            cacheRam = new CacheRam(key);
        }
        if (xmlCache) {
            if (xmlCacheKey.equals("")) {
                cacheXml = new CacheXml(context, key);
            } else {
                cacheXml = new CacheXml(context, xmlCacheKey);
            }
        }
        if (sqlCache) {
            if (sqlCacheKey.equals("")) {
                cacheSql = new CacheSql(context, key);
            } else {
                cacheSql = new CacheSql(context, sqlCacheKey);
            }
        }

        onSetup();
    }


    //5
    private void onSetup() {
        onStart();//call on start setMethod


        String where = "";
        String data = "";
        if (ramCache) {
            if (cacheRam.isExist()) {
                where = Webi.RAM;
                data = cacheRam.get();
            }
        }

        if (xmlCache) {
            if (cacheXml.contains()) {
                where = Webi.XML;
                data = cacheXml.get();
                if (encryptCache) {
                    data = new Encryption().decode(data);
                }
            }
        }

        if (sqlCache) {

            cacheSql.get(new CacheSql.DataRecived() {
                @Override
                public void data(String string) {
                    if (encryptCache) {
                        string = new Encryption().decode(string);
                    }

                    onDataReceive(string, Webi.SQL);

                }
            });

        }

        if (!data.equals("")) {
            onDataReceive(data, where);
        }


        if (!workOffline) {
            onExecute();
            /*--*   start background thread  *--*/
        } else {
            webiEvents.onLog(Logs.WARN, "work offline is on and only read from cache");
        }


    }


    //6
    protected void onExecute() {
        /*--*   start connection  *--*/
        openHttpURLConnection();
        /*--*   start requesting to server  *--*/

    }


    //4
    private void openHttpURLConnection() {

        HttpURLConnection httpURLConnection = null;

        try {

            if (gets.size() != 0) {
                this.url = buildURI(this.url, gets);
            }

            URL url = new URL(this.url);



            /*--*   http from connection  *--*/

            if (proxy != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                onInfo("Proxy connected to the HttpURLConnection");
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }


            httpURLConnection.setReadTimeout(readTimeOut);
            httpURLConnection.setConnectTimeout(connectTimeOut);

            httpURLConnection.setRequestMethod(method);

            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(!method.equals(Methods.GET.getValue()));
            httpURLConnection.setUseCaches(httpCache);

            httpURLConnection.connect();

            setHeaderData(httpURLConnection);

            writeOnHttpConnection(httpURLConnection);
            /*--*   write posts  *--*/

            String data = readHttpConnection(httpURLConnection);
            /*--*   read response  *--*/


            onDataReceive(data, Webi.ONLINE);
            /*--*   when data receive  *--*/

                /*--*   disconnect http connection  *--*/
            httpURLConnection.disconnect();
            onInfo("disconnect httpURLConnection");

        } catch (SocketTimeoutException e) {

            /*--*   when time out connection  *--*/
            webiEvents.OnTimeOut();
            onError(e.getMessage());
            onRetry();

        } catch (ProtocolException e) {
            onError(e.getMessage());
        } catch (Exception e) {
            onError(e.getMessage());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

    }

    private String buildURI(String url, List<Get> gets) {

        // build url with parameters.
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (int i = 0; i < gets.size(); i++) {
            builder.appendQueryParameter(gets.get(i).getKey(), gets.get(i).getValue());
        }

        return builder.toString();
    }

    private void setHeaderData(HttpURLConnection httpURLConnection) {
        /*httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");*/

        if (token != null) {
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
        }

        for (int i = 0; i < headers.size(); i++) {
            httpURLConnection.setRequestProperty(headers.get(i).getKey(), headers.get(i).getValue());
        }


    }


    //5
    private void onStart() {
        startTime = System.currentTimeMillis();
    }


    //6
    private void writeOnHttpConnection(HttpURLConnection httpURLConnection) {
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));


            writer.write(postData);

            writer.flush();
            writer.close();
            outputStream.close();

        } catch (IOException e) {

            onError(e.getMessage());

        } catch (Exception e) {
            onError(e.getMessage());
        }

    }

    private void onError(String error) {
        webiEvents.onLog(Logs.ERROR, error);
    }

    private void onInfo(String error) {
        webiEvents.onLog(Logs.INFO, error);
    }

    //6.5
    private void onRetry() {
        if (retry != 0) {
            retry--;
            webiEvents.onRetry(retry);
            onInfo("retry");
            onExecute();
        }
    }


    //7
    private String readHttpConnection(HttpURLConnection httpURLConnection) {
        final StringBuilder stringBuilder = new StringBuilder();

        int code = 0;
        try {
            code = httpURLConnection.getResponseCode();
            onInfo("response code = " + code + " : " + new HttpCode().httpCode(code));
            if (code == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } else {
                webiEvents.OnFailed(code);
                onError("httpURLConnection is not 200 or OK");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            onError(e.getMessage());
            return "";
        }
    }


    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * parse geted string
     * send to interface
     * cache if developer want
     * parse json xml if want
     * encrypt if want
     */

    private String lastResponse = "";

    //9 //3
    private void onDataReceive(String data, String where) {

        if (lastResponse.length() != data.length()) {

            onInfo("new response received from " + where);
            lastResponse = data;
            /*--*   call done setMethod  *--*/
            onDone();



            /*--*   post receive to interface  *--*/
            webiEvents.onResponse(data, where);

            /*--*   json parser  *--*/
            webiEvents.onJsonArray(data, where);
            webiEvents.onJsonObject(data, where);

            /*--*   xml parse  *--*/
            webiEvents.OnXmlReceive(data, where);

            if ((sqlCache || xmlCache) && (encryptCache)) {

                Encryption encryption = new Encryption();
                String encrypted = encryption.encode(data);

            /*--*   if enctypt Cache is true first cache Then send to cache  *--*/
                cache(encrypted, where);

            } else {
            /*--*   else enctypt is false Unchanged data  *--*/
                cache(data, where);
            }
        } else {
            webiEvents.onLog(Logs.WARN, "response not changed from = " + where);
        }

    }

    private void cache(String data, String where) {
        if (where.equals(Webi.ONLINE)) {
            if (xmlCache) {
                cacheXml.put(data);
            }

            if (ramCache) {
                cacheRam.put(data);
            }

            if (sqlCache) {
                cacheSql.put(data);
            }
        }

    }

    private void onDone() {
        elapsedTime = System.currentTimeMillis();
        responseTime = elapsedTime - startTime;

        /*--*   post responseTime to interface  *--*/
        webiEvents.onResponseTime(responseTime);

    }
}
