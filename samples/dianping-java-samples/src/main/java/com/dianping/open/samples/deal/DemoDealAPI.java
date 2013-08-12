/*
 * Create Author  : xiaopeng.li
 * Create Date     : Aug 12, 2013
 * Project            : dianping-java-samples
 * File Name        : DemoDealAPI.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.samples.deal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.dianping.open.samples.DemoApiTool;

/**
 * 功能描述: 演示团购API的使用
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 Aug 12, 2013
 * @since dianping-java-samples 1.0
 */
public class DemoDealAPI
{
    /**
     * Create one, use global
     */
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    /**
     * 功能描述： 获取所有支持团购的城市
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, Aug 12, 2013
     * 
     * @since dianping-java-samples 1.0
     * @param appKey
     * @param secret
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    public static List<String> requestCities(String appKey, String secret) throws JsonProcessingException,
            IOException
    {
        String apiUrl = "http://api.dianping.com/v1/metadata/get_cities_with_deals";
        String jsonResult = DemoApiTool.requestApi(apiUrl, appKey, secret, new HashMap<String, String>());
        JsonNode tree = JSON_MAPPER.readTree(jsonResult);
        JsonNode citiesNode = tree.get("cities");
        List<String> cities = new ArrayList<String>();
        for (Iterator<JsonNode> iterator = citiesNode.getElements(); iterator.hasNext();)
        {
            JsonNode node = iterator.next();
            cities.add(node.getTextValue());
        }
        return cities;
    }

    /**
     * 功能描述：获取所有的团购的信息
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, Aug 12, 2013
     * 
     * @since dianping-java-samples 1.0
     * @param appKey
     * @param secret
     * @param dealIds
     * @return
     * @throws IOException 
     */
    public static Map<String, List<Map<String, Object>>> requestDealData(String appKey, String secret,
                                                                             Map<String, List<String>> dealIds) throws IOException
    {
        Map<String, List<Map<String, Object>>> dealDatas = new HashMap<String, List<Map<String, Object>>>();
        for (Entry<String, List<String>> entry : dealIds.entrySet())
        {
            String city = entry.getKey();
            List<String> dealIdsWithinCity = entry.getValue();
            List<Map<String, Object>> dealDataList = new ArrayList<Map<String, Object>>();
            List<List<String>> batchIdsList = splitList(dealIdsWithinCity, 40);
            for (List<String> batchIds : batchIdsList)
            {
                dealDataList.addAll(requestBatchDeals(appKey, secret, batchIds));
                sleepAWhile();
            }
            dealDatas.put(city, dealDataList);
        }
        return dealDatas;
    }

    /**
     * 功能描述：获取所有城市的DealID
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, Aug 12, 2013
     * 
     * @since dianping-java-samples 1.0
     * @param appKey
     * @param secret
     * @param dealCities
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     */
    public static Map<String, List<String>> requestDealIds(String appKey, String secret, List<String> dealCities)
            throws JsonProcessingException, IOException
    {
        Map<String, List<String>> dealIds = new HashMap<String, List<String>>();
        String apiUrl = "http://api.dianping.com/v1/deal/get_all_id_list";
        Map<String, String> paramMap = new HashMap<String, String>();
        for (String city : dealCities)
        {
            paramMap.put("city", city);
            // paramMap.put("category", "美食");
            String jsonResult = DemoApiTool.requestApi(apiUrl, appKey, secret, paramMap);
            JsonNode tree = JSON_MAPPER.readTree(jsonResult);
            JsonNode idsNode = tree.get("id_list");
            List<String> ids = new ArrayList<String>();
            for (Iterator<JsonNode> iterator = idsNode.getElements(); iterator.hasNext();)
            {
                JsonNode node = iterator.next();
                ids.add(node.getTextValue());
            }
            dealIds.put(city, ids);
            sleepAWhile();
        }
        return dealIds;
    }

    /**
     * 功能描述：批量请求团购信息，每次最多40条
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, Aug 12, 2013
     * 
     * @since dianping-java-samples 1.0
     * @param appKey
     * @param secret
     * @param batchIdsList
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<Map<String, Object>> requestBatchDeals(String appKey, String secret,
                                                               List<String> batchIdsList) throws JsonParseException, JsonMappingException, IOException
    {
        String batchIdsParam = StringUtils.join(batchIdsList, ',');
        String apiUrl = "http://api.dianping.com/v1/deal/get_batch_deals_by_id";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("deal_ids", batchIdsParam);
        String jsonResult = DemoApiTool.requestApi(apiUrl, appKey, secret, paramMap);
        Map resultMap = JSON_MAPPER.readValue(jsonResult, Map.class);
        List<Map<String, Object>> deals = (List<Map<String, Object>>) resultMap.get("deals");
        return deals;
    }

    /**
     * 功能描述： 扫描获取所有在线团购信息，团购信息用Map保存
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, Aug 12, 2013
     * 
     * @since dianping-java-samples 1.0
     * @param appKey
     * @param secret
     * @throws IOException
     */
    public static Map<String, List<Map<String, Object>>> requestAllData(String appKey, String secret) throws IOException
    {
        List<String> dealCities = requestCities(appKey, secret);
        Map<String, List<String>> dealIds = requestDealIds(appKey, secret, dealCities);
        Map<String, List<Map<String, Object>>> dealDatas = requestDealData(appKey, secret, dealIds);
        return dealDatas;
    }

    private static void sleepAWhile()
    {
        try
        {
            Thread.sleep(10);
        }
        catch (InterruptedException e)
        {
        }
    }

    /**
     * 功能描述：分割List
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, Aug 12, 2013
     * 
     * @since dianping-java-samples 1.0
     * @param list
     *            待分割的list
     * @param pageSize
     *            每段list的大小
     * @return List<<List<T>>
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize)
    {
        int listSize = list.size(); // list的大小

        List<List<T>> listArray = new ArrayList<List<T>>(); // 创建list数组
                                                            // ,用来保存分割后的list
        for (int index = 0; index < listSize; index = index + pageSize)
        {
            List<T> subList = new ArrayList<T>(pageSize);
            if (index + pageSize < listSize)
            {
                subList.addAll(list.subList(index, index + pageSize));
            }
            else
            {
                subList.addAll(list.subList(index, list.size()));
            }
            listArray.add(subList);
        }
        return listArray;
    }

}
