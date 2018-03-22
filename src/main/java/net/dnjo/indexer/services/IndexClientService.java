package net.dnjo.indexer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.dnjo.indexer.configurations.ElasticsearchConfiguration;
import net.dnjo.indexer.enums.QueryObjectType;
import net.dnjo.indexer.interfaces.IndexedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Service
@Slf4j
public class IndexClientService {
    private final ElasticsearchConfiguration elasticsearchConfiguration;
    private final JestClient jestClient;
    private final ObjectMapper objectSerializer = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setDateFormat(new StdDateFormat());
    private final FilterProvider ignoreIdFilterProvider;
    private final FilterProvider ignoreCreatedAtFilterProvider;

    @AllArgsConstructor
    @Getter
    @ToString
    public enum IndexDefinition {
        PERSONAL_EVENT("personal-events", "personal-event"),
        TAG("tags", "tag");

        private String index;
        private String type;
    }

    @AllArgsConstructor
    @Getter
    private static class UpdateDocument {
        private Object doc;
    }

    @Autowired
    public IndexClientService(ElasticsearchConfiguration elasticsearchConfiguration, JestClient jestClient) {
        this.elasticsearchConfiguration = elasticsearchConfiguration;
        this.jestClient = jestClient;

        val idFilter = SimpleBeanPropertyFilter.serializeAllExcept("id");
        val createdAtFilter = SimpleBeanPropertyFilter.serializeAllExcept("id", "createdAt");
        ignoreIdFilterProvider = new SimpleFilterProvider().addFilter("indexedObject", idFilter);
        ignoreCreatedAtFilterProvider = new SimpleFilterProvider().addFilter("indexedObject", createdAtFilter);
    }

    @SneakyThrows({ IOException.class })
    public String indexObject(@NotNull IndexedObject object) {
        val source = objectSerializer.writer(ignoreIdFilterProvider).writeValueAsString(object);
        log.debug("Indexing object '{}' in index {}", source, object.getIndexDefinition());
        val index = elasticsearchConfiguration.indexName(object.getIndexDefinition().getIndex());
        val indexAction = new Index.Builder(source)
                .index(index)
                .type(object.getIndexDefinition().getType())
                .build();
        val result = jestClient.execute(indexAction);
        log.debug("Got index result '{}'", result.getJsonObject());
        return result.getId();
    }

    @SneakyThrows({ IOException.class })
    public void updateObject(@NotNull String id, @NotNull IndexedObject object) {
        val source = objectSerializer.writer(ignoreCreatedAtFilterProvider).writeValueAsString(new UpdateDocument(object));
        log.debug("Updating object '{}' with ID {}", source, id);
        val index = elasticsearchConfiguration.indexName(object.getIndexDefinition().getIndex());
        val updateAction = new Update.Builder(source)
                .index(index)
                .type(object.getIndexDefinition().getType())
                .id(id)
                .build();
        val result = jestClient.execute(updateAction);
        log.debug("Got update result '{}'", result.getJsonObject());
    }

    @SneakyThrows({ IOException.class })
    public SearchResult query(@NotNull String query, @NotNull QueryObjectType queryObjectType) {
        val index = elasticsearchConfiguration.indexName(queryObjectType.getIndexDefinition().getIndex());
        val search = new Search.Builder(query)
                .addIndex(index)
                .build();
        return jestClient.execute(search);
    }
}
