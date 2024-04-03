package org.nott.cli.common.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-4-3
 */
@RefreshScope
@Data
@Component
@ConfigurationProperties(prefix = "es")
public class ElasticSearchProp {

    private Integer port;

    private String host;

    private String scheme;
}
