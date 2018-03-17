package net.dnjo.indexer.controllers;

import net.dnjo.indexer.models.PersonalEvent;
import net.dnjo.indexer.services.PersisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final PersisterService persisterService;

    @Autowired
    public EventController(PersisterService persisterService) {
        this.persisterService = persisterService;
    }

    @RequestMapping(value = "/personal-event", method = RequestMethod.POST)
    public PersonalEvent createPersonalEvent(@RequestBody PersonalEvent personalEvent) {
        return persisterService.persistPersonalEvent(personalEvent);
    }
}
