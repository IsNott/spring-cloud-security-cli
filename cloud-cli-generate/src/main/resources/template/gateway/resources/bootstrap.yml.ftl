spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:18848
        namespace: cloud-cli
        ip: 127.0.0.1
      config:
        server-addr: localhost:18848
        namespace: cloud-cli
        file-extension: yaml
        group: DEFAULT_GROUP