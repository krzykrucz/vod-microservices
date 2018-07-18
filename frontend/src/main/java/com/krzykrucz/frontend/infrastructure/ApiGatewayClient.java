package com.krzykrucz.frontend.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class ApiGatewayClient {

    private static final String API_GATEWAY_HOST = "http://localhost:8060";

    private final RestTemplate restTemplate;

    @Autowired
    ApiGatewayClient(RestTemplateBuilder restTemplateBuilder, NonThrowingResponseErrorHandler responseErrorHandler) {
        restTemplate = restTemplateBuilder
                .errorHandler(responseErrorHandler)
                .build();
    }

    public <RES> ResponseEntity<RES> post(ApiEndpoint<RES> apiEndpoint, Object requestObject) {
        return restTemplate.postForEntity(URI.create(API_GATEWAY_HOST + apiEndpoint.getUrl()), requestObject, apiEndpoint.getResponseType());
    }

    public <RES> ResponseEntity<RES> get(ApiEndpoint<RES> apiEndpoint) {
        return restTemplate.getForEntity(API_GATEWAY_HOST + apiEndpoint.getUrl(), apiEndpoint.getResponseType(), apiEndpoint.getPathVariables());
    }
}
