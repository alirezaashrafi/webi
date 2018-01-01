package com.ashrafi.webi.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class WebServiceOld {

    /*public static final int POST = 1;
    public static final int GET = 2;

    public static final String SQL = "sql";
    public static final String PHP = "php";

    public static final boolean NO = false;
    public static final boolean YES = true;

    public static final int DONE = 0;
    public static final int ERROR = 1;

    private static final String SUCCESS = "success";
    private final String TAG = WebServiceOld.this.getClass().getName();
    private NoSqlCache noSqlCache;

    private OnResponse onResponse;
    private Handler handler;
    private Context context;
    private int setMethod = 1;
    private boolean cache = false;
    private HashMap<String, String> hashMap = new HashMap<String, String>();
    private String link = "";


    public WebServiceOld(Context context) {
        this.context = context;
        noSqlCache = new NoSqlCache(context);
        handler = new Handler(context.getMainLooper());
    }

    public WebServiceOld setMethod(int setMethod) {
        this.setMethod = setMethod;

        return this;
    }

    public WebServiceOld cache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public WebServiceOld from(String link) {

        this.link = link;

        return this;
    }

    public WebServiceOld onResponse(OnResponse onResponse) {
        this.onResponse = onResponse;

        return this;
    }


    public WebServiceOld addPostList(String key, String value) {
        if (value == null) {
            value = "0";
        }
        hashMap.put(key, value);
        return this;
    }

    public WebServiceOld addPostList(String key, int value) {

        hashMap.put(key, String.valueOf(value));
        return this;
    }

    public WebServiceOld addAllPost(HashMap<String, String> hashmap) {
        this.hashMap.putAll(hashmap);
        return this;
    }

    private String hashingLink = "";

    public WebServiceOld request() {

        new HashKey((link + hashMap.toString()), new HashKey.OnHash() {
            @Override
            public void hash(String code) {
                hashingLink = code;


                loadFromSqlite();

                doInBa();

            }
        });


        return this;
    }



    private void sendData(final String data, final String where) {
        if (where.equals(PHP) && cache) {

            //storage of no sql data base
            noSqlCache.put(hashingLink, data);

        }

        if (onResponse != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //send loaded data to interface
                    onResponse.onLoaded(data, where);

                    //decode to json array
                    JsonArrayDecoder(data, where);

                }
            });


        }

    }


    private void JsonArrayDecoder(final String data, final String where) {
        try {


            //decode to json array
            onResponse.onJsonArrayLoaded(new JSONArray(data), where, SUCCESS, false);

        } catch (final JSONException e) {
            Logs.e(TAG, "JsonArrayDecoder: " + e);
            e.printStackTrace();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private void doInBa() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBa(Void... voids) {

                HttpURLConnection openHttpURLConnection = null;
                try {

                    URL from = new URL(link);

                    openHttpURLConnection = (HttpURLConnection) from.openConnection();

                    openHttpURLConnection.setReadTimeout(15000);
                    openHttpURLConnection.setConnectTimeout(15000);
                    openHttpURLConnection.setRequestMethod("POST");
                    openHttpURLConnection.setDoInput(true);
                    openHttpURLConnection.setDoOutput(true);

                        OutputStream outputStream = openHttpURLConnection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        writer.write(buildPostData(hashMap));

                        writer.flush();
                        writer.close();
                        outputStream.close();




                    final StringBuilder stringBuilder = new StringBuilder();
                    final int responseCode = openHttpURLConnection.getResponseCode();


                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openHttpURLConnection.getInputStream()));

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }

                        bufferedReader.close();

                    }

                    sendData(stringBuilder.toString(), PHP);

                    openHttpURLConnection.disconnect();

                } catch (Exception e) {

                    assert openHttpURLConnection != null;
                    openHttpURLConnection.disconnect();
                    //loadFromSqlite();
                    Logs.e(TAG, "doInBa: " + e);
                }


                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

    @SuppressLint("StaticFieldLeak")
    private void loadFromSqlite() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBa(Void... voids) {
                if (cache) {
                    noSqlCache.get(hashingLink, new NoSqlCache.DataRecived() {
                        @Override
                        public void data(final String data) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sendData(data, SQL);
                                }
                            });
                        }
                    });
                }
                return null;
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }


    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }




    public interface OnResponse {
        void onLoaded(String response, String where);

        void onError(String error);

        void onJsonArrayLoaded(JSONArray jsonArray, String where, String errorLog, boolean isError);
    }*/
}