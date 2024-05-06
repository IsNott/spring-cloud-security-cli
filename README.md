# spring-cloud-security-cli
简单的Springboot3.x+cloud脚手架
## 介绍
个人自用的脚手架，用于快速搭建基于SpringCloud Alibaba的分布式Spring boot项目。
## 依赖

| name                 | version       |
| -------------------- | ------------- |
| Java                 | 17            |
| Spring-Boot          | 3.0.7         |
| Spring-Security-Web  | 6.0.3         |
| jjwt                 | 0.9.1         |
| Mybatis-plus         | 3.5.0         |
| Mybatis-Plus-Boot3   | 3.5.5         |
| Spring-Cloud-Alibaba | 2022.0.0.0    |

## 分支

```
master # 源分支
├─ feature/freemarker-1.0.0 # 生成器功能开发分支
├─ feature/standalone1.0.0  # 单体功能分支
├─ feature/microservice1.0.0 # 微服务功能分支

```

## 模块
```
spring-cloud-security-cli
├─nacos-example
|       └config.yml # nacos参考配置文件
├─ cloud-cli-service # 服务层模块
├─ cloud-cli-security # 安全认证模块
├─ cloud-cli-generate # 代码生成器模块（开发中）
├─ cloud-cli-common # 通用模块（工具、配置）
├─ cloud-cli-bean # 实体类模块
├─ cloud-cli-api # Web api服务
├─ (待定) cloud-cli-pay # 支付SDK分支

```
## 开发日志

| date | module | todo | done | doing | fix |
| --- | --- | --- | --- | --- | -- |
| 2024/5/3| generate | 测试各模块生成接口 | 生成项目各模块逻辑 | 替换模板内容| / |
| 2024/5/3| generate | RestAPI生成单体项目功能 | 生成微服务项目功能 | / | / |
|2024/5/6|generate| 替换模板date注释内容 | RestAPI生成单体项目功能  | / | / |

## REST API生成项目
### 运行cloud-cli-generate模块的GeneratorApplication

### 发送http请求
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

## 说明

基于 Apache 2.0 开源协议，可随意拉取或fork项目。
假如你的项目有更多的日常复用的脚手架功能，欢迎提供PR加入到项目中。
