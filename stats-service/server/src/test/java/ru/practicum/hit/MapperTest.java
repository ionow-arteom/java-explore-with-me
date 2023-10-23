package ru.practicum.hit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.dto.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    private static final DateTimeFormatter TEST_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Test
    @DisplayName("Ensure Hit is correctly mapped to HitDto")
    public void testToHitDto() {
        Hit hit = new Hit(1L, "App1", "/home", "192.168.1.1", LocalDateTime.of(2022, 10, 22, 12, 0));
        HitDto hitDto = Mapper.toHitDto(hit);

        assertEquals(hit.getId(), hitDto.getId());
        assertEquals(hit.getApp(), hitDto.getApp());
        assertEquals(hit.getUri(), hitDto.getUri());
        assertEquals(hit.getIp(), hitDto.getIp());
        assertEquals(hit.getTimestamp().format(TEST_DATE_TIME_FORMATTER), hitDto.getTimestamp());
    }

    @Test
    @DisplayName("Ensure HitDto is correctly mapped to Hit")
    public void testToHit() {
        HitDto hitDto = new HitDto(1L, "App1", "/home", "192.168.1.1", "2022-10-22 12:00:00");
        Hit hit = Mapper.toHit(hitDto);

        assertEquals(hitDto.getId(), hit.getId());
        assertEquals(hitDto.getApp(), hit.getApp());
        assertEquals(hitDto.getUri(), hit.getUri());
        assertEquals(hitDto.getIp(), hit.getIp());
        assertEquals(LocalDateTime.parse(hitDto.getTimestamp(), TEST_DATE_TIME_FORMATTER), hit.getTimestamp());
    }

    @Test
    @DisplayName("Ensure List<Hit> is correctly mapped to List<HitDto>")
    public void testToHitDtoList() {
        Hit hit1 = new Hit(1L, "App1", "/home", "192.168.1.1", LocalDateTime.of(2022, 10, 22, 12, 0));
        Hit hit2 = new Hit(2L, "App2", "/dashboard", "192.168.1.2", LocalDateTime.of(2022, 10, 22, 13, 0));
        List<Hit> hits = Arrays.asList(hit1, hit2);

        List<HitDto> hitDtos = Mapper.toHitDtoList(hits);

        assertEquals(2, hitDtos.size());
        assertEquals(hit1.getId(), hitDtos.get(0).getId());
        assertEquals(hit2.getId(), hitDtos.get(1).getId());
    }
}