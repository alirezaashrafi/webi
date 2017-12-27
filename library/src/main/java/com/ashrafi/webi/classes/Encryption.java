package com.ashrafi.webi.classes;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

final class Encryption {


    private final String TAG = this.getClass().getName();

    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * encode base64
     */
    protected String encode(String text) {

        try {
            /*--*   data to byte  *--*/
            byte[] data = new byte[0];
            data = text.getBytes("UTF-8");
            text = Base64.encodeToString(data, Base64.DEFAULT);
            return text;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "encode: " + e);
            return "";
        }
    }

    /**
     * (webi library github - by alireza ashrafi - github : alirezaashrafi)
     * <p>
     * decode base64
     */
    protected String decode(String text) {

        try {

            /*--*   data to byte  *--*/
            byte[] data = Base64.decode(text, Base64.DEFAULT);
            text = new String(data, "UTF-8");
            return text;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, "decode: " + e);
            return "";
        }
    }
}
