package net.dnjo.indexer.interfaces;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("ignoreId")
public interface HasId {
    String getId();
}
