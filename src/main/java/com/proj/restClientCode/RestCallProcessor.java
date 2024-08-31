package com.proj.restClientCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCallProcessor {

    @Autowired
    private RestCallExecutor<String> restCallEntities;

    private String url = "https://dummyapi.online/api/movies";

    @Autowired
    @Qualifier("restTemplateConfigWithProxy")
    RestTemplate restTemplate;

    public String restCallExecutor() {
        String response = null;
        try {
            restCallEntities
                    .withRestTemplateInstance(restTemplate)
                    .setUrl(url)
                    .setApplicationId("1234")
                    .setHttpMethod(HttpMethod.GET)
                    .setHttpHeaders(() -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        return headers;
                    })
                    .setResponseType(String.class)
                    .setSource("RESTTEMPLATECALL")
                    .setTarget("XYZLIBRARY");
            response = restCallEntities.execute();
        } catch (Exception e) {

            String msg = e.getMessage();
            throw e;
        }

        return response;
    }
}
