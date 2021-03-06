package net.dnjo.indexer.interfaces;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import net.dnjo.indexer.services.IndexClientService;

import java.time.LocalDateTime;

@JsonFilter("indexedObject")
public interface IndexedObject {
    String getId();

    @ApiModelProperty(readOnly = true)
    LocalDateTime getCreatedAt();

    @ApiModelProperty(readOnly = true)
    LocalDateTime getUpdatedAt();

    @JsonIgnore
    IndexClientService.IndexDefinition getIndexDefinition();
}
