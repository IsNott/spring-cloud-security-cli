spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true

api:
  prefix: /api

management:
  endpoint:
    gateway:
      enabled: true
  endpoints:
    web:
      exposure:
        # actuator 暴露所有端口可能有安全问题
        include: '*'