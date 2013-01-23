#coding=utf-8

import hashlib
import urllib

#请替换appkey和secret
appkey = "XXXXXXXX"
secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
apiUrl = "http://api.dianping.com/v1/business/find_businesses"

#示例参数
paramSet = []
paramSet.append(("format", "json"))
paramSet.append(("city", "上海"))
paramSet.append(("latitude", "31.21524"))
paramSet.append(("longitude", "121.420033"))
paramSet.append(("category", "美食"))
paramSet.append(("region", "长宁区"))
paramSet.append(("limit", "20"))
paramSet.append(("radius", "2000"))
paramSet.append(("offset_type", "0"))
paramSet.append(("has_coupon", "1"))
paramSet.append(("has_deal", "1"))
paramSet.append(("keyword", "泰国菜"))
paramSet.append(("sort", "7"))

#参数排序与拼接
paramMap = {}
for pair in paramSet:
	paramMap[pair[0]] = pair[1]

codec = appkey
for key in sorted(paramMap.iterkeys()):
	codec += key + paramMap[key]

codec += secret

#签名计算
sign = (hashlib.sha1(codec).hexdigest()).upper()

#拼接访问的URL
url_trail = "appkey=" + appkey + "&sign=" + sign
for pair in paramSet:
	url_trail += "&" + pair[0] + "=" + pair[1]

requestUrl = apiUrl + "?" + url_trail

#模拟请求
response = urllib.urlopen(requestUrl)

print response.read()