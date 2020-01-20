# Sping in Action 示例

## 软件版本
* Spring Boot版本：2.0.6.RELEASE
* Spring Cloud版本： Finchley.SR2
* Java 版本： 1.8
* Kafka版本： kafka_2.12-2.3.1


## 运行准备

* 启动MySQL数据库服务器，创建示例所需数据库并修改项目中相关配置信息
* 各个服务sql脚本查看相应src\main\resources\schema.sql文件，或者到https://github.com/carnellj中查看
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
* 刚整理完毕，未测试
* 可使用postman进行测试
* 先调用验证服务器auth/**oauth/token**接口获取token，然后添加token去调用组织服务或者许可证服务接口