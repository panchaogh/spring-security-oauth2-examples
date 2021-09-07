# 工程简介

## 授权码模式：
获取code码:
(GET)http://localhost:8081/uaa/oauth/authorize?client_id=client1&response_type=code&scope=ROLE_A ROLE_B ROLE_C ROLE_D&redirect_uri=http://www.baidu.com
根据code码获取token:
(POST)http://localhost:8081/uaa/oauth/token?client_id=client1&client_secret=secret&grant_type=authorization_code&code=W5eLul&redirect_uri=http://www.baidu.com

## 简化模式

(GET)http://localhost:8081/uaa/oauth/authorize?client_id=client1&response_type=token&scope=all&redirect_uri=http://www.baidu.com

## 密码模式

(POST)http://localhost:8081/uaa/oauth/token?client_id=client1&client_secret=secret&grant_type=password&username=zhangsan&password=123456

## 客户端模式

(POST)http://localhost:8081/uaa/oauth/token?client_id=client1&client_secret=secret&grant_type=client_credentials
