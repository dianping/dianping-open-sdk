/*
 * Create Author  : xiaopeng.li
 * Create Date     : Aug 12, 2013
 * Project            : dianping-java-samples
 * File Name        : DemoDealAPITest.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.samples.deal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 功能描述:
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 Aug 12, 2013
 * @since dianping-java-samples 1.0
 */
public class DemoDealAPITest
{
    @Test
    public void test()
    {
        String appKey = "XXXXXXXX";
        String secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        try
        {
            Map<String, List<Map<String, Object>>> deals = DemoDealAPI.requestAllData(appKey, secret);
            System.out.println(deals.size());
            System.out.println(deals.keySet());
            System.out.println(deals.get("上海"));
        }
        catch (IOException e)
        {
        }
    }
}
