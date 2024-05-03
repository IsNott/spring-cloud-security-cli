package ${packageName};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"${packageName}"})
@MapperScan(basePackages = {"${packageName}.service.mapper.**"})
@EnableDiscoveryClient
public class Application {
     public static void main(String[] args) {

         SpringApplication.run(Application.class, args);
     }
}