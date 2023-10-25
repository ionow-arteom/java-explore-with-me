package ru.practicum.hit;

import ru.practicum.dto.HitDto;
import static ru.practicum.dto.utilities.Constants.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Mapper {

    public static HitDto toHitDto(Hit hit) {
        return HitDto.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .build();
    }

    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
                .id(hitDto.getId())
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }

    public static List<HitDto> toHitDtoList(Iterable<Hit> hits) {
        return StreamSupport.stream(hits.spliterator(), false)
                .map(Mapper::toHitDto)
                .collect(Collectors.toList());
    }
}