package com.ashrafi.webi.DataBuilder;

import android.util.Log;

import com.ashrafi.webi.PostDataModel.Posts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by AlirezaAshrafi on 12/27/2017.
 */

public class PostDataBuilder {
    private final String TAG = this.getClass().getName();

    public String buildPostData(List<Posts>bodies) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < bodies.size(); i++) {
            Posts posts = bodies.get(i);
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            try {
                result.append(URLEncoder.encode(posts.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(posts.getValue(), "UTF-8"));
            }catch (Exception e){
                Log.e(TAG, "buildPostData: " + e );
            }

        }

        Log.i(TAG, "buildPostData: " + result);
        return result.toString();
    }

}
