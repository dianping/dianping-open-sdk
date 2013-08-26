/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : PicRequestBody.java
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
public class ImageMessageBody extends MessageBody
{
    private String picUrl;

    public ImageMessageBody()
    {
        super(MSG_TYPE_IMAGE);
    }

    /**
     * getter method
     * 
     * @see ImageMessageBody#picUrl
     * @return the picUrl
     */
    public String getPicUrl()
    {
        return picUrl;
    }

    /**
     * setter method
     * 
     * @see ImageMessageBody#picUrl
     * @param picUrl
     *            the picUrl to set
     */
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

}
