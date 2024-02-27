package org.nott;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"org.nott.cli"})
@MapperScan(basePackages = {"org.nott.cli.service.mapper.**"})
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}