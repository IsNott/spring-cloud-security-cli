package org.nott;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"your.group"})
@MapperScan(basePackages = {"your.mapper.location"})
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}