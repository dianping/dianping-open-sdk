/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : DispatchCenter.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin.dispatch;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.open.weixin.EchoHelper;
import com.dianping.open.weixin.PropertiesHelper;
import com.dianping.open.weixin.plugin.SimpleDealPlugin;

/**
 * 功能描述:
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-web
 */
public class DispatchCenter
{
    private final static Logger LOGGER = LoggerFactory.getLogger(DispatchCenter.class);

    /**
     * 功能描述：
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, 2013-8-23
     * 
     * @since weixin-web 1.0
     * @param request
     * @param response
     * @throws IOException
     */
    public static void acceptAndResponse(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        LOGGER.info("Dispatch request: {}", request);
        // 验证微信ECHO
        if (request.getParameter("echostr") != null)
        {
            EchoHelper.doValidate(request, response, PropertiesHelper.getProperty(PropertiesHelper.CONFIG_TOKEN));
        }
        else
        {
            LOGGER.info("Forward request to nearby plugin: {}", request);
            SimpleDealPlugin.INSTANCE.handleRequest(request, response);
        }
    }

}
