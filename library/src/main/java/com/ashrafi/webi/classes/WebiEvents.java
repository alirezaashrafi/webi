package com.ashrafi.webi.classes;

import android.content.Context;
import android.os.Handler;

import com.ashrafi.webi.enums.Logs;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

final class WebiEvents {

    private static final String TAG = "Webi-WebiEvents";
    private Handler handler;
    protected OnResponse onResponse;
    protected GetResponseTime getResponseTime;
    protected OnLog onLog;
    protected OnJsonArrayReceive onJsonArrayReceive;
    protected OnJsonObjectReceive onJsonObjectReceive;
    protected OnRetry onRetry;
    protected OnTimeOut onTimeOut;
    protected OnFailed onFailed;
    protected OnXmlReceive onXmlReceive;


    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }

    protected WebiEvents(Context context) {
        handler = new Handler(context.getMainLooper());
    }

    protected void onResponse(final String res, final String where) {
        onLog(Logs.INFO, "return response from " + where);
        if (onResponse != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onResponse.Response(res, where);
                }
            });
        }
    }

    protected void onResponseTime(final long time) {
        onLog(Logs.INFO, "response time = " + time);
        if (getResponseTime != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getResponseTime.time(time);
                }
            });
        }
    }

    protected void onLog(final Logs type, final String log) {
        if (onLog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onLog.onLog(type.getLog(), log);
                }
            });
        }
    }



    protected void OnXmlReceive(String xml, final String where){
        if (onXmlReceive!=null){
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final InputSource inputSource = new InputSource(new StringReader(xml));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            onXmlReceive.xml(documentBuilder.parse(inputSource),where);
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                onLog(Logs.ERROR,e.getMessage());
            }

        }
    }
    protected void OnFailed(final int code){
        if (onFailed!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onFailed.failed(code);
                }
            });
        }
    }
    protected void OnTimeOut() {
        if (onTimeOut != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    onTimeOut.timeOut();
                }
            });
        }
    }

    protected void onRetry(final int remaining) {

        if (onRetry != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onRetry.retry(remaining);
                }
            });
        }
    }


    protected void onJsonArray(final String jsonArray, final String where) {
        if (onJsonArrayReceive != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray ja = new JSONArray(jsonArray);
                        onJsonArrayReceive.jsonArray(ja, where);
                    } catch (Exception e) {
                        onLog(Logs.ERROR, "failed to parse JsonArray , response from = " + where);
                    }
                }
            });
        }
    }

    protected void onJsonObject(final String json, final String where) {
        if (onJsonObjectReceive != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject ja = new JSONObject(json);
                        onJsonObjectReceive.jsonObject(ja, where);
                    } catch (Exception e) {
                        onLog(Logs.ERROR, "failed to parse jsonObject , response from = " + where);
                    }
                }
            });
        }
    }

}
