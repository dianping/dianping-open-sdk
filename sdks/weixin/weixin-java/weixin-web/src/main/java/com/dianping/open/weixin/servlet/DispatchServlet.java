/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-3-27
 * Project            : assistant
 * File Name        : DpAssistantServlet.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dianping.open.weixin.dispatch.DispatchCenter;

/**
 * 
 * 功能描述:  <p>
 * 
 *
 * @author : xiaopeng.li <p>
 *
 * @version 1.0 2013-8-23
 *
 * @since weixin-web
 */
public class DispatchServlet extends HttpServlet
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -8286618433847457320L;

    private final static Logger LOGGER = LoggerFactory.getLogger(DispatchServlet.class);

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
        LOGGER.debug("Get request :  {}", request);
        DispatchCenter.acceptAndResponse(request, response);
    }
}
