package com.ashrafi.webi;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ashrafi.webi.classes.Webi;
import com.ashrafi.webi.classes.WebiConfig;
import com.ashrafi.webi.enums.Methods;
import com.ashrafi.webi.interfaces.OnLog;
import com.ashrafi.webi.interfaces.OnResponse;


public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final String link = "http://alirezaashrafi.ir";

        WebiConfig.init().setDefaultUrl(link).setDefaultSqlCache(true);



        initViews();
        Webi.with(this).onResponse(new OnResponse() {
            @Override
            public void Response(String res, String where) {
                textView.setText(res);
            }
        }).from("https://www.google.com/").setOnLogListener(new OnLog() {
            @Override
            public void onLog(String type, String log) {
                Log.i(TAG, "setOnLogListener: " + log);
            }
        }).setMethod(Methods.GET).connect();
    }



    private TextView textView;

    public void initViews() {
        textView = (TextView) findViewById(R.id.textView);
    }


}
