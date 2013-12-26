/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-12-26
 * Project            : DPOAuth2ClientDemo
 * File Name        : Constants.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package com.dianping.dpoauth2clientdemo.util;

/**
 *
 * @author : xiaopeng.li <p>
 *
 * @version 2.2 2013-12-26
 *
 * @since DPOAuth2ClientDemo 2.2
 */
public class Constants
{
    public static final String APP_KEY = "93578753";
    
    public static final String APP_SECRET = "9e3628fede584d8da00ccadb5a7f2b06";

    public static final String TOKEN_URL = "https://oauth.dianping.com/token";
    
    public static final String AUTHORIZE_URL = "https://oauth.dianping.com/authorize";
    
    public static final String API_URL = "https://api.dianping.com/v1/user/get_user_info";

    public static final String DEFAULT_REDIR_URI = "https://oauth.dianping.com/index.html";
    
    public static final String AUTHORIZE_REQUEST_URL = AUTHORIZE_URL + "?client_id=" + APP_KEY
            + "&response_type=code&state=xyzs&scope=user_info_read&redirect_uri="
            + DEFAULT_REDIR_URI;
}
