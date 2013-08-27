<?php
 
/**
 * 演示如何使用API工具，进行API调用，请自行替换AppKey与Secret
 */
require('api.php');

$params = array('latitude'=>'31.2204200000392','longitude'=>'121.41163000018','radius'=>'5000','sort'=>'1','limit'=>'5');

$api = new ApiTool('http://api.dianping.com/v1/deal/find_deals', '5589931241', '16adbf199c38458f847f4c99d25cab4d',  array('format'=>'json','city'=>'上海'));
$jsonObj = $api->requestApi($params);

print('Your request based on: ');
print('<br/>');
print_r($params);
print('<br/>');
print('Result:');
print('<hr/>');
var_dump($jsonObj);

print('status='.$jsonObj['status'].' ');
print('count='.$jsonObj->{'count'});

$deals = $jsonObj->{'deals'};
print($deals);

?>
