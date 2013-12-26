oauth2-javaweb-demo
=============================

<p>欢迎使用大众点评OAuth2 Java Web示例程序<p>

<p>演示内容包括：</p>
<ul>
	<li>构建Authorize请求，跳转到点评OAuth2服务器页面，要求用户登录并授权</li>
	<li>根据用户授权的结果，获取返回的授权码，然后构建Token请求，获取AccessToken</li>
	<li>使用AccessToken，调用相应的API获取用户的基础信息</li>
	<li>使用mvn jetty:start运行，进入页面http://localhost:8080/测试</li>
</ul>

<p>注意事项<p>
<ul>
	<li>工程编码为UTF8，如果导入项目出现乱码，请手动修改项目编码为UTF8</li>
	<li>请自行修改com.dianping.open.oauth.sample.utils.Constants.APP_KEY以及com.dianping.open.oauth.sample.utils.Constants.APP_SECRET为您自己的应用的APP KEY和SECRET</li>
</ul>

<p>任何问题请联系： api@dianping.com</p>


