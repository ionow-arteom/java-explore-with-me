package ru.practicum.server.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Hit, Long> {

    @Query(value = "SELECT new ru.practicum.dto.StatsDto(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatsDto> findAllStatsByUniqueIp(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.dto.StatsDto(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<StatsDto> findAllStats(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);

    @Query(value = "SELECT new ru.practicum.dto.StatsDto(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatsDto> findStatsByUrisByUniqueIp(@Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end,
                                             @Param("uris") List<String> uris);

    @Query(value = "SELECT new ru.practicum.dto.StatsDto(h.app, h.uri, COUNT(h.ip)) " +
            "FROM Hit AS h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<StatsDto> findStatsByUris(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);
}

