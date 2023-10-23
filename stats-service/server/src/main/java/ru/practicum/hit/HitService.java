package ru.practicum.hit;

import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HitService {
    void add(HitDto hitDto);

    List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
