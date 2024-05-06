package ${parent.groupId}.${parent.childLastPackage}.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

<#if parent.mode! == "1">@RefreshScope</#if>
@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtConfig {

    private String header;

    private String secret;

    private Integer expireMinute;

    private String tokenPrefix;

    private Integer expireTime;
}
