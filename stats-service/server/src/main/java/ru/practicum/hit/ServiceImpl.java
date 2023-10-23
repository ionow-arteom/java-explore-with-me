package ru.practicum.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceImpl implements HitService {

    private final HitRepository repository;

    @Override
    @Transactional
    public void add(HitDto hitDto) {
        Hit hit = Mapper.toHit(hitDto);
        repository.save(hit);
    }

    @Override
    public List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (isUrisEmpty(uris)) {
            return unique ? fetchStatsByUniqueIp(start, end) : fetchAllStats(start, end);
        } else {
            return unique ? fetchStatsByUrisAndUniqueIp(start, end, uris) : fetchStatsByUris(start, end, uris);
        }
    }

    private boolean isUrisEmpty(List<String> uris) {
        return uris == null || uris.isEmpty();
    }

    private List<StatsDto> fetchStatsByUniqueIp(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching stats by unique IP.");
        return repository.findAllStatsByUniqueIp(start, end);
    }

    private List<StatsDto> fetchAllStats(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching all stats.");
        return repository.findAllStats(start, end);
    }

    private List<StatsDto> fetchStatsByUrisAndUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris) {
        log.info("Fetching stats by URIs and unique IP.");
        return repository.findStatsByUrisByUniqueIp(start, end, uris);
    }

    private List<StatsDto> fetchStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris) {
        log.info("Fetching stats by URIs.");
        return repository.findStatsByUris(start, end, uris);
    }
}