package net.dnjo.indexer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import net.dnjo.indexer.configurations.ElasticsearchConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Service
public class IndexClientService {
    private final ElasticsearchConfiguration elasticsearchConfiguration;
    private final JestClient jestClient;
    private final ObjectMapper objectSerializer = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .setDateFormat(new StdDateFormat());

    @AllArgsConstructor
    @Getter
    public enum IndexDefinition {
        PERSONAL_EVENT("personal-events", "personal-event");

        private String index;
        private String type;
    }

    @Autowired
    public IndexClientService(ElasticsearchConfiguration elasticsearchConfiguration, JestClient jestClient) {
        this.elasticsearchConfiguration = elasticsearchConfiguration;
        this.jestClient = jestClient;
    }

    @SneakyThrows({ IOException.class })
    public void indexObject(@NotNull Object object, @NotNull IndexDefinition indexDefinition) {
        val source = objectSerializer.writeValueAsString(object);
        val index = elasticsearchConfiguration.indexName(indexDefinition.getIndex());
        val indexAction = new Index.Builder(source)
                .index(index)
                .type(indexDefinition.getType())
                .build();
        jestClient.execute(indexAction);
    }
}
