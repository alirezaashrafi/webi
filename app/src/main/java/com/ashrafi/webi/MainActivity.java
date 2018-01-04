package com.ashrafi.webi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ashrafi.webi.classes.Webi;
import com.ashrafi.webi.classes.WebiConfig;
import com.ashrafi.webi.enums.Methods;
import com.ashrafi.webi.interfaces.GetResponseTime;
import com.ashrafi.webi.interfaces.OnFailed;
import com.ashrafi.webi.interfaces.OnLog;
import com.ashrafi.webi.interfaces.OnResponse;
import com.ashrafi.webi.interfaces.OnRetry;


public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        String link = "http://alirezaashrafi.ir/webi";

        WebiConfig.init().setDefaultUrl(link);


        Webi.with(this)
                .onResponse(new OnResponse() {
                    @Override
                    public void Response(String res, String where) {
                        textView.setText(res);
                    }
                })
                .setMethod(Methods.GET)
                .addPost("username", "alireza")
                .addGet("password", "12345")
                .setSqlCache(true, "alirezaashrafi.ir")
                .setEncryptCache(true)
                .setRetryTimes(5)
                .setReadTimeOut(5000)
                .setOnRetry(new OnRetry() {
                    @Override
                    public void retry(int remainingRetrys) {
                        Log.w(TAG, "retry: " + remainingRetrys);
                    }
                })
                .getResponseTime(new GetResponseTime() {
                    @Override
                    public void time(long millisecond) {
                        Log.i(TAG, "time: " + millisecond);
                    }
                })
                .setOnLogListener(new OnLog() {
                    @Override
                    public void onLog(String type, String log) {
                        Log.i(TAG, "setOnLogListener: " + log);
                    }
                })
                .setOnFailed(new OnFailed() {
                    @Override
                    public void failed(int code) {
                        Log.e(TAG, "failed: " + String.valueOf(code));
                    }
                })
                .connect();
    }


    private TextView textView;

    public void initViews() {
        textView = (TextView) findViewById(R.id.textView);
    }


}
