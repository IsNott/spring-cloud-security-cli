package org.nott.cli.common.config;

import org.nott.common.config.SwaggerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import jakarta.annotation.Resource;

/**
 * @author Nott
 * @date 2024-8-8
 */

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Resource
    private SwaggerConfig swaggerConfig;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("# Spring-Cloud-Cli")
                        .termsOfServiceUrl("https://github.com/IsNott/spring-cloud-security-cli/")
                        .contact(new Contact("Nott", "https://github.com/isnott/", "isnott1028@outlook.com"))
                        .version("v0.1.0")
                        .build())
                //分组名称
                .groupName(swaggerConfig.getGroupName())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
