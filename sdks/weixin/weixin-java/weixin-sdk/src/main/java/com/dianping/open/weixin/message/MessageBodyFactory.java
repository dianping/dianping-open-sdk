/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : RequestBodyFactory.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin.message;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述:
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-sdk
 */
public class MessageBodyFactory
{
    private final static Logger LOGGER = LoggerFactory.getLogger(MessageBodyFactory.class);

    public static MessageBody createBody(HttpServletRequest request)
    {
        MessageBody body = null;
        try
        {
            InputStream is = request.getInputStream();
            InputStreamReader ir = new InputStreamReader(is, "utf-8");
            SAXReader reader = new SAXReader();
            Document document = reader.read(ir);
            Element root = document.getRootElement();

            String msgType = root.element("MsgType").getStringValue();
            if (MessageBody.MSG_TYPE_TEXT.equals(msgType))
            {
                body = new TextMessageBody();
                ((TextMessageBody) body).setContent(root.element("Content").getStringValue());
            }
            else if (MessageBody.MSG_TYPE_IMAGE.equals(msgType))
            {
                body = new ImageMessageBody();
                ((ImageMessageBody) body).setPicUrl(root.element("PicUrl").getStringValue());
            }
            else if (MessageBody.MSG_TYPE_LINK.equals(msgType))
            {
                body = new LinkMessageBody();
                ((LinkMessageBody) body).setUrl(root.element("PicUrl").getStringValue());
                ((LinkMessageBody) body).setDescription(root.element("Description").getStringValue());
                ((LinkMessageBody) body).setTitle(root.element("Title").getStringValue());
            }
            else if (MessageBody.MSG_TYPE_LOCATION.equals(msgType))
            {
                body = new LocationMessageBody();
                ((LocationMessageBody) body).setLabel(root.element("Label").getStringValue());
                ((LocationMessageBody) body).setLocationX(NumberUtils.toFloat(root.element("Location_X").getStringValue()));
                ((LocationMessageBody) body).setLocationY(NumberUtils.toFloat(root.element("Location_Y").getStringValue()));
                ((LocationMessageBody) body).setScale(NumberUtils.toFloat(root.element("Scale").getStringValue()));
            }

            body.setToUserId(root.element("ToUserName").getStringValue());
            body.setFromUserId(root.element("FromUserName").getStringValue());
            body.setCreateTime(new Date(NumberUtils.toLong(root.element("CreateTime").getStringValue())));
        }
        catch (Exception e)
        {
            LOGGER.error("accept fail", e);
        }
        return body;

    }
}
