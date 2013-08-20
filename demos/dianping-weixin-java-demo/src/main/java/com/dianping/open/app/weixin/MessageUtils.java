/*
 * Create Author  : shang.gao
 * Create Date     : 2013-3-26
 * Project            : assistant
 * File Name        : Utils.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.app.weixin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述:  消息收发工具<p>
 * 
 *
 * @author : shang.gao <p>
 *
 * @version 1.0 2013-3-26
 *
 * @since assistant 1.0
 */
public final class MessageUtils
{
    private final static Logger GLOGGER = LoggerFactory.getLogger(MessageUtils.class);
    
    public final static int TYPE_BUSINESS = 0;
    
    public final static int TYPE_DEAL = 1;
    
    public final static int TYPE_COUPON = 2;
    
    private final static String[] TYPES = {"周边商户", "周边团购", "周边优惠信息"};
    
    public final static String[][] CATEGORIES = {{"美食", "购物", "休闲娱乐", "生活服务", "酒店", "丽人"},
                                                    {"美食", "购物", "休闲娱乐", "生活服务", "电影", "酒店旅游"},
                                                    {"美食", "购物", "休闲娱乐", "生活服务", "电影", "酒店"}
                                                    };
    
    public final static int SEQ_TYPE = 0;
    
    public final static int SEQ_CATEGORY = 1;
    
    public final static int SEQ_LOCATION = 2;
    
    public final static int SEQ_COMPLETE = 3;

    private final static String[] TYPE_NODE_NAME = {"businesses", "deals", "coupons"};
    
    public final static String[] TIP = {"您好，我是点评小助手，为您提供周边生活服务。请选择服务的种类：", "请选择类别：", "请输入关键字或者发送您的位置：\n\n点击+号，选择位置，发送即可"};
    
    public final static String[] API_URL = {"http://api.dianping.com/v1/business/find_businesses", "http://api.dianping.com/v1/deal/find_deals", "http://api.dianping.com/v1/coupon/find_coupons"};
    
    private final static String RESULT_NONE = "无查询结果，请重新选择：";
    
    private final static String RESULT_ERROR = "查询失败，请重试：";
    
    private final static String COPYRIGHTS = "【数据来自大众点评】";
    
    private final static Properties GLOBAL_CONFIG = new Properties();
    
    private final static String CONFIG_TOKEN = "token";
    
    private final static String CONFIG_WID = "wid";
    
    private final static String CONFIG_APPKEY = "appkey";
    
    private final static String CONFIG_APPSECRET = "appsecret";
    
    private final static String CONFIG_PLATFORM = "platform";
    
    private final static String CONFIG_DEFAULTCITY = "default.city";
    
    private final static String CONFIG_LIMIT = "limit";
    
    private final static String CONFIG_BIZ_SORT = "biz.sort";
    
    private final static String CONFIG_BIZ_RADIUS = "biz.radius";
    
    private final static String CONFIG_DEAL_SORT = "deal.sort";
    
    private final static String CONFIG_COUPON_SORT = "coupon.sort";
    
    static{
        String filePath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getFile();
        try
        {
            GLOBAL_CONFIG.load(new FileInputStream(filePath));
        }
        catch (Exception e)
        {
            GLOGGER.error("cannot load file {}", filePath);
            System.exit(-1);
        }
    }
    
    private static String getCity(Condition condition)
    {
        return GLOBAL_CONFIG.getProperty(CONFIG_DEFAULTCITY);
    }
    
    private static String getTip(String prefix, String[] array)
    {
        StringBuilder sb = new StringBuilder(prefix);
        for(int i=0;i<array.length;i++)
        {
            sb.append(i+1).append('.').append(array[i]);
            if(i < array.length - 1)
            {
                sb.append("，");
            }
        }
        return sb.toString();
    }
        
    public static String buildParameters(List<String> params)
    {
        StringBuilder plainText = new StringBuilder();
        if (params != null)
        {
            String[] sorted = params.toArray(new String[params.size()]);
            Arrays.sort(sorted);
            for (String value : sorted)
            {
                plainText.append(value);

            }
        }
        return plainText.toString();
    }
    
