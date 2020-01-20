# Sping in Action 示例

## 软件版本
* Spring Boot版本：2.0.6.RELEASE
* Spring Cloud版本： Finchley.SR2
* Java 版本： 1.8


## 运行准备

* 启动MySQL数据库服务器，创建示例使用数据库并修改项目中相关配置信息
* 启动Zookeeper和Kafka

## 运行
可按以下顺序启动服务：

1. 启动eureka-server
2. 启动config-server
3. 启动auth-server
4. 启动zipkin-server
5. 启动zuul-server
6. 启动special-route-server
7. 启动organization-service
8. 启动licensing-service

## 其他
刚整理完毕，未测试