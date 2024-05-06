<#if parent.mode! == '0'>
spring:
  servlet:
    multipart:
      max-file-size: 100MB
      max-request-size: 100MB
      enabled: true
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/your_db_name?allowMultiQueries=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
    username: root
    password: pass
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      minimum-idle: 0
      maximum-pool-size: 20
      idle-timeout: 10000
      connection-test-query: select 1
  data:
    redis:
      host: localhost
      port: 6379
      database: 0
      # password:
mybatis-plus:
  mapper-locations: classpath*:/mapper/*Mapper.xml
  global-config:
    banner: false
    db-config:
      # id-type: AUTO
      field-strategy: NOT_NULL
      table-underline: true

jwt:
  token:
  header: 'Authorization'
  secret: 'your-key'
  tokenPrefix: 'Bearer'
  expireMinute: 60
  expireTime: 3600000

es:
  port: 9200
  host: localhost
  scheme: http

<#else>
spring:
  profiles:
    active: dev

  application:
    name: ${parent.applicationName}

logging:
  config: classpath:logback-dev.xml

server:
  port: 9901
</#if>