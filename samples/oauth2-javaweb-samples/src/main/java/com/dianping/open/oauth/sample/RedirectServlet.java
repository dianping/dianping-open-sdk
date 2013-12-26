package com.dianping.open.oauth.sample;
/*
 * Create Author  : xiaopengli
 * Create  Time   : 12/26/13 7:00 PM
 * Project        : API
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */

import com.dianping.open.oauth.sample.utils.Constants;
import com.dianping.open.oauth.sample.utils.RequestUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RedirectServlet extends HttpServlet
{

    /**
     * 接受跳转，构建请求获取AccessToken，然后请求用户信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String state = req.getParameter("state");
        String code = req.getParameter("code");

        if(state != null && code != null)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("code=").append(code)
                    .append("&state=").append(state)
                    .append("&client_id=").append(Constants.APP_KEY)
                    .append("&client_secret=").append(Constants.APP_SECRET)
                    .append("&grant_type=authorization_code&redirect_uri=").append(Constants.REDIR_URI);
            String tokenResponse = RequestUtils.sendPost(Constants.TOKEN_URL, stringBuilder.toString());
            if(tokenResponse != null)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                Map map = objectMapper.readValue(tokenResponse, Map.class);
                String accessToken = (String) map.get("access_token");
                String apiResponse = RequestUtils.sendGet(Constants.API_URL, "appkey=".concat(Constants.APP_KEY).concat("&access_token=").concat(accessToken));
                Map<String, Object> apiMap = objectMapper.readValue(apiResponse, Map.class);
                List list = (List) apiMap.get("users");
                Map<String, Object> userMap = (Map<String, Object>) list.get(0);

                HttpSession session = req.getSession(true);
                for(Map.Entry<String, Object> entry: userMap.entrySet())
                {
                    session.setAttribute(entry.getKey().toString(), entry.getValue().toString());
                }
                session.setAttribute("access_token", accessToken);
                resp.setContentType("text/html; charset=UTF-8");
                resp.sendRedirect("/success.jsp");
            }
        }
    }
}
