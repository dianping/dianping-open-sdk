/*
 * Create Author  : shang.gao
 * Create Date     : 2013-3-27
 * Project            : assistant
 * File Name        : DpAssistantServlet.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.app.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: 微信消息收发器
 * <p>
 * 
 * @author : shang.gao
 *         <p>
 * @version 1.0 2013-3-27
 * @since assistant 1.0
 */
public class AssistantServlet extends HttpServlet
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -8286618433847457320L;

    private final static Logger LOGGER = LoggerFactory.getLogger(AssistantServlet.class);

    private Map<String, Condition> conditions = new HashMap<String, Condition>();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        MessageUtils.acceptAndResponse(request, response, this.conditions, null, LOGGER);
    }
}
