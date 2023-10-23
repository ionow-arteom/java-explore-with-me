package ru.practicum.hit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class ServiceImplTest {

    @Mock
    private HitRepository repository;

    @InjectMocks
    private ServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Add Hit")
    @Test
    void addTest() {
        HitDto hitDto = HitDto.builder()
                .id(1L)
                .app("TestApp")
                .uri("/test-uri")
                .ip("192.168.1.1")
                .timestamp("2023-10-22 12:00:00")
                .build();

        service.add(hitDto);
        verify(repository, times(1)).save(any());
    }

    @DisplayName("Find statistics")
    @Test
    void findStatsTest() {
        LocalDateTime start = LocalDateTime.of(2023, 10, 22, 12, 0);
        LocalDateTime end = LocalDateTime.of(2023, 10, 22, 13, 0);
        service.findStats(start, end, Collections.singletonList("/test-uri"), true);

        verify(repository, times(1)).findStatsByUrisByUniqueIp(start, end, Collections.singletonList("/test-uri"));
    }
}
