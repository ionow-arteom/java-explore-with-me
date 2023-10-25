//package ru.practicum.hit;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import ru.practicum.dto.HitDto;
//import ru.practicum.dto.StatsDto;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static ru.practicum.dto.utilities.Constants.DATE_TIME_FORMATTER;
//
//public class ControllerTest {
//
//    @Mock
//    private HitService service;
//
//    @InjectMocks
//    private Controller controller;
//
//    private MockMvc mockMvc;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//    }
//
//    @DisplayName("Ensure POST /hit with valid HitDto calls the service's add method and returns 201 Created")
//    @Test
//    public void testAddHit() throws Exception {
//        HitDto hitDto = new HitDto(1L, "testApp", "/testUri", "127.0.0.1", "2023-10-22 12:00:00");
//
//        mockMvc.perform(post("/hit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(hitDto)))
//                .andExpect(status().isCreated());
//
//        verify(service, times(1)).add(any(HitDto.class));
//    }
//
//    @DisplayName("Ensure GET /stats with valid parameters calls the service's findStats method and returns expected stats")
//    @Test
//    public void testRetrieveStats() throws Exception {
//        LocalDateTime start = LocalDateTime.now().minusDays(1);
//        LocalDateTime end = LocalDateTime.now();
//        StatsDto statsDto = new StatsDto("testApp", "/testUri", 10L);
//
//        when(service.findStats(any(), any(), any(), any())).thenReturn(Collections.singletonList(statsDto));
//
//        mockMvc.perform(get("/stats")
//                        .param("start", start.format(DATE_TIME_FORMATTER))
//                        .param("end", end.format(DATE_TIME_FORMATTER)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json("[{'app':'testApp', 'uri':'/testUri', 'hits':10}]"));
//
//        verify(service, times(1)).findStats(any(), any(), any(), any());
//    }
//}