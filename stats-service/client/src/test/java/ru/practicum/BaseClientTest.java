package ru.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class BaseClientTest {

    @Mock
    private RestTemplate restTemplate;

    private BaseClient baseClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        baseClient = new BaseClient(restTemplate);
    }

    @DisplayName("When making a POST request, then the correct response is returned")
    @Test
    void testPost() {
        String endpoint = "http://example.com/api";
        String payload = "testPayload";

        ResponseEntity<Object> mockResponse = new ResponseEntity<>(payload, HttpStatus.CREATED);
        when(restTemplate.exchange(eq(endpoint), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class), anyMap()))
                .thenReturn(mockResponse);

        ResponseEntity<Object> response = baseClient.post(endpoint, payload);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(payload, response.getBody());
    }

    @DisplayName("When making a GET request with query parameters, then the correct response is returned")
    @Test
    void testGet() {
        String endpoint = "http://example.com/api";
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("key", "value");

        ResponseEntity<Object> mockResponse = new ResponseEntity<>("response", HttpStatus.OK);
        when(restTemplate.exchange(eq(endpoint), eq(HttpMethod.GET), any(HttpEntity.class), eq(Object.class), eq(queryParams)))
                .thenReturn(mockResponse);

        ResponseEntity<Object> response = baseClient.get(endpoint, queryParams);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("response", response.getBody());
    }

    @DisplayName("When an error occurs during the request, then the correct error response is returned")
    @Test
    void testHttpStatusCodeException() {
        String endpoint = "http://example.com/api";
        String payload = "testPayload";

        HttpStatusCodeException exception = new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "Bad Request") {
            @Override
            public byte[] getResponseBodyAsByteArray() {
                return "Error".getBytes();
            }
        };

        when(restTemplate.exchange(eq(endpoint), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class), anyMap()))
                .thenThrow(exception);

        ResponseEntity<Object> response = baseClient.post(endpoint, payload);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", new String((byte[]) response.getBody()));
    }
}