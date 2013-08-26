/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : EchoHelper.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: 处理微信服务器的echo string，验证URL
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-sdk
 */
public class EchoHelper
{
    private final static Logger LOGGER = LoggerFactory.getLogger(EchoHelper.class);

    /**
     * 
     * 功能描述：验证echo string<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author xiaopeng.li, 2013-8-23
     * @since weixin-sdk 1.0
     *
     * @param request
     * @param response
     * @param token
     * @throws IOException
     */
    public static void doValidate(HttpServletRequest request, HttpServletResponse response, String token) throws IOException
    {
        List<String> params = new ArrayList<String>(3);
        String signature = request.getParameter("signature");
        LOGGER.info("SINGATURE {}", signature);
        if (signature == null)
        {
            LOGGER.error("null signature");
            response.getWriter().println("error");
            return;
        }
        String timestamp = request.getParameter("timestamp");
        LOGGER.info("timestamp {}", timestamp);
        if (timestamp != null)
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
        if (nonce != null)
        {
            params.add(nonce);

        }
        else
        {
            LOGGER.error("null nonce");
            response.getWriter().println("error");
            return;
        }
        params.add(token);

        String echo = request.getParameter("echostr");
        LOGGER.info("echostr {}", echo);
        if (echo == null)
        {
            LOGGER.error("null echostr");
            response.getWriter().println("error");
            return;
        }
        String text = buildParameters(params);
        String shaText = DigestUtils.shaHex(text);
        if (!shaText.equals(signature))
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
}
