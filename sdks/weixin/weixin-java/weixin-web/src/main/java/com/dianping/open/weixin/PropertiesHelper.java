/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : PropertiesHelper.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述:
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-web
 */
public class PropertiesHelper
{
    private final static Logger LOGGER = LoggerFactory.getLogger(PropertiesHelper.class);

    public final static Properties GLOBAL_CONFIG = new Properties();

    public final static String CONFIG_TOKEN = "token";

    public final static String CONFIG_APPKEY = "appkey";

    public final static String CONFIG_APPSECRET = "appsecret";

    static{
        String filePath = Thread.currentThread().getContextClassLoader().getResource("config.properties").getFile();
        try
        {
            GLOBAL_CONFIG.load(new FileInputStream(filePath));
        }
        catch (Exception e)
        {
            LOGGER.error("cannot load file {}", filePath);
            System.exit(-1);
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
     * @since weixin-web 1.0
     *
     * @param key
     * @return
     */
    public static String getProperty(String key)
    {
        return GLOBAL_CONFIG.getProperty(key);
    }
}
