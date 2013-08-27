weixin-php-SDK
=============================
<p>欢迎使用点评微信 PHP SDK</p>

<h3>如何获取SDK:</h3>
    git clone git://github.com/dianping/dianping-open-sdk.git

<h4>文档说明</h4>
<ul>
    <li> api.php：API请求工具
    <li> api-demo.php：演示如何使用API请求工具
    <li> Wechat.php：微信公众平台App封装
    <li> server.php：入口文件，负责响应微信服务器的echo和处理用户请求，目前仅支持文本和地理位置两种消息的处理
</ul>

<h4>如何使用</h4>
<ul>
    <li> 前往微信公众平台主页，设置URL和Token，URL指向server.php页面
    <li> 修改server.php中的Token，AppKey和AppSecret，以及可能的默认参数，比如城市
    <li> 部署4个php文件到服务器并测试
</ul>

<h4>如何开发</h4>
<ul> 
    <li>基于server.php提供的基础功能，扩展实现更多的消息处理，比如多个消息组合实现分类、商区和关键字的精确匹配。
</ul>

<p>特别感谢: https://github.com/netputer/wechat-php-sdk</p>

---------------------------------------------------------------
<p><b>任何建议和反馈，欢迎联系我们.</b></p>

<p>开发这平台主页: http://developer.dianping.com</p>

<p>邮件：api@dianping.com</p>

<p>社区：http://s.dianping.com/developers</p>

<p>QQ群：69301261</h>

