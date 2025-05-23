package org.example.evaluations.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.evaluations.evaluation.controllers.LinkedInSearchController;
import org.example.evaluations.evaluation.dtos.LinkedInSearchRequest;
import org.example.evaluations.evaluation.models.LinkedInSearchItem;
import org.example.evaluations.evaluation.services.ISearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkedInSearchController.class)
public class LinkedInSearchControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISearchService searchService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSearchPeopleByUrl() throws Exception {
        // Prepare test data
        LinkedInSearchRequest searchRequest = new LinkedInSearchRequest();
        searchRequest.setUrl("https://www.linkedin.com/search/results/people/?currentCompany=%5B%221035%22%5D&geoUrn=%5B%22103644278%22%5D&keywords=max&origin=FACETED_SEARCH&sid=%3AB5\\\"}");

        LinkedInSearchItem item = new LinkedInSearchItem();
        item.setUsername("maxcharlamb");

        List<LinkedInSearchItem> items = Collections.singletonList(item);

        when(searchService.searchPeople(searchRequest)).thenReturn(items);

        // Perform the POST request
        mockMvc.perform(post("/linkedInSearch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(item.getUsername()));

        verify(searchService).searchPeople(searchRequest);
    }
}
