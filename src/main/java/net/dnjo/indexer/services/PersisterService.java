package net.dnjo.indexer.services;

import lombok.val;
import net.dnjo.indexer.models.PersonalEvent;
import net.dnjo.indexer.models.Tag;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
public class PersisterService {
    private final IndexClientService indexClientService;

    public PersisterService(IndexClientService indexClientService) {
        this.indexClientService = indexClientService;
    }

    public PersonalEvent persistPersonalEvent(@NotNull PersonalEvent personalEvent) {
        val now = LocalDateTime.now();
        val newEvent = personalEvent.toBuilder()
                .createdAt(now)
                .updatedAt(now)
                .build();
        val eventId = indexClientService.indexObject(newEvent);
        return newEvent.toBuilder().id(eventId).build();
    }

    public void updatePersonalEvent(@NotNull String id, @NotNull PersonalEvent personalEvent) {
        val updatedEvent = personalEvent.toBuilder().updatedAt(LocalDateTime.now()).build();
        indexClientService.updateObject(id, updatedEvent);
    }

    public Tag persistTag(@NotNull Tag tag) {
        val now = LocalDateTime.now();
        val newTag = tag.toBuilder()
                .createdAt(now)
                .updatedAt(now)
                .build();
        val tagId = indexClientService.indexObject(newTag);
        return newTag.toBuilder().id(tagId).build();
    }

    public void updateTag(@NotNull String id, @NotNull Tag tag) {
        val updatedTag = tag.toBuilder().updatedAt(LocalDateTime.now()).build();
        indexClientService.updateObject(id, updatedTag);
    }
}
