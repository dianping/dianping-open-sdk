/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-1-23
 * Project            : dianping-java-samples
 * File Name        : DemoApiTool.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 功能描述: API 工具 <p>
 * 
 *
 * @author : xiaopeng.li <p>
 *
 * @version 1.0 Aug 26, 2013
 *
 * @since weixin-sdk
 */
public class ApiTool
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiTool.class);

    public static String sign(String appKey, String secret, Map<String, String> paramMap)
    {
        // 对参数名进行字典排序
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        // 拼接有序的参数名-值串
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appKey);
        for (String key : keyArray)
        {
            stringBuilder.append(key).append(paramMap.get(key));
        }

        stringBuilder.append(secret);
        String codes = stringBuilder.toString();

        // SHA-1编码， 这里使用的是Apache
        // codec，即可获得签名(shaHex()会首先将中文转换为UTF8编码然后进行sha1计算，使用其他的工具包请注意UTF8编码转换)
        /*
         * 以下sha1签名代码效果等同 byte[] sha =
         * org.apache.commons.codec.digest.DigestUtils
         * .sha(org.apache.commons.codec
         * .binary.StringUtils.getBytesUtf8(codes)); String sign =
         * org.apache.commons
         * .codec.binary.Hex.encodeHexString(sha).toUpperCase();
         */
        String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();

        return sign;
    }

    public static String getQueryString(String appKey, String secret, Map<String, String> paramMap)
    {
        String sign = sign(appKey, secret, paramMap);

        // 添加签名
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appkey=").append(appKey).append("&sign=").append(sign);
        for (Entry<String, String> entry : paramMap.entrySet())
        {
            stringBuilder.append('&').append(entry.getKey()).append('=').append(entry.getValue());
        }
        String queryString = stringBuilder.toString();
        return queryString;
    }

    public static String requestApi(String apiUrl, String appKey, String secret, Map<String, String> paramMap)
    {
        String queryString = getQueryString(appKey, secret, paramMap);

        StringBuffer response = new StringBuffer();
        HttpClientParams httpConnectionParams = new HttpClientParams();
        httpConnectionParams.setConnectionManagerTimeout(1000);
        HttpClient client = new HttpClient(httpConnectionParams);
        HttpMethod method = new GetMethod(apiUrl);

        try
        {
            if (StringUtils.isNotBlank(queryString))
            {
                // Encode query string with UTF-8
                String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8");
                LOGGER.debug("Encoded Query:" + encodeQuery);
                method.setQueryString(encodeQuery);
            }

            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(
                                                       new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                response.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
        }
        catch (URIException e)
        {
            LOGGER.error("Can not encode query: " + queryString + " with charset UTF-8. ", e);
        }
        catch (IOException e)
        {
            LOGGER.error("Request URL: " + apiUrl + " failed. ", e);
        }
        finally
        {
            method.releaseConnection();
        }
        return response.toString();

    }
    
    public static String requestPostApi(String apiUrl, String appKey, String secret, Map<String, String> paramMap)
    {
        StringBuffer response = new StringBuffer();
        HttpClientParams httpConnectionParams = new HttpClientParams();
        httpConnectionParams.setConnectionManagerTimeout(1000);
        HttpClient client = new HttpClient(httpConnectionParams);
        PostMethod method = new PostMethod(apiUrl);
        
        try
        {
            String sign = sign(appKey, secret, paramMap);
            paramMap.put("sign", sign);
            paramMap.put("appkey", appKey);
            // 设置HTTP Post数据
            for (Map.Entry<String, String> entry : paramMap.entrySet())
            {
                method.addParameter(entry.getKey(), entry.getValue());
            }
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                response.append(line).append(System.getProperty("line.separator"));
            }
            reader.close();
        }
        catch (IOException e)
        {
            LOGGER.error("Request URL: " + apiUrl + " failed. ", e);
        }
        finally
        {
            method.releaseConnection();
        }
        return response.toString();
        
    }

}