# Druid Aggregated Monitor

集中监控分布式服务中的druid连接池

## 功能

本工程基于druid本身的StatViewServlet进行改造，支持在一个web工程中监控多个druid实例。druid原本的web监控>所支持的以下特性在改造后仍然支持：

1. 基于单个账号的访问控制
2. 基于IP黑白名单的访问控制


## 配置

目前配置方式只能写在配置文件中，配置文件路径为resources目录下的`druid-instances.json`，格式对应`io.github.bungder.druid.monitor.config.DruidConfig`。

之后应该需要改为采用配置中心进行配置。

## 运行

本工程基于SpringBoot，在源码目录执行以下命令即可：

```
mvn clean package
java -jar target/target/druid-aggregated-monitor-1.0-SNAPSHOT.jar
```

