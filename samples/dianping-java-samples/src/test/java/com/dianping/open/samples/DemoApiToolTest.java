/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-1-23
 * Project            : dianping-java-samples
 * File Name        : DemoApiToolTest.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.samples;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  <p>
 * 
 *
 * @author : xiaopeng.li <p>
 *
 * @version 1.0 2013-1-23
 *
 * @since dianping-java-samples 1.0
 */
public class DemoApiToolTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApiToolTest.class);

    @Test
    public void testRequestApi()
    {
        String apiUrl = "http://api.dianping.com/v1/business/find_businesses";
        String appKey = "XXXXXXXX";
        String secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        
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
        paramMap.put("keyword", "泰国菜");
        paramMap.put("sort", "7");
        paramMap.put("format", "json");
        
        String requestResult = DemoApiTool.requestApi(apiUrl, appKey, secret, paramMap);
        LOGGER.info(requestResult);
        Assert.assertNotNull(requestResult);
    }
    
    
    @Test
    public void testSign()
    {
        String appKey = "12332145";
        String secret = "20f644de23c54e04be38991710d2da13";
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
        paramMap.put("keyword", "泰国菜");
        paramMap.put("sort", "7");
        paramMap.put("format", "json");
        String sign = DemoApiTool.sign(appKey, secret, paramMap);
        LOGGER.info(sign);
        Assert.assertNotNull(sign);
    }
}
