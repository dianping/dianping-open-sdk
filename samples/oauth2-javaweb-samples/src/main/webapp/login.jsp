<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/main.css" rel="stylesheet" type="text/css" />
<title>大众点评开放平台—OAuth2示例</title>
</head>
<body>
	<div id="container">

		<ul id="mainlinks">
            <li><a href="<c:url value="http://developer.dianping.com"/>">开放平台主页</a></li>
		</ul>

		<div id="content">

			<h1>登录</h1>

			<p>此逻辑暂不实现</p>

			<form>
				<table>
					<tr>
						<td><label>用户名:</label></td>
						<td><input type='text' name='j_username' value="admin" /></td>
					</tr>
					<tr>
						<td><label>密码:</label></td>
						<td><input type='password' name='j_password' value="admin" /></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input name="login" value="登录" type="submit" /></td>
					</tr>
				</table>
			</form>
			<p>
				<a href="https://oauth.dianping.com/authorize?client_id=6785312264&response_type=code&state=abcd&redirect_uri=http://localhost:8080/redir&scope=user_info_read">使用大众点评账户登录</a>
			</p>
		</div>
	</div>
</body>
</html>