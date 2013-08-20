/*
 * Create Author  : shang.gao
 * Create Date     : 2013-3-29
 * Project            : weixin
 * File Name        : ResponseBody.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.app.weixin;

import java.io.Serializable;

/**
 * 功能描述:  <p>
 * 
 *
 * @author : shang.gao <p>
 *
 * @version 1.0 2013-3-29
 *
 * @since weixin 1.0
 */
public class ResponseBody implements Serializable
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
     * @see ResponseBody#title
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * setter method
     * @see ResponseBody#title
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * getter method 
     * @see ResponseBody#description
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * setter method
     * @see ResponseBody#description
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * getter method 
     * @see ResponseBody#picUrl
     * @return the picUrl
     */
    public String getPicUrl()
    {
        return picUrl;
    }

    /**
     * setter method
     * @see ResponseBody#picUrl
     * @param picUrl the picUrl to set
     */
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    /**
     * getter method 
     * @see ResponseBody#url
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * setter method
     * @see ResponseBody#url
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    
    
}
