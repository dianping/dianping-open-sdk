/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-26
 * Project            : weixin-sdk
 * File Name        : DealResponse.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.weixin.message;

import java.io.Serializable;

/**
 * 
 * 功能描述:  <p>
 * 
 *
 * @author : xiaopeng.li <p>
 *
 * @version 1.0 Aug 26, 2013
 *
 * @since weixin-sdk
 */
public class DealResponse implements Serializable
{
    
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -2955440012556604523L;

    private String title;
    
    private String description;
    
    private String picUrl;
    
    private String url;

    /**
     * getter method 
     * @see DealResponse#title
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * setter method
     * @see DealResponse#title
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * getter method 
     * @see DealResponse#description
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * setter method
     * @see DealResponse#description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * getter method 
     * @see DealResponse#picUrl
     * @return the picUrl
     */
    public String getPicUrl()
    {
        return picUrl;
    }

    /**
     * setter method
     * @see DealResponse#picUrl
     * @param picUrl the picUrl to set
     */
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    /**
     * getter method 
     * @see DealResponse#url
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * setter method
     * @see DealResponse#url
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    
    
}
