/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : LocationRequestBody.java
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
public class LocationMessageBody extends MessageBody
{

    private float locationX;

    private float locationY;

    private float scale;

    private String label;

    public LocationMessageBody()
    {
        super(MSG_TYPE_LOCATION);
    }

    /**
     * getter method
     * 
     * @see LocationMessageBody#label
     * @return the label
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * getter method
     * 
     * @see LocationMessageBody#locationX
     * @return the locationX
     */
    public float getLocationX()
    {
        return locationX;
    }

    /**
     * getter method
     * 
     * @see LocationMessageBody#locationY
     * @return the locationY
     */
    public float getLocationY()
    {
        return locationY;
    }

    /**
     * getter method
     * 
     * @see LocationMessageBody#scale
     * @return the scale
     */
    public float getScale()
    {
        return scale;
    }

    /**
     * setter method
     * 
     * @see LocationMessageBody#label
     * @param label
     *            the label to set
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * setter method
     * 
     * @see LocationMessageBody#locationX
     * @param locationX
     *            the locationX to set
     */
    public void setLocationX(float locationX)
    {
        this.locationX = locationX;
    }

    /**
     * setter method
     * 
     * @see LocationMessageBody#locationY
     * @param locationY
     *            the locationY to set
     */
    public void setLocationY(float locationY)
    {
        this.locationY = locationY;
    }

    /**
     * setter method
     * 
     * @see LocationMessageBody#scale
     * @param scale
     *            the scale to set
     */
    public void setScale(float scale)
    {
        this.scale = scale;
    }

    @Override
    public String toString()
    {
        return "LocationRequestBody [locationX=" + locationX + ", locationY=" + locationY + ", scale=" + scale
               + ", label=" + label + "]";
    }
}
