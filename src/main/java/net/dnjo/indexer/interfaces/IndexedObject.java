package net.dnjo.indexer.interfaces;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.dnjo.indexer.services.IndexClientService;

@JsonFilter("ignoreId")
public interface IndexedObject {
    String getId();

    @JsonIgnore
    IndexClientService.IndexDefinition getIndexDefinition();
}
