package org.example.evaluations.evaluation.services;

import org.example.evaluations.evaluation.dtos.LinkedInSearchData;
import org.example.evaluations.evaluation.dtos.LinkedInSearchRequest;
import org.example.evaluations.evaluation.dtos.LinkedInSearchResult;
import org.example.evaluations.evaluation.interceptors.HeaderRequestInterceptor;
import org.example.evaluations.evaluation.models.LinkedInSearchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LinkedInSearchService implements ISearchService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    //Take help from - https://binarycoders.wordpress.com/2020/10/04/add-a-header-to-spring-resttemplate/#:~:text=add(%20new%20HeaderRequestInterceptor(%20%22X,setInterceptors(interceptors)%3B
    public List<LinkedInSearchItem> searchPeople(LinkedInSearchRequest linkedInSearchRequest) {
        //Add your implementation here
        System.out.println("incoming req url (before change):" + linkedInSearchRequest.getUrl());
        //linkedInSearchRequest.setUrl("https://www.linkedin.com/search/results/people/?currentCompany=%5B%221035%22%5D&geoUrn=%5B%22103644278%22%5D&keywords=max&origin=FACETED_SEARCH&sid=%3AB5");//testing
        //Mine
        /*You need to use postForEntity method with Endpoint - https://linkedin-data-api.p.rapidapi.com/search-people-by-url. The response will be in form of LinkedInSearchResult. Since this is protected call and request and response both are of different datatype, exchange method you used in previous assignments will not work. In this case, you need to set interceptors while building RestTemplate Object. Please refer https://binarycoders.wordpress.com/2020/10/04/add-a-header-to-spring-resttemplate/#:~:text=add(%20new%20HeaderRequestInterceptor(%20%22X,setInterceptors(interceptors)%3B on how to achieve this. */
        /*public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
 
    private final String headerName;
    private final String headerValue;
 
    public HeaderRequestInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }
 
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set(headerName, headerValue);
        return execution.execute(request, body);
    }
} */
    /* Now, we add it to our ‘RestTemplate’:
List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
interceptors.add(new HeaderRequestInterceptor("X-Custom-Header", "<custom_value>"));
 
RestTemplate restTemplate = new RestTemplate();
restTemplate.setInterceptors(interceptors); */
/* side note: Just an extra side note. As of Spring Framework 5, a new HTTP client called ‘WebClient’ has been added. It is assumed that ‘RestTemplate’ will be deprecated at some point. If we are starting a new application, specially if you are using the ‘WebFlux’ stack, it will be a better choice to use the new version. */

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("X-RapidAPI-Key", "d01744daf3msh556f35c35f05b00p121b6ajsne56804740137"));
 
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setInterceptors(interceptors);
        final String URL = "https://linkedin-data-api.p.rapidapi.com/search-people-by-url"; //if used this then NPE because the string contianed space first
        ResponseEntity<LinkedInSearchResult> responseEntity = restTemplate.postForEntity(URL, linkedInSearchRequest, LinkedInSearchResult.class);
        
        //if empty list result commented then nullpointer
        List<LinkedInSearchItem> resultEmpty = Collections.emptyList();
        if(responseEntity == null) return resultEmpty;
        LinkedInSearchResult linkedInSearchResult = responseEntity.getBody();
        if(linkedInSearchResult == null) return resultEmpty;
        LinkedInSearchData linkedInSearchData = linkedInSearchResult.getData();
        if(linkedInSearchData == null) return resultEmpty;
        return linkedInSearchData.getItems();
    }
}
