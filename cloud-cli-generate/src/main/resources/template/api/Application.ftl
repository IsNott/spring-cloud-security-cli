package ${packageName};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<#if parent.mode! == "1">import org.springframework.cloud.client.discovery.EnableDiscoveryClient;</#if>

@SpringBootApplication(scanBasePackages = {"${packageName}"})
@MapperScan(basePackages = {"${packageName}.service.mapper.**"})
<#if parent.mode! == "1">@EnableDiscoveryClient</#if>
public class Application {
     public static void main(String[] args) {

         SpringApplication.run(Application.class, args);
     }
}