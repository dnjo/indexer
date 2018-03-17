package net.dnjo.indexer.configurations;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "indexer.elasticsearch")
@Getter
@Setter
public class ElasticsearchConfiguration {
    private String url = "http://localhost:9200";
    private String indexPrefix = "dnjo-indexer-";

    @Bean
    public JestClientFactory jestClientFactory() {
        final JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(url)
                .build());
        return factory;
    }

    @Bean(destroyMethod = "close")
    public JestClient jestClient(JestClientFactory jestClientFactory) {
        return jestClientFactory.getObject();
    }

    public String indexName(@NotNull final String name) {
        return indexPrefix + name;
    }
}
