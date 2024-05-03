spring:
  profiles:
    active: dev

  application:
    name: ${parent.applicationName}

logging:
  config: classpath:logback-dev.xml

server:
  port: 9901

