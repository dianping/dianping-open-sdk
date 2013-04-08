/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-4-8
 * Project            : Dianping-android-samples
 * File Name        : MainActivity.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.android.samples;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dianping.open.android.samples.utils.DemoApiTool;

/**
 * Android 版本演示请求API
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 2.2 2013-4-8
 * @since Dianping-android-samples 2.2
 */
public class MainActivity extends Activity
{
    static class RequestAPILickListener implements OnClickListener
    {

        private Activity activity;

        public RequestAPILickListener(Activity activity)
        {
            this.activity = activity;
        }

        @Override
        public void onClick(View v)
        {
            TextView text = (TextView) activity.findViewById(R.id.TextView01);

            String apiUrl = "http://api.dianping.com/v1/business/find_businesses";
            String appKey = ((EditText) activity.findViewById(R.id.TextKey)).getText().toString();
            String secret = ((EditText) activity.findViewById(R.id.TextSecret)).getText().toString();

            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("city", "上海");
            paramMap.put("latitude", "31.21524");
            paramMap.put("longitude", "121.420033");
            paramMap.put("category", "美食");
            paramMap.put("region", "长宁区");
            paramMap.put("limit", "20");
            paramMap.put("radius", "2000");
            paramMap.put("offset_type", "0");
            paramMap.put("has_coupon", "1");
            paramMap.put("has_deal", "1");
            paramMap.put("keyword", "̩泰国菜");
            paramMap.put("sort", "7");
            paramMap.put("format", "json");

            String requestResult = DemoApiTool.requestApi(apiUrl, appKey, secret, paramMap);
            text.setText(requestResult);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        setTitle("API Demo Activity");
        Button button = (Button) findViewById(R.id.DialogButton01);
        button.setOnClickListener(new RequestAPILickListener(this));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
