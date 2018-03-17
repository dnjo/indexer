package net.dnjo.indexer.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JacksonConfiguration implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ObjectMapper) {
            // Ignore any filters on objects to be serialized. For example, the "ignoreId" filter is used to filter out
            // the ID field in Elasticsearch, but we want to return the field to API consumers.
            val objectMapper = (ObjectMapper) bean;
            objectMapper.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
        }
        return bean;
    }
}
