package org.example.evaluations.evaluation.services;

import org.example.evaluations.evaluation.dtos.LinkedInSearchRequest;
import org.example.evaluations.evaluation.models.LinkedInSearchItem;

import java.util.List;

public interface ISearchService {
    List<LinkedInSearchItem> searchPeople(LinkedInSearchRequest linkedInSearchRequest);
}
