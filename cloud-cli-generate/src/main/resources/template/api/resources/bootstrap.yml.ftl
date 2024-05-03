spring:
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
        namespace: public
        ip: 127.0.0.1
      config:
        server-addr: localhost:8848
        namespace: ${parent.childModuleDirPrefix}
        file-extension: yaml
        group: DEFAULT_GROUP