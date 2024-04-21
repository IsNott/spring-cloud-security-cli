<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>${version}</version>
    <packaging>pom</packaging>
    <modules>
       <#list moduleInfos as module>
           ${module.artifactId}
       </#list>
    </modules>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.plugin.version>3.8.1</maven.compiler.plugin.version>
        <spring-boot.version>3.0.7</spring-boot.version>
        <java.version>17</java.version>
        <guava.version>32.1.2-jre</guava.version>
        <hutool.version>5.8.25</hutool.version>
        <jsoup.version>1.15.3</jsoup.version>
        <mybatis-plus.version>3.5.0</mybatis-plus.version>
        <mybatis-plus-boot3.version>3.5.5</mybatis-plus-boot3.version>
        <log4j.version>2.20.0</log4j.version>
        <cloud-alibaba-version>2022.0.0.0</cloud-alibaba-version>
        <fast.json-version>2.0.46</fast.json-version>
        <mysql.connector-version>8.0.33</mysql.connector-version>
        <cloud.cli-version>1.0-SNAPSHOT</cloud.cli-version>
        <swagger-version>2.0.0-rc2</swagger-version>
        <redisson.version>3.19.3</redisson.version>
        <okhttp-version>4.11.0</okhttp-version>
        <jsoup.version>1.15.3</jsoup.version>
        <valid-version>3.0.2</valid-version>
        <transmittable-thread-local.version>2.14.2</transmittable-thread-local.version>
        <lettuce-version>6.3.0.RELEASE</lettuce-version>
        <commons-version>3.14.0</commons-version>
        <elastic.search-version>8.13.0</elastic.search-version>
        <aspectj.version>1.9.21</aspectj.version>
        <freemark.version>2.3.31</freemark.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-alibaba-commons -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-commons</artifactId>
            </dependency>

            <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-discovery -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            </dependency>

            <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            </dependency>

            <dependency>
                <groupId>org.jsoup</groupId>
                <artifactId>jsoup</artifactId>
                <version>${jsoup.version}</version>
            </dependency>

            <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-alibaba-sentinel-gateway -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
            </dependency>

            <!-- https://mvnrepository.com/artifact/co.elastic.clients/elasticsearch-java -->
<!--            <dependency>-->
<!--                <groupId>co.elastic.clients</groupId>-->
<!--                <artifactId>elasticsearch-java</artifactId>-->
<!--                <version>${elastic.search-version}</version>-->
<!--            </dependency>-->

<!--            <dependency>-->
<!--                <groupId>org.elasticsearch.client</groupId>-->
<!--                <artifactId>elasticsearch-rest-client</artifactId>-->
<!--                <version>${elastic.search-version}</version>-->
<!--            </dependency>-->

            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
                <version>${mybatis-plus-boot3.version}</version>
            </dependency>

            <dependency>
                <groupId>org.redisson</groupId>
                <artifactId>redisson-spring-boot-starter</artifactId>
                <version>${redisson.version}</version>
            </dependency>

            <dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>${guava.version}</version>
            </dependency>

            <dependency>
                <groupId>cn.hutool</groupId>
                <artifactId>hutool-all</artifactId>
                <version>${hutool.version}</version>
            </dependency>

            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-to-slf4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-core</artifactId>
                <version>${log4j.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.logging.log4j</groupId>
                <artifactId>log4j-api</artifactId>
                <version>${log4j.version}</version>
            </dependency>

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fast.json-version}</version>
            </dependency>

            <dependency>
                <groupId>com.mysql</groupId>
                <artifactId>mysql-connector-j</artifactId>
                <version>${mysql.connector-version}</version>
            </dependency>

            <dependency>
                <groupId>com.squareup.okhttp3</groupId>
                <artifactId>okhttp</artifactId>
                <version>${okhttp-version}</version>
            </dependency>

            <!-- https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api -->
            <dependency>
                <groupId>jakarta.validation</groupId>
                <artifactId>jakarta.validation-api</artifactId>
                <version>${valid-version}</version>
            </dependency>

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>transmittable-thread-local</artifactId>
                <version>${transmittable-thread-local.version}</version>
            </dependency>

            <!-- https://mvnrepository.com/artifact/com.auth0/java-jwt -->
            <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
            <dependency>
                <groupId>io.jsonwebtoken</groupId>
                <artifactId>jjwt</artifactId>
                <version>0.9.1</version>
            </dependency>

            <!-- https://mvnrepository.com/artifact/io.lettuce/lettuce-core -->
            <dependency>
                <groupId>io.lettuce</groupId>
                <artifactId>lettuce-core</artifactId>
                <version>${lettuce-version}</version>
            </dependency>


            <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-lang3</artifactId>
                <version>${commons-version}</version>
            </dependency>

            <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
            <dependency>
                <groupId>org.aspectj</groupId>
                <artifactId>aspectjweaver</artifactId>
                <version>${aspectj.version}</version>
            </dependency>

            <!-- https://mvnrepository.com/artifact/org.freemarker/freemarker -->
            <dependency>
                <groupId>org.freemarker</groupId>
                <artifactId>freemarker</artifactId>
                <version>2.3.31</version>
            </dependency>


        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.plugin.version}</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <layers>
                        <enabled>true</enabled>
                    </layers>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>