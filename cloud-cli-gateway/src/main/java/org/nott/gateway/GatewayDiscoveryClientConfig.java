package org.nott.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.discovery.GatewayDiscoveryClientAutoConfiguration;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Nott
 * @date 2024-12-20
 */
@Configuration
public class GatewayDiscoveryClientConfig<main> extends GatewayDiscoveryClientAutoConfiguration {

    @Value("${api.prefix:/api}")
    private String prefix;

    @Bean
    @Override
    public DiscoveryLocatorProperties discoveryLocatorProperties() {
        DiscoveryLocatorProperties properties = new DiscoveryLocatorProperties();
        properties.setPredicates(configMyPredicate());
        properties.setFilters(configMyFilter());
        return properties;
    }

    private List<FilterDefinition> configMyFilter() {
        List<FilterDefinition> definitions = new ArrayList<>();
        FilterDefinition definition = new FilterDefinition();
        definition.setName(NameUtils.normalizeFilterFactoryName(RewritePathGatewayFilterFactory.class));
        String regex = "'" + prefix + "/' + serviceId + '/(?<remaining>.*)'";
        String replacement = "'/${remaining}'";
        definition.addArg("regexp", regex);
        definition.addArg("replacement", replacement);
        definitions.add(definition);
        return definitions;
    }

    private List<PredicateDefinition> configMyPredicate() {
        List<PredicateDefinition> definitions = new ArrayList<>();
        PredicateDefinition definition = new PredicateDefinition();
        definition.setName(NameUtils.normalizeRoutePredicateName(PathRoutePredicateFactory.class));
        String pattern = "'" + prefix + "/'+serviceId+'/**'";
        definition.addArg("pattern", pattern);
        definitions.add(definition);
        return definitions;
    }
}
