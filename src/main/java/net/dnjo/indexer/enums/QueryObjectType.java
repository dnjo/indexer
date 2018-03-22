package net.dnjo.indexer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dnjo.indexer.services.IndexClientService;

@AllArgsConstructor
@Getter
public enum QueryObjectType {
    PERSONAL_EVENT(IndexClientService.IndexDefinition.PERSONAL_EVENT);

    private IndexClientService.IndexDefinition indexDefinition;
}
