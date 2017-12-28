package com.ashrafi.webi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ashrafi.webi.classes.Webi;
import com.ashrafi.webi.interfaces.OnResponse;


public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();


        Webi.with(this).from("https://github.com/alirezaashrafi/webi").onResponse(new OnResponse() {
            @Override
            public void Response(String res, String where) {
                Log.i(TAG, "Response: " + where);

                textView.setText(res);
            }
        }).sqlCache(true).connect();


    }


    private TextView textView;

    public void initViews() {
        textView = (TextView) findViewById(R.id.textView);
    }


}
