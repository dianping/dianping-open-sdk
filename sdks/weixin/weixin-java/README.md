weixin-java-SDK
=============================

<p>Welcome to Use Dianping Weixin Java SDK</p>

<p>These Java SDK helps you to build a WeiXin App using Dianping API</p>

<h3>How to get samples:</h3>
    git clone git://github.com/dianping/dianping-open-sdk.git

<h4>wexin-java</h4>
<ul>
    <li> weixin-sdk: Java SDK for WeiXin developers
    <li> weixin-web: Demo Web using weixin-sdk
</ul>

<h4>Usage:</h4>
<ul>
    <li> cd weixin-java
    <li> change weixin-web/src/main/resources/config.properties, add you AppKey and App Secret
    <li> mvn clean package
    <li> deploy weixin-java/weixin-web/target/weixin-web-1.0.0.war into your web server
</ul>

<h4>How to develop your plugin:</h4>
<ul> 
    <li>Add your own QueryStepProcessor implementations
    <li>Register QueryStepProcessor into Plugin
    <li>Call Plugin to invoker Deal API 
    <li>More details refer to: https://github.com/dianping/dianping-open-sdk/blob/master/sdks/weixin/weixin-java/weixin-sdk/src/main/java/com/dianping/open/weixin/plugin/NearbyDealPlugin.java</li>
</ul>

<br/>
---------------------------------------------------------------
<p><b>If you have any suggestion, please send mails to us.</b></p>

<p>Dianping Developer Portal Site: http://developer.dianping.com</p>

<p>Contact us:api@dianping.com</h>

