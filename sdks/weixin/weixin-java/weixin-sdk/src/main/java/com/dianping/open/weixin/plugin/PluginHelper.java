/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : PluginHelper.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.weixin.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.open.weixin.ApiTool;
import com.dianping.open.weixin.message.DealResponse;
import com.dianping.open.weixin.message.MessageBody;
import com.dianping.open.weixin.query.WeixinQuery;

/**
 * 功能描述:  默认的团购信息输出显示，可以自定义修改<p>
 * 
 *
 * @author : xiaopeng.li <p>
 *
 * @version 1.0 2013-8-23
 *
 * @since weixin-sdk
 */
public class PluginHelper
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginHelper.class);

    public static void invokeApiAndResponse(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response, String appKey, String secret)
    {
        Map<String,String> map = new HashMap<String, String>();
        map.put("city", weixinQuery.getCity());
        map.put("limit", Integer.toString(weixinQuery.getLimit()));
        map.put("category", weixinQuery.getCategory());
        if(weixinQuery.getLatitude() != null && weixinQuery.getLongitude() != null)
        {
            map.put("latitude", weixinQuery.getLatitude().toString());
            map.put("longitude", weixinQuery.getLongitude().toString());
            map.put("radius", Integer.toString(weixinQuery.getRaduis()));
        }
        if(weixinQuery.getKeyword() != null)
        {
            map.put("keyword", weixinQuery.getKeyword());
        }
        
        String jsonResult = ApiTool.requestApi("http://api.dianping.com/v1/deal/find_deals",appKey, secret,  map);
        
        LOGGER.info(jsonResult);
        try
        {
            parseAndOutput(response, requestBody, jsonResult);
        }
        catch (IOException e)
        {
            LOGGER.error("Can not send response:" + requestBody, e);
        }
    }

    /**
     * 功能描述：<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author xiaopeng.li, 2013-8-23
     * @since weixin-sdk 1.0
     *
     * @param httpServletResponse
     * @param body 
     * @param content 
     * @throws IOException 
     */
    public static void sendTextResponse(HttpServletResponse httpServletResponse, MessageBody body, String content) throws IOException
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        root.addElement("ToUserName").setText(body.getFromUserId());
        root.addElement("FromUserName").setText(body.getToUserId());
        root.addElement("CreateTime").setText(Long.toString(System.currentTimeMillis() / 100));
        root.addElement("MsgType").addCDATA("text");
        root.addElement("Content").addCDATA(content);
        String responseContent = root.asXML();
        LOGGER.info("response {}", responseContent);
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.getWriter().println(responseContent);
    }

    /**
     * 功能描述：<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author xiaopeng.li, 2013-8-23
     * @param weixinUserId 
     * @since weixin-sdk 1.0
     *
     * @return
     */
    public static WeixinQuery buildQuery(String weixinUserId)
    {
        WeixinQuery weixinQuery = new WeixinQuery();
        weixinQuery.setWeixinUserId(weixinUserId);
        return weixinQuery;
    }


    @SuppressWarnings("unchecked")
    public static void parseAndOutput(HttpServletResponse response, MessageBody requestBody, String result)
            throws IOException
    {
        try
        {
            Map<String, Object> dataMap = new ObjectMapper().readValue(result, Map.class);
            String status = (String) dataMap.get("status");
            Integer count = (Integer) dataMap.get("count");
            
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("xml");
            root.addElement("ToUserName").setText(requestBody.getFromUserId());
            root.addElement("FromUserName").setText(requestBody.getToUserId());
            root.addElement("CreateTime").setText(Long.toString(System.currentTimeMillis() / 100));
            
            if ("OK".equals(status) && count > 0)
            {
                root.addElement("MsgType").setText("news");
                root.addElement("ArticleCount").setText(Integer.toString(count + 1));
                Element articles = root.addElement("Articles");
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) dataMap.get("deals");
                
                Element dpElement = articles.addElement("item");
                dpElement.addElement("Title").addCDATA("以下团购由大众点评提供");
                dpElement.addElement("Description").addCDATA("以下团购由大众点评提供");
                dpElement.addElement("PicUrl").addCDATA("http://dp-openapi-weixin.cfapps.io/logo-weixin.png");
                dpElement.addElement("Url").addCDATA("http://m.dianping.com/tuan");
                
                for (Map<String, Object> item : dataList)
                {
                    Element itemElement = articles.addElement("item");
                    DealResponse body = getResponseBody(item);
                    itemElement.addElement("Title").addCDATA(body.getTitle());
                    itemElement.addElement("Description").addCDATA(body.getDescription());
                    itemElement.addElement("PicUrl").addCDATA(body.getPicUrl());
                    itemElement.addElement("Url").addCDATA(body.getUrl());
                }
                root.addElement("FuncFlag").setText("1");
            }
            else
            {
                root.addElement("MsgType").addCDATA("text");
                root.addElement("Content").addCDATA("暂无数据");
            }
            String responseContent = root.asXML();
            LOGGER.info("response {}", responseContent);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(responseContent);
        }
        catch (Exception e)
        {
            LOGGER.error("parse json error", e);
        }
    }

    /**
     * 功能描述：
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, 2013-8-23
     * 
     * @since weixin-sdk 1.0
     * @param item
     * @return
     */
    public static DealResponse getResponseBody(Map<String, Object> item)
    {
        DealResponse body = new DealResponse();
        StringBuilder sb = new StringBuilder();
        sb.append(item.get("title"));
        Integer distance = (Integer) item.get("distance");
        if (distance != null && distance > 0)
        {
            sb.append("\n距离：").append(distance).append("米");
        }
        sb.append("\n价格 :").append(item.get("current_price"));
        body.setDescription((String) item.get("title"));
        body.setTitle(sb.toString());
        body.setPicUrl((String) item.get("s_image_url"));
        body.setUrl((String) item.get("deal_url"));
        return body;
    }
}
