 
/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-12-26
 * Project            : DPOAuth2ClientDemo
 * File Name        : MainActivity.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.dpoauth2clientdemo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.dianping.dpoauth2clientdemo.util.Constants;
import com.dianping.dpoauth2clientdemo.util.RequestUtils;

public class MainActivity extends Activity
{

    private static final int CODE_MSG = 1;

    private static final int TOKENE_MSG = 2;

    private static final int API_MSG = 3;

    private WebView webView;

    private Dialog dialog;

    private TextView tokenText;

    private TextView apiText;

    private TextView apiResultText;

    private TextView codeText;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            String content = msg.obj.toString();
            if (msg.what == CODE_MSG)
            {
                codeText.setText(content);
            }
            else if (msg.what == TOKENE_MSG)
            {
                tokenText.setText(content);
                apiText.setText("API: "
                                + Constants.API_URL.concat("?appkey=").concat(Constants.APP_KEY).concat("&access_token=").concat(content));
            }
            else if (msg.what == API_MSG)
            {
                apiResultText.setText(content);
            }
        }
    };

    /**
     * 
     * 功能描述：启动线程访问API，并将返回结果通知给Handler<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author xiaopeng.li, 2013-12-26
     * @since DPOAuth2ClientDemo 2.0
     *
     * @param accessToken
     */
    private void asyncRequestApi(final String accessToken)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String json = RequestUtils.requestUserInfo(accessToken);
                Message message = new Message();
                message.obj = json;
                message.what = API_MSG;
                handler.sendMessage(message);
            }
        }).start();
    }

    /**
     * 
     * 功能描述：启动线程访问获取AccessToken，并将返回结果通知给Handler<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author xiaopeng.li, 2013-12-26
     * @since DPOAuth2ClientDemo 2.0
     *
     * @param url
     */
    private void ayncRequestAccessToken(final String url)
    {
        new Thread()
        {
            @Override
            public void run()
            {
                String code = null;
                String state = null;
                int indexOfParam = url.indexOf('?');
                String params = url.substring(indexOfParam + 1);
                String[] strings = params.split("\\&");
                if (strings != null)
                {
                    for (String string : strings)
                    {
                        if (string.startsWith("code"))
                        {
                            code = string.substring(string.indexOf('=') + 1);
                        }
                        else if (string.startsWith("state"))
                        {
                            state = string.substring(string.indexOf('=') + 1);
                        }
                    }
                }

                String json = RequestUtils.requestAccessToken(code, state);
                JSONObject jsonObject;
                Message message = new Message();
                message.what = TOKENE_MSG;
                try
                {
                    jsonObject = new JSONObject(json);
                    String accessToken = jsonObject.getString("access_token");
                    message.obj = accessToken;
                }
                catch (JSONException e)
                {
                    message.obj = e.getMessage();
                }
                handler.sendMessage(message);

            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tokenText = (TextView) findViewById(R.id.tokenText);
        this.apiResultText = (TextView) findViewById(R.id.apiResultText);
        this.apiText = (TextView) findViewById(R.id.apiLabel);
        this.codeText = (TextView) findViewById(R.id.codeText);

        Button button = (Button) findViewById(R.id.oauthButton);
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openWebDialog();
            }
        });

        Button apiButton = (Button) findViewById(R.id.apiButton);
        apiButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CharSequence text = tokenText.getText();
                final String accessToken = text.toString();
                asyncRequestApi(accessToken);
            }
        });
    }
    
    private void openWebDialog()
    {
        webView = new WebView(MainActivity.this);
        webView.loadUrl(Constants.AUTHORIZE_REQUEST_URL);
        WebViewClient webViewClient = new WebViewClient()
        {
            /**
             * 拦截code响应，构建请求获取access_token并关闭web view
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url)
            {
                view.loadUrl(url);
                if (url.contains("code="))
                {
                    Message message = new Message();
                    message.obj = url.substring(url.indexOf('?') + 1);
                    message.what = CODE_MSG;
                    handler.sendMessage(message);
                    ayncRequestAccessToken(url);
                    dialog.dismiss();
                }
                return true;
            }        
        };
        webView.setWebViewClient(webViewClient);

        webView.getSettings().setJavaScriptEnabled(true);

        dialog = new Dialog(MainActivity.this, R.style.Dialog_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(webView);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);

        //关闭对话框，继续下面的处理
        dialog.show();
    }
}
