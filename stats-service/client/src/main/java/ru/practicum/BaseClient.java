package ru.practicum;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class BaseClient {
    private final RestTemplate restTemplate;

    public BaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected <T> ResponseEntity<Object> post(String endpoint, T payload) {
        return executeRequest(HttpMethod.POST, endpoint, null, payload);
    }

    protected ResponseEntity<Object> get(String endpoint, @Nullable Map<String, Object> queryParams) {
        return executeRequest(HttpMethod.GET, endpoint, queryParams, null);
    }

    private <T> ResponseEntity<Object> executeRequest(HttpMethod httpMethod, String endpoint,
                                                      @Nullable Map<String, Object> queryParams, @Nullable T payload) {
        HttpEntity<T> httpEntity = new HttpEntity<>(payload, createDefaultHeaders());
        try {
            return restTemplate.exchange(endpoint, httpMethod, httpEntity, Object.class,
                    Optional.ofNullable(queryParams).orElse(Collections.emptyMap()));
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsByteArray());
        }
    }

    private HttpHeaders createDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    private static ResponseEntity<Object> processResponse(ResponseEntity<Object> originalResponse) {
        HttpStatus status = originalResponse.getStatusCode();
        if (status.is2xxSuccessful()) {
            return originalResponse;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(status);
        return originalResponse.hasBody() ? responseBuilder.body(originalResponse.getBody()) : responseBuilder.build();
    }
}