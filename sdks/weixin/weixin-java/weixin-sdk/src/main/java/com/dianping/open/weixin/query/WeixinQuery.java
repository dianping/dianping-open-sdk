/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : ApiQuery.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.open.weixin.query;

import java.util.concurrent.atomic.AtomicInteger;

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
public class WeixinQuery
{
    @Override
    public String toString()
    {
        return "WeixinQuery [weixinUserId=" + weixinUserId + ", city=" + city + ", region=" + region + ", category="
               + category + ", latitude=" + latitude + ", longitude=" + longitude + ", raduis=" + raduis + ", keyword="
               + keyword + ", step=" + step + "]";
    }

    private String weixinUserId;
    
    private String city;
    
    private String region;
    
    private String category;
    
    private Float latitude;
    
    private int limit = 5;
    
    /**
     * getter method 
     * @see WeixinQuery#weixinUserId
     * @return the weixinUserId
     */
    public String getWeixinUserId()
    {
        return weixinUserId;
    }

    /**
     * setter method
     * @see WeixinQuery#weixinUserId
     * @param weixinUserId the weixinUserId to set
     */
    public void setWeixinUserId(String weixinUserId)
    {
        this.weixinUserId = weixinUserId;
    }

    /**
     * getter method 
     * @see WeixinQuery#city
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * setter method
     * @see WeixinQuery#city
     * @param city the city to set
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * getter method 
     * @see WeixinQuery#region
     * @return the region
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * setter method
     * @see WeixinQuery#region
     * @param region the region to set
     */
    public void setRegion(String region)
    {
        this.region = region;
    }

    /**
     * getter method 
     * @see WeixinQuery#category
     * @return the category
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * setter method
     * @see WeixinQuery#category
     * @param category the category to set
     */
    public void setCategory(String category)
    {
        this.category = category;
    }

    /**
     * getter method 
     * @see WeixinQuery#latitude
     * @return the latitude
     */
    public Float getLatitude()
    {
        return latitude;
    }

    /**
     * setter method
     * @see WeixinQuery#latitude
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }

    /**
     * getter method 
     * @see WeixinQuery#longitude
     * @return the longitude
     */
    public Float getLongitude()
    {
        return longitude;
    }

    /**
     * setter method
     * @see WeixinQuery#longitude
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    /**
     * getter method 
     * @see WeixinQuery#raduis
     * @return the raduis
     */
    public int getRaduis()
    {
        return raduis;
    }

    /**
     * setter method
     * @see WeixinQuery#raduis
     * @param raduis the raduis to set
     */
    public void setRaduis(int raduis)
    {
        this.raduis = raduis;
    }

    /**
     * getter method 
     * @see WeixinQuery#keyword
     * @return the keyword
     */
    public String getKeyword()
    {
        return keyword;
    }

    /**
     * setter method
     * @see WeixinQuery#keyword
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    /**
     * getter method 
     * @see WeixinQuery#step
     * @return the step
     */
    public AtomicInteger getStep()
    {
        return step;
    }

    /**
     * setter method
     * @see WeixinQuery#step
     * @param step the step to set
     */
    public void setStep(AtomicInteger step)
    {
        this.step = step;
    }

    private Float longitude;
    
    private int raduis = 2000;
    
    private String keyword;
    
    private AtomicInteger step = new AtomicInteger();
    
    public void incrStep()
    {
        this.step.addAndGet(1);
    }

    /**
     * getter method 
     * @see WeixinQuery#limit
     * @return the limit
     */
    public int getLimit()
    {
        return limit;
    }

    /**
     * setter method
     * @see WeixinQuery#limit
     * @param limit the limit to set
     */
    public void setLimit(int limit)
    {
        this.limit = limit;
    }
}
