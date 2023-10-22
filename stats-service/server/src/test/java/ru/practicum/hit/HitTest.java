package ru.practicum.hit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HitTest {

    @Test
    @DisplayName("Ensure Hit object can be correctly instantiated and fields are set properly")
    public void testHitInstantiation() {
        LocalDateTime now = LocalDateTime.now();

        Hit hit = Hit.builder()
                .id(1L)
                .app("TestApp")
                .uri("/test")
                .ip("127.0.0.1")
                .timestamp(now)
                .build();

        assertEquals(1L, hit.getId());
        assertEquals("TestApp", hit.getApp());
        assertEquals("/test", hit.getUri());
        assertEquals("127.0.0.1", hit.getIp());
        assertEquals(now, hit.getTimestamp());
    }

    @Test
    @DisplayName("Ensure two Hit objects with the same ID are considered equal")
    public void testHitEqualityBasedOnId() {
        Hit hit1 = Hit.builder().id(1L).build();
        Hit hit2 = Hit.builder().id(1L).build();

        assertEquals(hit1, hit2);
    }

    @Test
    @DisplayName("Ensure two Hit objects with different IDs are not considered equal")
    public void testHitInequalityBasedOnId() {
        Hit hit1 = Hit.builder().id(1L).build();
        Hit hit2 = Hit.builder().id(2L).build();

        assertNotEquals(hit1, hit2);
    }
}