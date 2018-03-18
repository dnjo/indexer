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
import java.util.List;

@Data
@Setter(AccessLevel.NONE)
@Builder(toBuilder = true)
public class PersonalEvent implements IndexedObject {
    @ApiModelProperty(readOnly = true)
    private String id;

    @NotNull
    private LocalDateTime eventTime;

    @ApiModelProperty(readOnly = true)
    private LocalDateTime receivedTime;

    @NotNull
    private String description;

    private List<String> tags;

    @Override
    public IndexClientService.IndexDefinition getIndexDefinition() {
        return IndexClientService.IndexDefinition.PERSONAL_EVENT;
    }
}