    public static String accessUrl(String url, String queryString, Integer timeout, String charset, String ua) throws Exception
    {

        charset = (charset == null ? "utf-8" : charset);
        HttpClient client = new HttpClient();
        HttpClientParams httpConnectionParams = new HttpClientParams();
        if (timeout != null)
        {
            httpConnectionParams.setConnectionManagerTimeout(timeout);
        }
        httpConnectionParams.setContentCharset(charset);
        httpConnectionParams.setUriCharset(charset);
        httpConnectionParams.setHttpElementCharset(charset);
        client.setParams(httpConnectionParams);
   
        GetMethod method = new GetMethod();
        method.setURI(new URI(url, true, charset));
        method.setRequestHeader("Connection", "keep-alive");
        if (StringUtils.isNotBlank(queryString))
        {
            method.setQueryString(URIUtil.encodeQuery(queryString, charset));
        }
        StringBuffer responseBody = new StringBuffer();
        try
        {
            int status = client.executeMethod(method);
            if(status == 200)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    responseBody.append(line).append(System.getProperty("line.separator"));
                }
                reader.close();
            }
                
        }
        catch (URIException e)
        {
            throw new Exception(e);
        }
        catch (IOException e)
        {
            throw new Exception(e);
        }
        finally
        {
            method.releaseConnection();
        }
        return responseBody.toString();
    }
    
    public static void doValidate(HttpServletRequest request, HttpServletResponse response, Logger LOGGER) throws IOException
    {
        List<String> params = new ArrayList<String>(3);
        String signature = request.getParameter("signature");
        LOGGER.info("SINGATURE {}", signature);
        if(signature == null)
        {
            LOGGER.error("null signature");
            response.getWriter().println("error");
            return;
        }
        String timestamp = request.getParameter("timestamp");
        LOGGER.info("timestamp {}", timestamp);
        if(timestamp != null)
        {
            params.add(timestamp);
        }
        else
        {
            LOGGER.error("null timestamp");
            response.getWriter().println("error");
            return;
        }
        String nonce = request.getParameter("nonce");
        LOGGER.info("nonce {}", nonce);
        if(nonce != null)
        {
            params.add(nonce);
            
        }
        else
        {
            LOGGER.error("null nonce");
            response.getWriter().println("error");
            return;
        }
        params.add(GLOBAL_CONFIG.getProperty(CONFIG_TOKEN));
        
        String echo = request.getParameter("echostr");
        LOGGER.info("echostr {}", echo);
        if(echo == null)
        {
            LOGGER.error("null echostr");
            response.getWriter().println("error");
            return;
        }
        String text = MessageUtils.buildParameters(params);
        String shaText = DigestUtils.shaHex(text);
        if(!shaText.equals(signature))
        {
            LOGGER.error("invalid signature");
            response.getWriter().println("fail");
            return;
        }
        else
        {
            LOGGER.warn("validation successful");
        }
        response.getWriter().println(echo);
    }
    
    private static ResponseBody getResponseBody(int type, Map<String, Object> item)
    {
        ResponseBody body = new ResponseBody();
        Integer distance = (Integer)item.get("distance");
        if(TYPE_BUSINESS == type)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(item.get("name")).append(item.get("branch_name")).append("\n地址：").append(item.get("address"));
            if(distance != null && distance > 0)
            {
                sb.append("\n距离：").append(distance);                
            }
            sb.append("米\n电话：").append(item.get("telephone"))
            .append("\n口味：").append(item.get("product_grade")).append("  环境：").append(item.get("decoration_grade")).append("  服务：").append(item.get("service_grade"))
            .append("\n").append(COPYRIGHTS);
            body.setTitle(sb.toString());
            body.setPicUrl((String)item.get("s_photo_url"));
            body.setUrl((String)item.get("business_url"));
        }
        else if(TYPE_DEAL == type)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(item.get("description"));
            if(distance != null && distance > 0)
            {
                sb.append("\n距离：").append(distance);                
            }
            sb.append("米 \n截止日期：").append(item.get("purchase_deadline"))
                .append("\n已购买：").append(item.get("purchase_count")).append("\n").append(COPYRIGHTS);
            body.setTitle(sb.toString());
            
            body.setPicUrl((String)item.get("s_image_url"));
            body.setUrl((String)item.get("deal_url"));
        }
        else if(TYPE_COUPON == type)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(item.get("title")).append("\n").append(item.get("description"));
            if(distance != null && distance > 0)
            {
                sb.append("\n距离：").append(distance);                
            }
            sb.append("米\n截止日期：").append(item.get("expiration_date"))
                .append("\n已下载：").append(item.get("download_count")).append("\n").append(COPYRIGHTS);
            body.setTitle(sb.toString());
            body.setUrl((String)item.get("coupon_url"));
        }
        
        return body;
    }
    
    /**
     * 
     * 功能描述：发送提示信息<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2013-3-29
     * @since weixin 2.0
     *
     * @param request
     * @param response
     * @param conditionMap
     * @param condition
     * @param content
     * @param LOGGER
     * @throws IOException
     */
    public static void writeTips(HttpServletRequest request, HttpServletResponse response, Map<String, Condition> conditionMap, Condition condition, String content, Logger LOGGER) throws IOException
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        root.addElement("ToUserName").setText(condition.getUser());
        root.addElement("FromUserName").setText(condition.getSelfId());
        root.addElement("CreateTime").setText(Long.toString(System.currentTimeMillis()/100));
        root.addElement("MsgType").setText("text");
        root.addElement("Content").addCDATA(content);
        root.addElement("FuncFlag").setText("0");
        conditionMap.put(condition.getUser(), condition);
        String result = root.asXML();
        LOGGER.info("response {}", result);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(result);
    }
    
    /**
     * 
     * 功能描述：按微信的消息格式输出消息<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2013-3-29
     * @since weixin 2.0
     *
     * @param request
     * @param response
     * @param conditionMap
     * @param condition
     * @param count
     * @param dataMap
     * @param LOGGER
     * @throws IOException
     */
    public static void writeResult(HttpServletRequest request, HttpServletResponse response, Map<String, Condition> conditionMap, Condition condition, int count, Map<String, Object> dataMap, Logger LOGGER) throws IOException
    {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        root.addElement("ToUserName").setText(condition.getUser());
        root.addElement("FromUserName").setText(condition.getSelfId());
        root.addElement("CreateTime").setText(Long.toString(System.currentTimeMillis()/100));
        root.addElement("MsgType").setText("news");
        root.addElement("ArticleCount").setText(Integer.toString(count));
        Element articles = root.addElement("Articles");
        List<Map<String, Object>> dataList = (List<Map<String, Object>>)dataMap.get(TYPE_NODE_NAME[condition.getType()]);
        for(Map<String, Object> item : dataList)
        {
            Element itemElement = articles.addElement("item");
            ResponseBody body = getResponseBody(condition.getType(), item);
            itemElement.addElement("Title").addCDATA(body.getTitle());
            itemElement.addElement("Description").addCDATA(body.getDescription());
            itemElement.addElement("PicUrl").addCDATA(body.getPicUrl());
            itemElement.addElement("Url").addCDATA(body.getUrl());
        }
        root.addElement("FuncFlag").setText("1");
        conditionMap.put(condition.getUser(), condition);
        String result = root.asXML();
        LOGGER.info("response {}", result);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(result);
    }
    
    /**
     * 
     * 功能描述：梳理并返回微信消息，图文方式<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2013-3-29
     * @since weixin 2.0
     *
     * @param request
     * @param response
     * @param conditions
     * @param condition
     * @param result
     * @param LOGGER
     * @throws IOException
     */
    public static void parseAndOutput(HttpServletRequest request, HttpServletResponse response, Map<String, Condition> conditions, Condition condition, String result, Logger LOGGER) throws IOException
    {
        if(result != null)
        {
            try
            {
                Map<String, Object> dataMap = new ObjectMapper().readValue(result, Map.class);
                LOGGER.info("mapper {}", dataMap);
                String status = (String)dataMap.get("status");
                if("OK".equals(status))
                {
                    Integer count = (Integer)dataMap.get("count");
                    if(count != null && count > 0)
                    {
                        
                        writeResult(request, response, conditions, condition, count, dataMap, LOGGER);
                    }
                    else
                    {
                        MessageUtils.writeTips(request, response, conditions, condition, getTip(RESULT_NONE, TYPES), LOGGER);
                    }
                }
                else
                {
                    MessageUtils.writeTips(request, response, conditions, condition, getTip(RESULT_ERROR, TYPES), LOGGER);
                }
            }
            catch (Exception e)
            {
                LOGGER.error("parse json error", e);
                MessageUtils.writeTips(request, response, conditions, condition, getTip(RESULT_ERROR, TYPES), LOGGER);
            }
        }
        else
        {
            MessageUtils.writeTips(request, response, conditions, condition, getTip(RESULT_NONE, TYPES), LOGGER);
        }
    }
    /**
     * 
     * 功能描述：消息收集<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2013-3-29
     * @since weixin 2.0
     *
     * @param request
     * @param response
     * @param selfId
     * @param conditionMap
     * @param LOGGER
     * @return
     * @throws IOException
     */
    public static Object[] collect(HttpServletRequest request, HttpServletResponse response, String selfId, Map<String, Condition> conditionMap, Logger LOGGER) throws IOException
    {
        Condition condition = null;
        String content = null;
        InputStream is = request.getInputStream();
        try
        {
            InputStreamReader ir = new InputStreamReader(is, "utf-8");
            SAXReader reader = new SAXReader();
            Document document = reader.read(ir);
            Element root = document.getRootElement();
            
            for (Iterator<?> iter = root.elementIterator(); iter.hasNext();) {
                Element element = (Element) iter.next();
                String elementName = element.getName();
                String text = element.getText();
                if("ToUserName".equals(elementName))
                {
                    LOGGER.info("userName:{}", text);
                    
                }
                if("FromUserName".equals(elementName))
                {
                    LOGGER.info("FromUserName: {}", text);
                    condition = conditionMap.get(text);
                    if(condition == null || condition.getSeq() == SEQ_COMPLETE)
                    {
                        condition = new Condition();
                        condition.setSelfId(selfId);
                    }
                    condition.setUser(text);
                }
                if("Content".equals(elementName))
                {
                    LOGGER.info("Content: {}", text);
                    content = text;
                }
                if("Location_Y".equals(elementName))
                {
                    LOGGER.info("locationX: {}", text);
                    condition.setLongtitude(Double.valueOf(text));
                }
                if("Location_X".equals(elementName))
                {
                    LOGGER.info("locationY: {}", text);
                    condition.setLatitude(Double.valueOf(text));
                }
            }
            
        }
        catch(Exception e)
        {
            LOGGER.error("accept fail" ,e);
            condition.setSeq(SEQ_TYPE);
            writeTips(request, response, conditionMap, condition, getTip(MessageUtils.TIP[SEQ_TYPE], TYPES), LOGGER);
        }
        return new Object[]{condition ,content};
    }

    /**
     * 
     * 功能描述：组织微信用户的请求完成OpenAPI的访问<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2013-3-29
     * @since weixin 2.0
     *
     * @param condition
     * @param LOGGER
     * @return
     */
    public static String callApi(Condition condition, Logger LOGGER)
    {
        String appKey = GLOBAL_CONFIG.getProperty(CONFIG_APPKEY);
        String appSecret = GLOBAL_CONFIG.getProperty(CONFIG_APPSECRET);
        
        String limit = GLOBAL_CONFIG.getProperty(CONFIG_LIMIT);
        String platform = GLOBAL_CONFIG.getProperty(CONFIG_PLATFORM);
        String category =  CATEGORIES[condition.getType()][condition.getCategory()];
        String longitude = condition.getLongtitude() == null ? null : Double.toString(condition.getLongtitude());
        String latitude = condition.getLatitude()== null ? null : Double.toString(condition.getLatitude());
        String keyword = condition.getKeyword();
        String city = getCity(condition);
        String bizRadius = GLOBAL_CONFIG.getProperty(CONFIG_BIZ_RADIUS);
        String bizSort = GLOBAL_CONFIG.getProperty(CONFIG_BIZ_SORT);
        String dealSort = GLOBAL_CONFIG.getProperty(CONFIG_DEAL_SORT);
        String couponSort = GLOBAL_CONFIG.getProperty(CONFIG_COUPON_SORT);
        
        StringBuilder paramStr = new StringBuilder();
        paramStr.append("appkey=").append(appKey)
        .append("&limit=").append(GLOBAL_CONFIG.getProperty(CONFIG_LIMIT))
        .append("&platform=").append(GLOBAL_CONFIG.getProperty(CONFIG_PLATFORM))
        .append("&category=").append(CATEGORIES[condition.getType()][condition.getCategory()]);
        
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("limit", limit);
        paramMap.put("platform", platform);
        paramMap.put("category", category);
        
        if(latitude != null && longitude != null)
        {
            paramStr.append("&longitude=").append(condition.getLongtitude()).append("&latitude=").append(condition.getLatitude());   
            paramMap.put("longitude", longitude);
            paramMap.put("latitude", latitude);
        }
        if(keyword != null)
        {
            paramStr.append("&keyword=").append(keyword);
            paramMap.put("keyword", keyword);
        }
             
        if(condition.getType() == TYPE_BUSINESS)
        {
            if(latitude != null && longitude != null)
            {
                paramStr.append("&radius=").append(bizRadius).append("&sort=").append(bizSort);  
                paramMap.put("radius", bizRadius);
                paramMap.put("sort", bizSort);
            }
            else
            {
                paramStr.append("&city=").append(city).append("&sort=1");
                paramMap.put("city", city);
                paramMap.put("sort", "1");
            }
        }
        else
        {
            paramStr.append("&city=").append(city);
            paramMap.put("city", city);
            if(condition.getType() == TYPE_DEAL)
            {
                if(latitude == null || longitude == null)
                {
                    dealSort = "1";
                }
                paramStr.append("&sort=").append(dealSort);
                paramMap.put("sort", dealSort);                
            }
            else if(condition.getType() == TYPE_COUPON)
            {
                if(latitude == null || longitude == null)
                {
                    couponSort = "1";
                }
                paramStr.append("&sort=").append(couponSort);
                paramMap.put("sort", couponSort); 
            }
        }
        
        String sign = SecurityUtils.encryptPath(appKey, appSecret, paramMap, false);
        paramStr.append("&sign=").append(sign);
        
        String result = null;
        try
        {
            LOGGER.info("access url {}", paramStr.toString());
            result = accessUrl(API_URL[condition.getType()], paramStr.toString(), null, null, null);
            LOGGER.info("API RESULT {}", result);
        }
        catch (Exception e)
        {
            LOGGER.error("access openapi fail" ,e);
        }
        
        
        return result;
    }
    
    /**
     * 
     * 功能描述：微信信息接收与反馈入口<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2013-3-29
     * @since weixin 2.0
     *
     * @param request
     * @param response
     * @param conditions
     * @param selfId
     * @param LOGGER
     * @throws IOException
     */
    public static void acceptAndResponse(HttpServletRequest request, HttpServletResponse response, Map<String, Condition> conditions, String selfId, Logger LOGGER) throws IOException
    {
        //验证微信ECHO
        if(request.getParameter("echostr") != null)
        {
            MessageUtils.doValidate(request, response, LOGGER);
        }
        //来自用户的消息
        else
        {
            if(selfId == null)
            {
                selfId = GLOBAL_CONFIG.getProperty(CONFIG_WID);
            }
            Object[] info = MessageUtils.collect(request, response, selfId, conditions, LOGGER);
            Condition condition = (Condition)info[0];
            String content = (String)info[1];
            //1. 首先提示
            if(!conditions.containsKey(condition.getUser()))
            {
                MessageUtils.writeTips(request, response, conditions, condition, getTip(MessageUtils.TIP[SEQ_TYPE], TYPES), LOGGER);
            }
            //2. 接着选择服务种类
            else if(condition.getSeq() == SEQ_TYPE)
            {
                int type = NumberUtils.toInt(content);
                if(type > 0 && type <= MessageUtils.TYPES.length)
                {
                    condition.setType(type-1);
                    condition.incr();
                    MessageUtils.writeTips(request, response, conditions, condition, getTip(MessageUtils.TIP[SEQ_CATEGORY], CATEGORIES[condition.getType()]), LOGGER);
                }
                else
                {
                    MessageUtils.writeTips(request, response, conditions, condition, getTip(MessageUtils.TIP[SEQ_TYPE], TYPES), LOGGER);
                }
            }
            //3. 然后选择类别
            else if(condition.getSeq() == SEQ_CATEGORY)
            {
                int category = NumberUtils.toInt(content);
                if(category > 0 && category <= MessageUtils.CATEGORIES[condition.getType()].length)
                {
                    condition.setCategory(category-1);
                    condition.incr();
                    MessageUtils.writeTips(request, response, conditions, condition, MessageUtils.TIP[SEQ_LOCATION], LOGGER);
                }
                else
                {
                    MessageUtils.writeTips(request, response, conditions, condition, getTip(MessageUtils.TIP[SEQ_CATEGORY], CATEGORIES[condition.getType()]), LOGGER);
                }
            }
            //4. 获取位置、关键字，搞定！
            else if(condition.getSeq() == SEQ_LOCATION)
            {
                if(condition.getLatitude() != null && condition.getLongtitude() != null)
                {
                    String result = MessageUtils.callApi(condition, LOGGER);
                    condition.incr();
                    MessageUtils.parseAndOutput(request, response, conditions, condition, result, LOGGER);
                }
                else if(content != null)
                {
                    condition.setKeyword(content);
                    String result = MessageUtils.callApi(condition, LOGGER);
                    condition.incr();
                    MessageUtils.parseAndOutput(request, response, conditions, condition, result, LOGGER);
                }
                else
                {
                    MessageUtils.writeTips(request, response, conditions, condition, MessageUtils.TIP[SEQ_LOCATION], LOGGER);
                }
            }
        }
    }
}
