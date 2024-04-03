package org.nott.cli.common.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author Nott
 * @date 2024-4-3
 */
@Component
public class ElasticSearchComponent {

    @Autowired
    private ElasticsearchClient client;

    public boolean createIndex(String indexName) throws IOException {
        CreateIndexResponse createIndexResponse = client.indices().create(
                c -> c.index(indexName));
        return createIndexResponse.acknowledged();
    }

    public boolean indexIsExists(String indexName) throws IOException {
        BooleanResponse response = client.indices().exists(e -> e.index(indexName));
        return response.value();
    }

    public Map<String, IndexState> queryIndex(String indexName) throws IOException {
        GetIndexResponse response = client.indices().get(i -> i.index(indexName));
        return response.result();
    }
}
