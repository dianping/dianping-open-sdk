/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-8-23
 * Project            : weixin-web
 * File Name        : RequestBody.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.weixin.message;

import java.util.Date;

/**
 * 功能描述:
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-8-23
 * @since weixin-sdk
 */
public class MessageBody
{
    public static final String MSG_TYPE_TEXT = "text";

    public static final String MSG_TYPE_IMAGE = "image";

    public static final String MSG_TYPE_LOCATION = "location";

    public static final String MSG_TYPE_LINK = "link";

    private String fromUserId;

    private String toUserId;

    private Date createTime;

    private String msgType;

    private String msgId;

    /**
     * @param msgType
     */
    public MessageBody(String msgType)
    {
        this.msgType = msgType;
    }

    /**
     * getter method
     * 
     * @see MessageBody#createTime
     * @return the createTime
     */
    public Date getCreateTime()
    {
        return createTime;
    }

    /**
     * getter method
     * 
     * @see MessageBody#fromUserId
     * @return the fromUserId
     */
    public String getFromUserId()
    {
        return fromUserId;
    }

    /**
     * getter method
     * 
     * @see MessageBody#msgId
     * @return the msgId
     */
    public String getMsgId()
    {
        return msgId;
    }

    /**
     * getter method
     * 
     * @see MessageBody#msgType
     * @return the msgType
     */
    public String getMsgType()
    {
        return msgType;
    }

    /**
     * getter method
     * 
     * @see MessageBody#toUserId
     * @return the toUserId
     */
    public String getToUserId()
    {
        return toUserId;
    }

    /**
     * setter method
     * 
     * @see MessageBody#createTime
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    /**
     * setter method
     * 
     * @see MessageBody#fromUserId
     * @param fromUserId
     *            the fromUserId to set
     */
    public void setFromUserId(String fromUserId)
    {
        this.fromUserId = fromUserId;
    }

    /**
     * setter method
     * 
     * @see MessageBody#msgId
     * @param msgId
     *            the msgId to set
     */
    public void setMsgId(String msgId)
    {
        this.msgId = msgId;
    }

    /**
     * setter method
     * 
     * @see MessageBody#msgType
     * @param msgType
     *            the msgType to set
     */
    public void setMsgType(String msgType)
    {
        this.msgType = msgType;
    }

    /**
     * setter method
     * 
     * @see MessageBody#toUserId
     * @param toUserId
     *            the toUserId to set
     */
    public void setToUserId(String toUserId)
    {
        this.toUserId = toUserId;
    }

    @Override
    public String toString()
    {
        return "RequestBody [fromUserId=" + fromUserId + ", toUserId=" + toUserId + ", createTime=" + createTime
               + ", msgType=" + msgType + ", msgId=" + msgId + "]";
    }

}
