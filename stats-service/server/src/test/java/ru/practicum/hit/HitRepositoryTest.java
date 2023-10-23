package ru.practicum.hit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class HitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HitRepository hitRepository;

    @BeforeEach
    public void setUp() {
        Hit hit1 = new Hit(null, "App1", "/home", "192.168.1.1", LocalDateTime.now());
        Hit hit2 = new Hit(null, "App1", "/home", "192.168.1.2", LocalDateTime.now());
        Hit hit3 = new Hit(null, "App2", "/dashboard", "192.168.1.1", LocalDateTime.now());

        entityManager.persist(hit1);
        entityManager.persist(hit2);
        entityManager.persist(hit3);
    }

    @Test
    @DisplayName("Ensure findAllStatsByUniqueIp returns correct stats")
    public void testFindAllStatsByUniqueIp() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        List<StatsDto> stats = hitRepository.findAllStatsByUniqueIp(start, end);

        assertEquals(2, stats.size());
    }

    @Test
    @DisplayName("Ensure findAllStats returns correct stats")
    public void testFindAllStats() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        List<StatsDto> stats = hitRepository.findAllStats(start, end);

        assertEquals(2, stats.size());
    }

    @Test
    @DisplayName("Ensure findStatsByUrisByUniqueIp returns correct stats for specific URIs")
    public void testFindStatsByUrisByUniqueIp() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<String> uris = Arrays.asList("/home");

        List<StatsDto> stats = hitRepository.findStatsByUrisByUniqueIp(start, end, uris);

        assertEquals(1, stats.size());
    }

    @Test
    @DisplayName("Ensure findStatsByUris returns correct stats for specific URIs")
    public void testFindStatsByUris() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<String> uris = Arrays.asList("/dashboard");

        List<StatsDto> stats = hitRepository.findStatsByUris(start, end, uris);

        assertEquals(1, stats.size());
    }
}

