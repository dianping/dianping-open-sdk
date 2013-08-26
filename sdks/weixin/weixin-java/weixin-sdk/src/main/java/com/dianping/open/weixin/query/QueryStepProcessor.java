/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : QueryStepProcessor.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.weixin.query;

import javax.servlet.http.HttpServletResponse;

import com.dianping.open.weixin.message.MessageBody;

/**
 * 功能描述:  <p>
 * 
 *
 * @author : xiaopeng.li <p>
 *
 * @version 1.0 2013-8-23
 *
 * @since weixin-sdk
 */
public interface QueryStepProcessor
{
    void process(WeixinQuery weixinQuery, MessageBody body, HttpServletResponse response);
}
