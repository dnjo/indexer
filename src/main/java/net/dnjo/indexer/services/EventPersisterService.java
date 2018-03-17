package net.dnjo.indexer.services;

import lombok.val;
import net.dnjo.indexer.models.PersonalEvent;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
public class EventPersisterService {
    private final IndexClientService indexClientService;

    public EventPersisterService(IndexClientService indexClientService) {
        this.indexClientService = indexClientService;
    }

    public PersonalEvent persistPersonalEvent(@NotNull PersonalEvent personalEvent) {
        val receivedTime = LocalDateTime.now();
        val event = personalEvent.toBuilder().receivedTime(receivedTime).build();
        indexClientService.indexObject(event, IndexClientService.IndexDefinition.PERSONAL_EVENT);
        return event;
    }
}
