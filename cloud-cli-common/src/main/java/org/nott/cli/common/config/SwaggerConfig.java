package org.nott.cli.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-8-9
 */

@Data
@Component
@ConfigurationProperties(prefix = "swagger2")
public class SwaggerConfig {

    private String groupName;
}
