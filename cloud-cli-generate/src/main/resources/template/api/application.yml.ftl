spring:
profiles:
active: dev

application:
name: ${parent.applicationName}

logging:
config: classpath:logback/logback.xml

server:
port: 9901

