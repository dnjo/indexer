package net.dnjo.indexer.controllers;

import net.dnjo.indexer.enums.QueryObjectType;
import net.dnjo.indexer.services.IndexClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private final IndexClientService indexClientService;

    @Autowired
    public SearchController(IndexClientService indexClientService) {
        this.indexClientService = indexClientService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String search(@RequestBody String query, @RequestParam QueryObjectType objectType) {
        return indexClientService.query(query, objectType).getJsonString();
    }
}
