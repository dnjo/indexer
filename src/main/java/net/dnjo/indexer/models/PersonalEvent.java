package net.dnjo.indexer.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class PersonalEvent {
    @NotNull
    private LocalDateTime eventTime;

    @ApiModelProperty(readOnly = true)
    private LocalDateTime receivedTime;

    @NotNull
    private String description;
}
