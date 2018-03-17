package net.dnjo.indexer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.dnjo.indexer.configurations.ElasticsearchConfiguration;
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

    @AllArgsConstructor
    @Getter
    @ToString
    public enum IndexDefinition {
        PERSONAL_EVENT("personal-events", "personal-event"),
        TAG("tags", "tag");

        private String index;
        private String type;
    }

    @Autowired
    public IndexClientService(ElasticsearchConfiguration elasticsearchConfiguration, JestClient jestClient) {
        this.elasticsearchConfiguration = elasticsearchConfiguration;
        this.jestClient = jestClient;
    }

    @SneakyThrows({ IOException.class })
    public String indexObject(@NotNull Object object, @NotNull IndexDefinition indexDefinition) {
        val source = objectSerializer.writeValueAsString(object);
        log.debug("Indexing object '{}' in index {}", source, indexDefinition);
        val index = elasticsearchConfiguration.indexName(indexDefinition.getIndex());
        val indexAction = new Index.Builder(source)
                .index(index)
                .type(indexDefinition.getType())
                .build();
        val result = jestClient.execute(indexAction);
        log.debug("Got index result '{}'", result.getJsonObject());
        return result.getId();
    }
}
