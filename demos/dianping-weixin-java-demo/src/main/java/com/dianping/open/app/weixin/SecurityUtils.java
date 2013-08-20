/*
 * Create Author  : shang.gao
 * Create Date     : 2012-12-7
 * Project            : open-platform-common
 * File Name        : SecurityUtils.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */


package com.dianping.open.app.weixin;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述: SIGN签名工具类，支持对Key/Secret、Request Parameter以及timestamp签名
 * <p>
 * 
 * @author : shang.gao
 *         <p>
 * @version 1.0 2012-12-7
 * @since open-platform-common 1.0
 */
public class SecurityUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);
    
    /**
     * URL中签名的参数名
     */
    public final static String REQ_SIGNATURE = "sign";

    /**
     * URL附加的时间戳参数名
     */
    private final static String REQ_TIMESTAMP = "timestamp";

    /**
     * 日期时间连字符
     */
    private final static char TIME_HYPHEN = '-';
    
    /**
     * timestamp参数个数，包括年，月，日，小时，分钟，秒
     */
    private final static int DATETIME_PARAM_NUM = 6;

    /**
     * 功能描述：Request参数列表处理，内部进行排序，并按keyvalue组合
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author shang.gao, 2012-12-7
     * 
     * @since open-platform-common 2.0
     * @param plainText 明文
     * @param paramMap 参数表
     */
    @SuppressWarnings("unchecked")
    private static void buildParameters(StringBuilder plainText, Map<String, String> paramMap)
    {

        if (paramMap != null)
        {
            Map.Entry<String, String>[] entryArray = paramMap.entrySet().toArray(new Map.Entry[paramMap.size()]);
            Arrays.sort(entryArray, new Comparator<Map.Entry<String, String>>()
            {

                @Override
                public int compare(Entry<String, String> o1, Entry<String, String> o2)
                {
                    return o1.getKey().compareTo(o2.getKey());
                }
                
            });
            for (Map.Entry<String, String> entry : entryArray)
            {
                String value = entry.getValue();
                if (value != null)
                {
                    plainText.append(entry.getKey()).append(value);
                }

            }
        }
    }

    /**
     * 功能描述：截取当前系统时间，year-month-date-hour-minute-second格式
     * <p>
     * 前置条件：当且仅当启用时间戳验证时使用
     * <p>
     * 方法影响：
     * <p>
     * Author shang.gao, 2012-12-7
     * 
     * @since open-platform-common 2.0
     * @return
     */
    private static String fetchTime()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //+1转成月份
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return new StringBuilder().append(year).append(TIME_HYPHEN).append(month).append(TIME_HYPHEN).append(date).append(TIME_HYPHEN)
            .append(hour).append(TIME_HYPHEN).append(minute).append(TIME_HYPHEN).append(second).toString();
    }

    /**
     * 功能描述：组织签名，key+request parameters+secret[+timestamp]
     * <p>
     * 前置条件：客户端调用时必须，其中timestamp是可选项
     * <p>
     * 方法影响： 若启用timestamp，不仅签名中包括，而且返回的sign字符串中带有&timestamp=xxxx-xx-xx-xx的明文
     * <p>
     * Author shang.gao, 2012-12-7
     * 
     * @since open-platform-common 2.0
     * @param key 标识，如用户id，appKey
     * @param secret 私钥，如密码
     * @param paramMap 参数表
     * @param needTime 是否附加timestamp
     * @return
     */
    public static String encryptPath(String key, String secret, Map<String, String> paramMap, boolean needTime)
    {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(secret))
        {
            return null;
        }

        StringBuilder plainText = new StringBuilder(key);
        buildParameters(plainText, paramMap);
        String time = null;
        if (needTime)
        {
            time = fetchTime();
            if (LOGGER.isDebugEnabled())
            {
                LOGGER.debug("::append time string: {}", time);
            }
            plainText.append(time);
        }
        plainText.append(secret);
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("::plain::{}", plainText.toString());
        }

        StringBuilder shaText = new StringBuilder(DigestUtils.shaHex(plainText.toString()));
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("::sha-1 encrypt:: {}", shaText);
        }

        String encrypt = shaText.toString().toUpperCase();
        if (needTime)
        {
            encrypt = new StringBuilder(encrypt).append("&").append(REQ_TIMESTAMP).append("=").append(time).toString();
        }

        return encrypt;
    }

    /**
     * 功能描述：SIGN验证，忽略时间戳验证
     * <p>
     * 前置条件：服务端顶层业务FILTER中调用
     * <p>
     * 方法影响：
     * <p>
     * Author shang.gao, 2012-12-7
     * 
     * @since open-platform-common 2.0
     * @param key 标识，如用户id，appKey
     * @param secret 私钥，如密码
     * @param paramMap 参数表
     * @return
     */
    public static boolean validateEncrypt(String key, String secret, String sign, Map<String, String> paramMap)
    {
        return validateEncrypt(key, secret, sign, paramMap, null, -1);
    }

    /**
     * 功能描述：SIGN验证，支持时间戳验证
     * <p>
     * 前置条件：服务端顶层业务FILTER中调用
     * <p>
     * 方法影响：
     * <p>
     * Author shang.gao, 2012-12-7
     * 
     * @since open-platform-common 2.0
     * @param key 标识，如用户id，appKey
     * @param secret 私钥，如密码
     * @param sign 签名
     * @param paramMap 参数表
     * @param timestamp 时间戳
     * @param maxMinutes 最大允许访问分钟，可以是小数
     * @return
     */
    public static boolean validateEncrypt(String key, String secret, String sign, Map<String, String> paramMap,
                                          String timestamp, float maxMinutes)
    {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(secret) || StringUtils.isBlank(sign))
        {
            return false;
        }
        boolean result = false;
        String shaText = null;

        shaText = sign.toLowerCase();

        result = validateDateAndTime(timestamp, maxMinutes);
        if (result)
        {
            StringBuilder plainText = new StringBuilder(key);
            buildParameters(plainText, paramMap);
            if (timestamp != null)
            {
                plainText.append(timestamp);
            }
            plainText.append(secret);
            LOGGER.debug("::plain::{}", plainText.toString());

            String currentShaText = DigestUtils.shaHex(plainText.toString());
            LOGGER.debug("::sha-1 encrypt::{}", currentShaText);
            result = shaText.equals(currentShaText);
        }
        return result;
    }
 
    /**
     * 
     * 功能描述：获取请求时间戳,忽略毫秒<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2012-12-11
     * @since open-platform-common 2.0
     *
     * @param dateAndTime 日期时间数组{年,月,日,小时,分钟,秒}
     * @return
     */
    private static long getRequestTimemillis(String[] dateAndTime){
        int year = Integer.valueOf(dateAndTime[0]);
        //还原为月序号
        int month = Integer.valueOf(dateAndTime[1])-1;
        int date = Integer.valueOf(dateAndTime[2]);
        int hour = Integer.valueOf(dateAndTime[3]);
        int minute = Integer.valueOf(dateAndTime[4]);
        int second = Integer.valueOf(dateAndTime[5]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        // 忽略毫秒
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    
    /**
     * 
     * 功能描述：获取当前时间戳,忽略毫秒<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author shang.gao, 2012-12-11
     * @since open-platform-common 2.0
     *
     * @return
     */
    private static long getCurrentTimemillis(){
        Calendar currentTime = Calendar.getInstance();
        // 忽略毫秒
        currentTime.set(Calendar.MILLISECOND, 0);
        return currentTime.getTimeInMillis();
    }

    /**
     * 功能描述：时效验证
     * <p>
     * 前置条件：需对API访问进行有效时间控制的，需要使用此验证; 客户端需要在签名中加入timestamp，并且明文传输timestamp
     * <p>
     * 方法影响：
     * <p>
     * Author shang.gao, 2012-12-10
     * 
     * @since open-platform-common 2.0
     * @param timestamp
     *            year-month-date-hour-minute-second格式
     * @param maxMinutes
     *            若>0，则表明需要进行时效验证；否则忽略返回true
     * @return 是否通过
     */
    private static boolean validateDateAndTime(String timestamp, float maxMinutes)
    {
        boolean result = false;
        if (maxMinutes > 0)
        {
            if (!StringUtils.isBlank(timestamp))
            {
                String[] dateAndTime = timestamp.split(String.valueOf(TIME_HYPHEN));
                if (dateAndTime.length == DATETIME_PARAM_NUM)
                {
                    long timemillisToVal = getRequestTimemillis(dateAndTime);
                    long currentTimemillis = getCurrentTimemillis();

                    if (currentTimemillis >= timemillisToVal
                        && (currentTimemillis - timemillisToVal <= maxMinutes * 60000))
                    {
                        result = true;
                    }
                    else
                    {
                        LOGGER.debug("::rejected::timestamp is {}, and exceeds the max minutes{}", timestamp,
                                     maxMinutes);
                    }
                }
                else
                {
                    LOGGER.debug("::rejected::invalid timestamp found {}, but constrants with max minutes {}",
                                 timestamp, maxMinutes);
                }
            }
            else
            {
                LOGGER.debug("::rejected::no timestamp found, but constrants with max minutes {}", maxMinutes);
            }
        }
        else
        {
            result = true;
        }

        return result;
    }
}
