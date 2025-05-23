package org.example.evaluations.evaluation.dtos;

import lombok.Data;
import org.example.evaluations.evaluation.models.LinkedInSearchItem;

import java.util.List;

@Data
public class LinkedInSearchData {
    private List<LinkedInSearchItem> items;
}
