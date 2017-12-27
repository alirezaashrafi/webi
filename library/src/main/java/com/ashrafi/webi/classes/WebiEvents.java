package com.ashrafi.webi.classes;

import android.content.Context;
import android.os.Handler;

import com.ashrafi.webi.enums.Log;
import com.ashrafi.webi.interfaces.GetResponseTime;
import com.ashrafi.webi.interfaces.OnJsonArrayReceive;
import com.ashrafi.webi.interfaces.OnJsonObjectReceive;
import com.ashrafi.webi.interfaces.OnLog;
import com.ashrafi.webi.interfaces.OnResponse;

import org.json.JSONArray;
import org.json.JSONObject;

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

    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }

    protected WebiEvents(Context context) {
        handler = new Handler(context.getMainLooper());
    }

    protected void onResponse(final String res, final String where) {
        if (onResponse != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onResponse.Response(res,where);
                }
            });
        } else {
            android.util.Log.w(TAG, "onResponse: is null");
        }
    }

    protected void onResponseTime(final long time) {
        if (getResponseTime != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getResponseTime.time(time);
                }
            });
        } else {
            android.util.Log.w(TAG, "onResponseTime: is null");
        }
    }

    protected void onLog(final Log type, final String log) {
        if (onLog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onLog.onLog(type.getLog(), log);
                }
            });
        } else {
            android.util.Log.i(TAG, "onLog: is null ");
        }
    }


    protected void onJsonArray(final String jsonArray, final String where) {
        if (onJsonArrayReceive != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray ja = new JSONArray(jsonArray);
                        onJsonArrayReceive.jsonArray(ja,where);
                    } catch (Exception e) {
                        android.util.Log.e(TAG, "run: " + e);
                    }
                }
            });
        } else {
            android.util.Log.i(TAG, "onJsonArray: is null ");
        }
    }

    protected void onJsonObject(final String json, final String where) {
        if (onJsonObjectReceive != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject ja = new JSONObject(json);
                        onJsonObjectReceive.jsonObject(ja,where);
                    } catch (Exception e) {
                        android.util.Log.e(TAG, "run: " + e);
                    }
                }
            });
        } else {
            android.util.Log.i(TAG, "onJsonObject: is null ");
        }
    }

}
