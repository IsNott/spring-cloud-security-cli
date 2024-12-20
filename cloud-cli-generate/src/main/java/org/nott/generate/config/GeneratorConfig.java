package org.nott.generate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nott
 * @date 2024-12-20
 */
@Data
@ConfigurationProperties(prefix = "generator")
@Component
@RefreshScope
public class GeneratorConfig {

    private List<String> modules;

    private String defaultAuthor;

    private String projectRoot;
}
