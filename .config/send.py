#!/usr/bin/python
# -*- coding: UTF-8 -*-

import requests
import json

dingding_url = "https://oapi.dingtalk.com/robot/send?access_token=6738db2b73dc29f0531afba5de2c63931bcd98362fe81a3908f961b1e31747ee"  

headers = {"Content-Type": "application/json; charset=utf-8"}

# 读取json文件内容,返回字典格式
with open('D:\\jenkins\\AndroidStudy\\.config\\response.json','r',encoding='utf8')as fp:
    json_data = json.load(fp)
    print('这是文件中的json数据：',json_data)
    print('这是读取到文件数据的数据类型：', type(json_data))
    print('code', json_data['code'])
    print('appQRCodeURL', json_data['data']['appQRCodeURL'])
    print('appShortcutUrl', json_data['data']['appShortcutUrl'])
    
post_data = {
    "msgtype": "link", 
    "link": {
        "text": json_data['data']['appName']+"打包成功，版本号"+json_data['data']['appVersion']+",打包时间"+json_data['data']['appCreated'],
        "title": "Android自动打包",
        "picUrl": json_data['data']['appQRCodeURL'],
        "messageUrl": "https://www.pgyer.com/"+json_data['data']['appShortcutUrl']
    },
    "at": {
        "atMobiles": ["18355558613"]
    }
}

r = requests.post(dingding_url, headers=headers, data=json.dumps(post_data))
print(r.content)

