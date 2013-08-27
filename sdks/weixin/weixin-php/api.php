<?php

/**
 * API Tool For Developers.
 */
class ApiTool
{
    private $appKey;

    private $appSecret;

    private $apiUrl;

    private $defaultParams;

     /**
     * 初始化
     *
     * @param string $apiUrl API URL
     * @param string $appKey 
     * @param string $appSecret 
     */
    public function __construct($apiUrl, $appKey, $appSecret, $defaultParams) {
        $this->apiUrl = $apiUrl;
        $this->appKey = $appKey;
        $this->appSecret = $appSecret;
        $this->defaultParams = $defaultParams;
    }

    /**
     * $givenParams = array('latitude'=>'31.2204200000392','longitude'=>'121.41163000018','radius'=>'1000','sort'=>'1','category'=>'美食','region'=>'长宁区','limit'=>'20','keyword'=>'泰国菜');
     *
     * @param array givenParams
     */
    public function requestApi($givenParams){

        $params = array_merge($this->defaultParams, $givenParams);

        //按照参数名排序
        ksort($params);

        //连接待加密的字符串
        $codes = '';

        //请求的URL参数
        $queryString = '';

        while (list($key, $val) = each($params))
        {
          $codes .=($key.$val);
          $queryString .=('&'.$key.'='.urlencode($val));
        }

        $codes = $this->appKey.$codes.$this->appSecret;

        $sign = strtoupper(sha1($codes));

        $url= $this->apiUrl.'?appkey='.$this->appKey.'&sign='.$sign.$queryString;

        print($url);

        $curl = curl_init();

        // 设置你要访问的URL
        curl_setopt($curl, CURLOPT_URL, $url);

        // 设置cURL 参数，要求结果保存到字符串中还是输出到屏幕上。
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);

        curl_setopt($curl, CURLOPT_ENCODING, 'UTF-8');

        // 运行cURL，请求API
        $data = json_decode(curl_exec($curl), true);

        // 关闭URL请求
        curl_close($curl);

        return $data;
    }

}
 
?>
