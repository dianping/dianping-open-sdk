<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/main.css"/>" rel="stylesheet" type="text/css" />
<title>大众点评开放平台—OAuth2示例</title>
</head>
<body>


	<div id="container">

		<ul id="mainlinks">
			<li><a href="<c:url value="http://developer.dianping.com"/>">开放平台主页</a></li>
			<li><a href="<c:url value="/login.jsp"/>" class="selected">登录</a></li>
		</ul>

		<div id="content">
			<h1>点评用户登录成功</h1>
			
			<p>此网站演示了如何使用大众点评OAuth 2.0实现第三方的网站接入,使用OAuth2.0
				你可以在不需要知道用户的登录隐私信息的基础上,获取受保护的用户数据.</p>

            <table>
                <tr>
                    <td>
                        <label>用户名：</label>
                    </td>
                    <td>
                        <label>${sessionScope.user_nickname}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>AccessToken：</label>
                    </td>
                    <td>
                        <label>${sessionScope.access_token}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>点评ID：</label>
                    </td>
                    <td>
                        <label>${sessionScope.user_id}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>注册城市：</label>
                    </td>
                    <td>
                        <label>${sessionScope.current_city}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>头像：</label>
                    </td>
                    <td>
                        <img src="${sessionScope.profile_picture_url}"/>
                    </td>
                </tr>
            </table>

		</div>
	</div>
	</div>
</body>
</html>