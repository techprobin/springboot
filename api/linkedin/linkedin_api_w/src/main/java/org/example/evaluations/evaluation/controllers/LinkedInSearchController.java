package org.example.evaluations.evaluation.controllers;

import java.util.List;

import org.example.evaluations.evaluation.dtos.LinkedInSearchRequest;
import org.example.evaluations.evaluation.models.LinkedInSearchItem;
import org.example.evaluations.evaluation.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/linkedInSearch")
public class LinkedInSearchController {

    @Autowired
    private ISearchService searchService;

    //Add your APIs here
    @PostMapping
    public List<LinkedInSearchItem> searchPeople(@RequestBody LinkedInSearchRequest linkedInSearchRequest) {
        //Add your implementation here
        return searchService.searchPeople(linkedInSearchRequest);
    }
}
