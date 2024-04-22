<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>${parent.groupId}</groupId>
        <artifactId>${parent.artifactId}</artifactId>
        <version>${parent.version}</version>
    </parent>

    <artifactId>${current.artifactId}</artifactId>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.nott</groupId>
            <artifactId>cloud-cli-service</artifactId>
            <version><#noparse>$</#noparse>{${parent.childModuleDirPrefix}-version}</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <#noparse><version>${cloud-alibaba-version}</version></#noparse>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config -->

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <#noparse><version>${cloud-alibaba-version}</version></#noparse>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-bootstrap -->

        <dependency>
            <groupId>org.nott</groupId>
            <artifactId>cloud-cli-security</artifactId>
            <#noparse><version>${cloud.cli-version}</version></#noparse>
        </dependency>
    </dependencies>
</project>