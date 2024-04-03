package org.nott.cli.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.Resource;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.nott.cli.common.prop.ElasticSearchProp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nott
 * @date 2024-4-3
 */
@Configuration
public class ElasticSearchConfig {

    @Resource
    private ElasticSearchProp elasticSearchProp;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient client = RestClient.builder(new HttpHost(elasticSearchProp.getHost(), elasticSearchProp.getPort(), elasticSearchProp.getScheme()))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }


}
