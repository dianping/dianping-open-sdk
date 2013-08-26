/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : TextRequestBody.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin.message;

/**
 * 功能描述:
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-sdk
 */
public class TextMessageBody extends MessageBody
{
    private String content;

    public TextMessageBody()
    {
        super(MSG_TYPE_TEXT);
    }

    /**
     * getter method
     * 
     * @see TextMessageBody#content
     * @return the content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * setter method
     * 
     * @see TextMessageBody#content
     * @param content
     *            the content to set
     */
    public void setContent(String content)
    {
        this.content = content;
    }

}
