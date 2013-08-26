/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : UrlRequestBody.java
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
public class LinkMessageBody extends MessageBody
{

    private String title;

    private String description;

    private String url;

    public LinkMessageBody()
    {
        super(MSG_TYPE_LINK);
    }

    /**
     * getter method
     * 
     * @see LinkMessageBody#description
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * getter method
     * 
     * @see LinkMessageBody#title
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * getter method
     * 
     * @see LinkMessageBody#url
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * setter method
     * 
     * @see LinkMessageBody#description
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * setter method
     * 
     * @see LinkMessageBody#title
     * @param title
     *            the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * setter method
     * 
     * @see LinkMessageBody#url
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

}
