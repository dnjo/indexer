package net.dnjo.indexer.controllers;

import net.dnjo.indexer.models.PersonalEvent;
import net.dnjo.indexer.services.EventPersisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    private final EventPersisterService eventPersisterService;

    @Autowired
    public EventController(EventPersisterService eventPersisterService) {
        this.eventPersisterService = eventPersisterService;
    }

    @RequestMapping(value = "/personal-event", method = RequestMethod.POST)
    public PersonalEvent createPersonalEvent(@RequestBody PersonalEvent personalEvent) {
        return eventPersisterService.persistPersonalEvent(personalEvent);
    }
}
