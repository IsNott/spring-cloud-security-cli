<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${parent.groupId}</groupId>
        <artifactId>${parent.artifactId}</artifactId>
        <#if parent.version! != ''>
         <version>${parent.version}</version>
         <#else>
         <version>1.0.0-SNAPSHOT</version>
         </#if>
    </parent>

    <artifactId>${current.artifactId}</artifactId>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>${parent.groupId}</groupId>
            <artifactId>${parent.childModuleDirPrefix}-service</artifactId>
            <version><#noparse>$</#noparse>{${parent.childModuleDirPrefix}-version}</version>
        </dependency>

        <#if parent.mode! == "1">
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <#noparse><version>${cloud-alibaba-version}</version></#noparse>
        </dependency>
        </#if>

        <#if parent.mode! == "1">
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <#noparse><version>${cloud-alibaba-version}</version></#noparse>
        </dependency>
        </#if>

        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap -->

        <dependency>
            <groupId>${parent.groupId}</groupId>
            <artifactId>${parent.childModuleDirPrefix}-security</artifactId>
            <version><#noparse>$</#noparse>{${parent.childModuleDirPrefix}-version}</version>
        </dependency>
    </dependencies>
</project>