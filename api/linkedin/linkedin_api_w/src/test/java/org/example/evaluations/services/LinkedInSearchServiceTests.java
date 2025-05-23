package org.example.evaluations.services;

import org.example.evaluations.evaluation.dtos.LinkedInSearchData;
import org.example.evaluations.evaluation.dtos.LinkedInSearchRequest;
import org.example.evaluations.evaluation.dtos.LinkedInSearchResult;
import org.example.evaluations.evaluation.models.LinkedInSearchItem;
import org.example.evaluations.evaluation.services.LinkedInSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LinkedInSearchServiceTests {

    @Autowired
    private LinkedInSearchService linkedInSearchService;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
    }

    @Test
    void testSearchPeople() {
        // Prepare test data
        LinkedInSearchRequest searchRequest = new LinkedInSearchRequest();
        searchRequest.setUrl("https://www.linkedin.com/search/results/people/?currentCompany=%5B%221035%22%5D&geoUrn=%5B%22103644278%22%5D&keywords=max&origin=FACETED_SEARCH&sid=%3AB5\\\"}");

        LinkedInSearchItem item = new LinkedInSearchItem();
        item.setFullName("Max Montes Soza");
        item.setLocation("Wayland, MA");
        item.setUsername("max-montes-soza");

        LinkedInSearchData searchData = new LinkedInSearchData();
        searchData.setItems(Collections.singletonList(item));

        LinkedInSearchResult searchResult = new LinkedInSearchResult();
        searchResult.setData(searchData);

        ResponseEntity<LinkedInSearchResult> responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);

        when(restTemplate.postForEntity(
                eq("https://linkedin-data-api.p.rapidapi.com/search-people-by-url"),
                eq(searchRequest),
                eq(LinkedInSearchResult.class))
        ).thenReturn(responseEntity);

        // Call the method under test
        List<LinkedInSearchItem> items = linkedInSearchService.searchPeople(searchRequest);

        // Verify the result
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
        verify(restTemplate).setInterceptors(anyList());
        verify(restTemplate,times(1)).setInterceptors(any());
    }
}
