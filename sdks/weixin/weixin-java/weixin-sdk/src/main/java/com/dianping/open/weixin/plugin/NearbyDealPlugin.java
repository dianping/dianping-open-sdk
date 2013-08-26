/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : NearbyDealPlugin.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.open.weixin.message.LocationMessageBody;
import com.dianping.open.weixin.message.MessageBody;
import com.dianping.open.weixin.message.MessageBodyFactory;
import com.dianping.open.weixin.message.TextMessageBody;
import com.dianping.open.weixin.query.QueryStepProcessor;
import com.dianping.open.weixin.query.WeixinQuery;

/**
 * 功能描述: 示例plugin，可以选择嵌入在其他的应用中
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-sdk
 */
public class NearbyDealPlugin
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NearbyDealPlugin.class);

    public static NearbyDealPlugin INSTANCE = new NearbyDealPlugin();
    
    protected static final String[] DEAL_CATEGORIES = new String[]{"美食", "购物", "休闲娱乐", "丽人", "生活服务", "电影", "酒店", "旅游" };
    
    private Map<String, WeixinQuery> weixinQueryMap = new ConcurrentHashMap<String, WeixinQuery>();
    
    protected List<QueryStepProcessor> processors = new ArrayList<QueryStepProcessor>();

    /**
     * 
     */
    public NearbyDealPlugin()
    {
        registryProcessor();
    }

    public void registryProcessor()
    {
        //Step 1, show welcome message, and register Query
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                weixinQuery.setCity("上海");
                try
                {
                    PluginHelper.sendTextResponse(response, requestBody,"请选择分类信息: " + getCategoryTip());
                }
                catch (IOException e)
                {
                    //ignore
                    LOGGER.error("Can not send message to:" + requestBody , e);
                }
            }
        });
        //Step 2, add category info
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                TextMessageBody textRequestBody = (TextMessageBody) requestBody;
                String categoryIndex = textRequestBody.getContent();
                int index = NumberUtils.toInt(categoryIndex);
                weixinQuery.setCategory(index < DEAL_CATEGORIES.length ? DEAL_CATEGORIES[index] : DEAL_CATEGORIES[0]);
                try
                {
                    PluginHelper.sendTextResponse(response, requestBody,"请输入关键字或者发送您的位置：\n\n点击+号，选择位置，发送即可");
                }
                catch (IOException e)
                {
                    //ignore
                    LOGGER.error("Can not send message to:" + requestBody , e);
                }
            }
        });
        // Step 3, call API and response
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                LOGGER.info("Final step: {} - {}" , weixinQuery, requestBody);
                String msgType = requestBody.getMsgType();
                if(MessageBody.MSG_TYPE_LOCATION.equals(msgType))
                {
                    LocationMessageBody locationRequestBody = (LocationMessageBody) requestBody;
                    float locationX = locationRequestBody.getLocationX();
                    float locationY = locationRequestBody.getLocationY();
                    weixinQuery.setLatitude(locationX);
                    weixinQuery.setLongitude(locationY);
                }
                else if(MessageBody.MSG_TYPE_TEXT.equals(msgType))
                {
                    TextMessageBody textRequestBody = (TextMessageBody) requestBody;
                    String keyword = textRequestBody.getContent();
                    weixinQuery.setKeyword(keyword);
                }
                else
                {
                    //error message!
                    LOGGER.error("error message type!");
                }
                PluginHelper.invokeApiAndResponse(weixinQuery, requestBody, response, "XXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }

        });
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
     * @param requestBody
     * @param response
     * @throws IOException
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        MessageBody requestBody = MessageBodyFactory.createBody(request);
        LOGGER.info("Process request: {}", requestBody);
        
        String weixinUserId = requestBody.getFromUserId();
        WeixinQuery weixinQuery = this.weixinQueryMap.get(weixinUserId);
        //请求没有累积或者已经完成
        if(weixinQuery == null || weixinQuery.getStep().get() == this.processors.size())
        {
            weixinQuery = PluginHelper.buildQuery(weixinUserId);
            weixinQueryMap.put(weixinUserId, weixinQuery);
        }
        int step = weixinQuery.getStep().get();
        
        if(step < this.processors.size())
        {
            weixinQuery.incrStep();
            LOGGER.info("Step {} - query {}", step, weixinQuery);
            this.processors.get(step).process(weixinQuery, requestBody, response);
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
     * @return
     */
    protected String getCategoryTip()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int index =0 ;;)
        {
            stringBuilder.append(index).append(" ").append(DEAL_CATEGORIES[index]);
            index ++;
            if(index < DEAL_CATEGORIES.length)
            {
                stringBuilder.append(',');
            }
            else
            {
                break;
            }
        }
        return stringBuilder.toString();
    }

}
