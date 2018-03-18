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
        val receivedTime = LocalDateTime.now();
        val event = personalEvent.toBuilder().receivedTime(receivedTime).build();
        val eventId = indexClientService.indexObject(event);
        return event.toBuilder().id(eventId).build();
    }

    public Tag persistTag(@NotNull Tag tag) {
        val tagId = indexClientService.indexObject(tag);
        return tag.toBuilder().id(tagId).build();
    }
}
