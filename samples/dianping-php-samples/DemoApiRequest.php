<html>
<head>
<title>Dianping API PHP Samples</title>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
</head>
<body>
<?php

//需要PHP 5 以上以及安装curl扩展

//AppKey 信息，请替换
define('APPKEY','XXXXXXXX');

//AppSecret 信息，请替换
define('SECRET','XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');


//API 请求地址
define('URL', 'http://api.dianping.com/v1/business/find_businesses');

//示例请求参数
$params = array('format'=>'json','city'=>'上海','latitude'=>'31.2204200000392','longitude'=>'121.41163000018','radius'=>'1000','offset_type'=>'1','sort'=>'7','has_deal'=>'1','has_coupon'=>'1','category'=>'美食','region'=>'长宁区','limit'=>'20','keyword'=>'泰国菜');

//按照参数名排序
ksort($params);
//print($params);

//连接待加密的字符串
$codes = APPKEY;

//请求的URL参数
$queryString = '';

while (list($key, $val) = each($params))
{
  $codes .=($key.$val);
  $queryString .=('&'.$key.'='.$val);
}

$codes .=SECRET;
//print($codes);

$sign = strtoupper(sha1($codes));

$url= URL . '?appkey='.APPKEY.'&sign='.$sign.$queryString;

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

print('Your request based on: ');
print('<br/>');
print_r($params);
print('<br/>');
print('Result:');
print('<hr/>');

var_dump($data);
 
?>

</body>
</html>
