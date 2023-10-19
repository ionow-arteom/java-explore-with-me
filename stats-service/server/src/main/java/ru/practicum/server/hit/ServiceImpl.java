package ru.practicum.server.hit;

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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceImpl implements HitService {

    private final Repository repository;

    @Override
    @Transactional
    public void add(HitDto hitDto) {
        repository.save(Mapper.returnHit(hitDto));
    }

    @Override
    public List<StatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
       if (uris == null || uris.isEmpty()) {
           if (unique) {
              log.info("Get stats by unique ip");
              return repository.findAllStatsByUniqueIp(start, end);
           } else {
               log.info("Get all stats");
               return repository.findAllStats(start, end);
           }
       } else {
           if (unique) {
               log.info("All stats by uri and unique ip");
               return repository.findStatsByUrisByUniqueIp(start, end, uris);
           } else {
               log.info("Get all stats by uris");
               return repository.findStatsByUris(start, end, uris);
           }
       }
    }
}
