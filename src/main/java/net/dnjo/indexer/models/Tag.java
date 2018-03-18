package net.dnjo.indexer.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import net.dnjo.indexer.interfaces.IndexedObject;
import net.dnjo.indexer.services.IndexClientService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Setter(AccessLevel.NONE)
@Builder(toBuilder = true)
public class Tag implements IndexedObject {
    @ApiModelProperty(readOnly = true)
    private String id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    private String name;

    @Override
    public IndexClientService.IndexDefinition getIndexDefinition() {
        return IndexClientService.IndexDefinition.TAG;
    }
}
