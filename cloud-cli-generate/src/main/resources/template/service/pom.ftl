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
            <groupId>org.nott</groupId>
            <artifactId>cloud-cli-bean</artifactId>
            <version><#noparse>${cloud.cli-version}</#noparse></version>
        </dependency>

        <dependency>
            <groupId>org.nott</groupId>
            <artifactId>cloud-cli-common</artifactId>
            <version><#noparse>${cloud.cli-version}</#noparse></version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
        </dependency>


    </dependencies>
</project>