package net.dnjo.indexer.controllers;

import net.dnjo.indexer.models.Tag;
import net.dnjo.indexer.services.PersisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final PersisterService persisterService;

    @Autowired
    public TagController(PersisterService persisterService) {
        this.persisterService = persisterService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Tag createTag(@RequestBody Tag tag) {
        return persisterService.persistTag(tag);
    }
}