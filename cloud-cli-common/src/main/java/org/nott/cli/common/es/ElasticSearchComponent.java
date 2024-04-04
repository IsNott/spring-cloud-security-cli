package org.nott.cli.common.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ElasticSearch 8 base curd component
 *
 * @author Nott
 * @date 2024-4-3
 */
@Component
public class ElasticSearchComponent<T> {

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

    public boolean deleteIndex(String indexName) throws IOException {
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(d -> d.index(indexName));
        return deleteIndexResponse.acknowledged();
    }

    public T queryDocument(String indexName, Object id, Class<T> tClass) throws IOException {
        GetResponse<T> response = client.get(g -> g.index(indexName).id(id), tClass);
        return response.source();
    }

    public void addDocument(T obj, String indexName, Object id) throws IOException {
        client.index(i -> i.index(indexName).id(id).document(obj));
    }

    public T updateDocument(T obj, String indexName, Object id, Class<T> tClass) throws IOException {
        UpdateResponse<T> updateResponse = client.update(u -> u
                        .index(indexName)
                        .id(id)
                        .doc(obj)
                , tClass);
        return updateResponse.source();
    }

    public boolean docIsExists(String indexName, Object id) throws IOException {
        BooleanResponse indexResponse = client.exists(e -> e.index(indexName).id(id));
        return indexResponse.value();
    }

    public void deleteDocument(String indexName, Object id) throws IOException {
        DeleteResponse deleteResponse = client.delete(d -> d
                .index(indexName)
                .id(id)
        );
    }

    public void saveBatchDocument(List<T> objs, String indexName) {
        BulkResponse bulkResponse = client.bulk(b -> b.index(indexName)
                .operations(objs));
    }


}
