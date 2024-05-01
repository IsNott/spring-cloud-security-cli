<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
            <groupId>${parent.groupId</groupId>
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
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.nott</groupId>
            <artifactId>cloud-cli-service</artifactId>
            <version>${cloud.cli-version}</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>transmittable-thread-local</artifactId>
        </dependency>

    </dependencies>
</project>