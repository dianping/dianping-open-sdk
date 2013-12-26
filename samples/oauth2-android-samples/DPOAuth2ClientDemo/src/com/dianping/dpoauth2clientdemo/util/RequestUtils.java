/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-12-26
 * Project            : DPOAuth2ClientDemo
 * File Name        : RequestUtils.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */

package com.dianping.dpoauth2clientdemo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * @author : xiaopeng.li
 *         <p>
 * @version 2.2 2013-12-26
 * @since DPOAuth2ClientDemo 2.2
 */
public class RequestUtils
{
    private static final String UTF8 = "UTF8";

    /**
     * 
     * 请求AccessToken<p>
     *
     * Author xiaopeng.li, 2013-12-26
     * @since DPOAuth2ClientDemo 2.0
     *
     * @param code
     * @param state
     * @return
     */
    public static String requestAccessToken(String code, String state)
    {
        DefaultHttpClient client = new DefaultHttpClient();
        List<BasicNameValuePair> paramPair = new ArrayList<BasicNameValuePair>();
        paramPair.add(new BasicNameValuePair("client_id", Constants.APP_KEY));
        paramPair.add(new BasicNameValuePair("grant_type", "authorization_code"));
        paramPair.add(new BasicNameValuePair("scope", "user_info_read"));
        paramPair.add(new BasicNameValuePair("redirect_uri", Constants.DEFAULT_REDIR_URI));
        paramPair.add(new BasicNameValuePair("client_secret", Constants.APP_SECRET));
        paramPair.add(new BasicNameValuePair("code", code));
        paramPair.add(new BasicNameValuePair("state", state));

        try
        {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPair, UTF8);
            HttpPost post = new HttpPost(Constants.TOKEN_URL);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            int result = response.getStatusLine().getStatusCode();
            StringBuilder stringBuilder = new StringBuilder();
            if (result == 200)
            {
                InputStream in = response.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF8));
                String line = bufferedReader.readLine();
                while (line != null)
                {
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }
    
    /**
     * 
     * 请求用户信息，使用AccessToken<p>
     *
     * Author xiaopeng.li, 2013-12-26
     * @since DPOAuth2ClientDemo 2.0
     *
     * @param accessToken
     * @return
     */
    public static String requestUserInfo(String accessToken)
    {
        DefaultHttpClient client = new DefaultHttpClient();

        try
        {
            HttpGet post = new HttpGet(Constants.API_URL.concat("?appkey=").concat(Constants.APP_KEY).concat("&access_token=").concat(accessToken));
            HttpResponse response = client.execute(post);
            int result = response.getStatusLine().getStatusCode();
            StringBuilder stringBuilder = new StringBuilder();
            if (result == 200)
            {
                InputStream in = response.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, UTF8));
                String line = bufferedReader.readLine();
                while (line != null)
                {
                    stringBuilder.append(line).append("\n");
                    line = bufferedReader.readLine();
                }
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
