weixin-java-SDK
=============================

<p>Welcome to Use Dianping Weixin Java SDK</p>

<p>These Java SDK helps you to build a WeiXin App using Dianping API/p>

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
    <li> mvn clean package
    <li> deploy weixin-java/weixin-web/target/weixin-web-1.0.0.war into your web server
</ul>

<h4>How to developer your plugin:</h4>

```java
public class NearbyDealPlugin
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NearbyDealPlugin.class);

    public static NearbyDealPlugin INSTANCE = new NearbyDealPlugin();
    
    protected static final String[] DEAL_CATEGORIES = new String[]{"美食", "购物", "休闲娱乐", "丽人", "生活服务", "电影", "酒店", "旅游" };
    
    private Map<String, WeixinQuery> weixinQueryMap = new ConcurrentHashMap<String, WeixinQuery>();
    
    protected List<QueryStepProcessor> processors = new ArrayList<QueryStepProcessor>();

    /**
     * 
     */
    public NearbyDealPlugin()
    {
        registryProcessor();
    }

	//重写这个方法，即可自定义请求的处理与参数的包装
    public void registryProcessor()
    {
        //Step 1, show welcome message, and register Query
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                weixinQuery.setCity("上海");
                try
                {
                    PluginHelper.sendTextResponse(response, requestBody,"请选择分类信息: " + getCategoryTip());
                }
                catch (IOException e)
                {
                    //ignore
                    LOGGER.error("Can not send message to:" + requestBody , e);
                }
            }
        });
        //Step 2, add category info
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                TextMessageBody textRequestBody = (TextMessageBody) requestBody;
                String categoryIndex = textRequestBody.getContent();
                int index = NumberUtils.toInt(categoryIndex);
                weixinQuery.setCategory(index < DEAL_CATEGORIES.length ? DEAL_CATEGORIES[index] : DEAL_CATEGORIES[0]);
                try
                {
                    PluginHelper.sendTextResponse(response, requestBody,"请输入关键字或者发送您的位置：\n\n点击+号，选择位置，发送即可");
                }
                catch (IOException e)
                {
                    //ignore
                    LOGGER.error("Can not send message to:" + requestBody , e);
                }
            }
        });
        // Step 3, call API and response
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                LOGGER.info("Final step: {} - {}" , weixinQuery, requestBody);
                String msgType = requestBody.getMsgType();
                if(MessageBody.MSG_TYPE_LOCATION.equals(msgType))
                {
                    LocationMessageBody locationRequestBody = (LocationMessageBody) requestBody;
                    float locationX = locationRequestBody.getLocationX();
                    float locationY = locationRequestBody.getLocationY();
                    weixinQuery.setLatitude(locationX);
                    weixinQuery.setLongitude(locationY);
                }
                else if(MessageBody.MSG_TYPE_TEXT.equals(msgType))
                {
                    TextMessageBody textRequestBody = (TextMessageBody) requestBody;
                    String keyword = textRequestBody.getContent();
                    weixinQuery.setKeyword(keyword);
                }
                else
                {
                    //error message!
                    LOGGER.error("error message type!");
                }
                PluginHelper.invokeApiAndResponse(weixinQuery, requestBody, response, "XXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }

        });
    }
    

    /**
     * 功能描述：
     * <p>
     * 前置条件：
     * <p>
     * 方法影响：
     * <p>
     * Author xiaopeng.li, 2013-8-23
     * 
     * @since weixin-sdk 1.0
     * @param requestBody
     * @param response
     * @throws IOException
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        MessageBody requestBody = MessageBodyFactory.createBody(request);
        LOGGER.info("Process request: {}", requestBody);
        
        String weixinUserId = requestBody.getFromUserId();
        WeixinQuery weixinQuery = this.weixinQueryMap.get(weixinUserId);
        //请求没有累积或者已经完成
        if(weixinQuery == null || weixinQuery.getStep().get() == this.processors.size())
        {
            weixinQuery = PluginHelper.buildQuery(weixinUserId);
            weixinQueryMap.put(weixinUserId, weixinQuery);
        }
        int step = weixinQuery.getStep().get();
        
        if(step < this.processors.size())
        {
            weixinQuery.incrStep();
            LOGGER.info("Step {} - query {}", step, weixinQuery);
            this.processors.get(step).process(weixinQuery, requestBody, response);
        }
    }


    /**
     * 功能描述：<p>
     *
     * 前置条件：<p>
     *
     * 方法影响： <p>
     *
     * Author xiaopeng.li, 2013-8-23
     * @since weixin-sdk 1.0
     *
     * @return
     */
    protected String getCategoryTip()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int index =0 ;;)
        {
            stringBuilder.append(index).append(" ").append(DEAL_CATEGORIES[index]);
            index ++;
            if(index < DEAL_CATEGORIES.length)
            {
                stringBuilder.append(',');
            }
            else
            {
                break;
            }
        }
        return stringBuilder.toString();
    }

}
```

Another sample of Plugin:
```java
  //预置好请求的其他参数，然后一步输入“keyword”或者“地理位置”返回结果
  public void registryProcessor()
  {
        // Step 1, call API and response
        this.processors.add(new QueryStepProcessor()
        {
            @Override
            public void process(WeixinQuery weixinQuery, MessageBody requestBody, HttpServletResponse response)
            {
                LOGGER.info("Final step: {} - {}" , weixinQuery, requestBody);
                String msgType = requestBody.getMsgType();
                if(MessageBody.MSG_TYPE_LOCATION.equals(msgType))
                {
                    LocationMessageBody locationRequestBody = (LocationMessageBody) requestBody;
                    float locationX = locationRequestBody.getLocationX();
                    float locationY = locationRequestBody.getLocationY();
                    weixinQuery.setLatitude(locationX);
                    weixinQuery.setLongitude(locationY);
                }
                else if(MessageBody.MSG_TYPE_TEXT.equals(msgType))
                {
                    TextMessageBody textRequestBody = (TextMessageBody) requestBody;
                    String keyword = textRequestBody.getContent();
                    weixinQuery.setKeyword(keyword);
                }
                else
                {
                    //error message!
                    LOGGER.error("error message type!");
                }
                PluginHelper.invokeApiAndResponse(weixinQuery, requestBody, response,
                                                  PropertiesHelper.getProperty(PropertiesHelper.CONFIG_APPKEY),
                                                  PropertiesHelper.getProperty(PropertiesHelper.CONFIG_APPSECRET));
            }

        });
    }
    
```


---------------------------------------------------------------
<p><b>If you have any suggestion, please send mails to us.</b></p>

<p>Dianping Developer Portal Site: http://developer.dianping.com</p>

<p>Contact us:api@dianping.com</h>

