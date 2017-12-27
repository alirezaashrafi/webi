package com.ashrafi.webi.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.ashrafi.webi.DataBuilder.PostDataBuilder;
import com.ashrafi.webi.PostDataModel.Posts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

public class WebService {

    /*--*   log tag  *--*/
    private final String TAG = this.getClass().getName();

    /*--*   request body array list  *--*/
    protected List<Posts> postsList;

    /*--*  webi cache configs  *--*/
    protected boolean ramCache = false;
    protected boolean xmlCache = false;
    protected boolean sqlCache = false;
    protected boolean workOffline = false;
    protected boolean httpCache = true;
    protected boolean encryptCache = false;


    protected String url = "";
    protected String method = "POST";

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

    //0
    WebService(Context context, Webi webi, WebiEvents webiEvents) {
        this.postsList = new ArrayList<>();
        this.webiEvents = webiEvents;
        this.webi = webi;
        this.context = context;

        Handler handler = new Handler(context.getMainLooper());
    }

    //1
    protected void begin() {
        //build string of post data
        postData = new PostDataBuilder().buildPostData(postsList);

        key = new HashKey(url + postData).build();//hash


        if (ramCache) {
            cacheRam = new CacheRam(key);
        }
        if (xmlCache) {
            cacheXml = new CacheXml(context, key);
        }
        if (sqlCache) {
            cacheSql = new CacheSql(context, key);
        }


        onSetup();
    }


    //2
    private void onSetup() {
        onStart();//call on start method


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
                    } else {
                        string = string;
                    }

                    onDataReceive(string, Webi.SQL);

                }
            });

        }

        if (!data.equals("")) {
            onDataReceive(data, where);
        }


        if (!workOffline) {
            doInBackground();
            /*--*   start background thread  *--*/
        }


    }


    //3
    private void doInBackground() {

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                /*--*   start connection  *--*/
                openHttpURLConnection();
                /*--*   start requesting to server  *--*/

                return null;
            }
        };
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    //4
    private HttpURLConnection openHttpURLConnection() {


        HttpURLConnection httpURLConnection = null;
        try {
            URL urlObject = new URL(url);

            /*--*   http from connection  *--*/
            httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setReadTimeout(readTimeOut);
            httpURLConnection.setConnectTimeout(connectTimeOut);
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(httpCache);

            // TODO: 12/27/2017  //attr of http connection

            // TODO: 12/27/2017 response code
            writeOnHttpConnection(httpURLConnection);
            /*--*   write postsList  *--*/

            String data = readHttpConnection(httpURLConnection);
            /*--*   read response  *--*/


            onDataReceive(data, Webi.ONLINE);
            /*--*   when data receive  *--*/



        } catch (Exception e) {
            Log.e(TAG, "openHttpURLConnection: " + e);
        } finally {
            if (httpURLConnection != null) {
                /*--*   disconnect http connection  *--*/
                httpURLConnection.disconnect();
            }
        }
        return httpURLConnection;
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

        } catch (Exception e) {
            Log.e(TAG, "writeOnHttpConnection: " + e);
        }

    }


    //7
    private String readHttpConnection(HttpURLConnection httpURLConnection) {
        final StringBuilder stringBuilder = new StringBuilder();

        int code = 0;
        try {
            code = httpURLConnection.getResponseCode();
            if (code == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
            } else {
                Log.e(TAG, "readHttpConnection: httpURLConnection is not 200 or OK");
                webiEvents.onLog(com.ashrafi.webi.enums.Log.ERROR, "httpURLConnection is not 200 or OK it is " + httpURLConnection.getResponseCode());
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "readHttpConnection: it is " + code);
            return "";
        }
    }

    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * when http from connection done
     */


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


            lastResponse = data;
            /*--*   call done method  *--*/
            onDone();


        /*--*   post receive to interface  *--*/
            webiEvents.onResponse(data, where);

        /*--*   json parser  *--*/
            webiEvents.onJsonArray(data, where);
            webiEvents.onJsonObject(data, where);

            // TODO: 12/27/2017  xml
            if ((sqlCache || xmlCache) && (encryptCache)) {

                Encryption encryption = new Encryption();
                String encrypted = encryption.encode(data);

            /*--*   if enctypt Cache is true first cache Then send to cache  *--*/
                cache(encrypted, where);

            } else {
            /*--*   else enctypt is false Unchanged data  *--*/
                cache(data, where);
            }
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
