# spring-cloud-security-cli

简单的Springboot3.x+Cloud脚手架。

## 介绍

个人自用的脚手架，用于快速搭建基于SpringCloud Alibaba的分布式Spring boot项目。

## 依赖

| name                   | version    |
|------------------------|------------|
| Java                   | 17         |
| Spring-Boot            | 3.0.7      |
| Spring-Security-Web    | 6.0.3      |
| jjwt                   | 0.9.1      |
| Mybatis-plus           | 3.5.0      |
| Mybatis-Plus-Boot3     | 3.5.5      |
| Spring-Cloud-Alibaba   | 2022.0.0.0 |
| Spring-Cloud-Gateway   | 4.0.3      |
| Spring-Cloud-OpenFeign | 4.0.3      |

## 模块
```
spring-cloud-security-cli
├─nacos-example
|       └config.yml # api模块nacos参考配置文件
├─ cloud-cli-service # 服务层模块
├─ cloud-cli-security # 安全认证模块
├─ cloud-cli-generate # 代码生成器模块
├─ cloud-cli-common # 通用模块（工具、配置）
├─ cloud-cli-bean # 实体类模块
├─ cloud-cli-api # Web api服务
├─ cloud-cli-pay# 支付SDK分支（暂无）
├─ cloud-cli-gateway# 微服务网关
```

## 功能
根据API请求生成项目模板：
- 单体项目（普通的Spring boot项目）
- 微服务项目（包含网关，重写了Filter、Predicate，默认以统一前缀:/api/{注册服务名}，转发请求）。

*以上均包含服务层、安全认证、通用、实体类、API模块*

## REST API生成项目
1.运行cloud-cli-generate模块的GeneratorApplication

2.发送http请求
```
path: /generate/project
body:{
    "projectInfo": {
        "groupId": "org.test", // 项目groupId
        "artifactId": "standalone", // 项目artifactId
        "applicationName": "standalone-test", // 应用名称
        "author": "test", // 作者 默认=default
        "childModuleDirPrefix": "standalone-test-cli", // 子模块名称前缀
        "childLastPackage": "cli", // 子模块包名后缀
        "root":"D:\\新建文件夹", // 项目路径
        "mode":"0" // 1-微服务 0-单体 不填默认微服务
    }
}
```

## 分支

```
master # 源分支(最新版本)
├─ feature/freemarker-1.0.0 # 生成器功能开发分支
├─ feature/standalone1.0.0 # 单体服务功能分支
├─ feature/microservice1.0.0 # 微服务功能分支
```
## 开发日志

查看以往Commits记录。

## 说明

基于 Apache 2.0 开源协议，可随意拉取或fork项目。<br>
假如你的项目有更多的日常复用的脚手架功能，欢迎提供PR加入到项目中。<br>
如果此项目对你有用，请帮忙点一个star，谢谢。<br>
