//package ru.practicum;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import ru.practicum.dto.HitDto;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//class StatsClientTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @InjectMocks
//    private StatsClient statsClient;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("When adding a hit, then the correct endpoint is called")
//    void testAdd() {
//        HitDto hitDto = new HitDto(1L, "testApp", "testUri", "127.0.0.1", "2023-10-22 12:00:00");
//        when(restTemplate.postForEntity(anyString(), eq(hitDto), eq(Object.class)))
//                .thenReturn(ResponseEntity.ok().build());
//
//        ResponseEntity<Object> response = statsClient.add(hitDto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("When retrieving stats with URIs, then the correct endpoint is called")
//    void testFindStats() {
//        String[] uris = {"uri1", "uri2"};
//        when(restTemplate.getForEntity(anyString(), eq(Object.class)))
//                .thenReturn(ResponseEntity.ok().build());
//
//        ResponseEntity<Object> response = statsClient.findStats("2023-10-01 12:00:00", "2023-10-22 12:00:00", uris, true);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("When retrieving stats without URIs, then the correct endpoint is called")
//    void testFindStatsWithoutUris() {
//        when(restTemplate.getForEntity(anyString(), eq(Object.class)))
//                .thenReturn(ResponseEntity.ok().build());
//
//        ResponseEntity<Object> response = statsClient.findStatsWithoutUris("2023-10-01 12:00:00", "2023-10-22 12:00:00", true);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//}