/*
 * Create Author  : shang.gao
 * Create Date     : 2013-3-26
 * Project            : assistant
 * File Name        : Command.java
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
 * @version 1.0 2013-3-26
 *
 * @since assistant 1.0
 */
public class Condition implements Serializable
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 6272340608696035344L;

    private int type;
    
    private int seq;
   
    private String user;
    
    private String selfId;
    
    private String keyword;
    
    private Double longtitude;
    
    private Double latitude;
    
    private Integer category;

    /**
     * getter method 
     * @see Condition#keyword
     * @return the keyword
     */
    public String getKeyword()
    {
        return keyword;
    }

    /**
     * setter method
     * @see Condition#keyword
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    /**
     * getter method 
     * @see Condition#longtitude
     * @return the longtitude
     */
    public Double getLongtitude()
    {
        return longtitude;
    }

    /**
     * setter method
     * @see Condition#longtitude
     * @param longtitude the longtitude to set
     */
    public void setLongtitude(Double longtitude)
    {
        this.longtitude = longtitude;
    }

    /**
     * getter method 
     * @see Condition#latitude
     * @return the latitude
     */
    public Double getLatitude()
    {
        return latitude;
    }

    /**
     * setter method
     * @see Condition#latitude
     * @param latitude the latitude to set
     */
    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    /**
     * getter method 
     * @see Condition#category
     * @return the category
     */
    public Integer getCategory()
    {
        return category;
    }

    /**
     * setter method
     * @see Condition#category
     * @param category the category to set
     */
    public void setCategory(Integer category)
    {
        this.category = category;
    }
    
    public void incr()
    {
        this.seq++;
    }
    
    /**
     * getter method 
     * @see Condition#selfId
     * @return the selfId
     */
    public String getSelfId()
    {
        return selfId;
    }
    /**
     * getter method 
     * @see Condition#seq
     * @return the seq
     */
    public int getSeq()
    {
        return seq;
    }

    /**
     * setter method
     * @see Condition#selfId
     * @param selfId the selfId to set
     */
    public void setSelfId(String selfId)
    {
        this.selfId = selfId;
    }

    /**
     * getter method 
     * @see Condition#user
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * setter method
     * @see Condition#user
     * @param user the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * getter method 
     * @see Condition#type
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * setter method
     * @see Condition#type
     * @param type the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Condition [type=" + type + ", seq=" + seq + ", user=" + user + ", selfId=" + selfId + ", keyword="
               + keyword + ", longtitude=" + longtitude + ", latitude=" + latitude + ", category=" + category + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longtitude == null) ? 0 : longtitude.hashCode());
        result = prime * result + ((selfId == null) ? 0 : selfId.hashCode());
        result = prime * result + seq;
        result = prime * result + type;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Condition other = (Condition) obj;
        if (category == null)
        {
            if (other.category != null)
                return false;
        }
        else if (!category.equals(other.category))
            return false;
        if (keyword == null)
        {
            if (other.keyword != null)
                return false;
        }
        else if (!keyword.equals(other.keyword))
            return false;
        if (latitude == null)
        {
            if (other.latitude != null)
                return false;
        }
        else if (!latitude.equals(other.latitude))
            return false;
        if (longtitude == null)
        {
            if (other.longtitude != null)
                return false;
        }
        else if (!longtitude.equals(other.longtitude))
            return false;
        if (selfId == null)
        {
            if (other.selfId != null)
                return false;
        }
        else if (!selfId.equals(other.selfId))
            return false;
        if (seq != other.seq)
            return false;
        if (type != other.type)
            return false;
        if (user == null)
        {
            if (other.user != null)
                return false;
        }
        else if (!user.equals(other.user))
            return false;
        return true;
    }

    /**
     * setter method
     * @see Condition#seq
     * @param seq the seq to set
     */
    public void setSeq(int seq)
    {
        this.seq = seq;
    }

}
