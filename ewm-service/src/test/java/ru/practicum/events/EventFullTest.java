package ru.practicum.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.util.enumerated.State;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventFullTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Test getters and setters of EventFull")
    void testGettersAndSetters() {
        EventFull event = new EventFull();
        event.setAnnotation("Annotation");
        event.setCategory(new CategoryDto());
        event.setConfirmedRequests(10L);
        event.setCreatedOn(LocalDateTime.now());
        event.setDescription("Description");
        event.setEventDate(LocalDateTime.now());
        event.setId(1L);
        event.setInitiator(new UserShortDto());
        event.setLocation(new LocationDto());
        event.setPaid(true);
        event.setParticipantLimit(100L);
        event.setPublishedOn(LocalDateTime.now());
        event.setRequestModeration(true);
        event.setState(State.PUBLISHED);
        event.setTitle("Title");
        event.setViews(1000L);

        assertEquals("Annotation", event.getAnnotation());
        assertEquals(10L, event.getConfirmedRequests());
        assertEquals("Description", event.getDescription());
        assertEquals(1L, event.getId());
        assertEquals(true, event.getPaid());
        assertEquals(100L, event.getParticipantLimit());
        assertEquals(true, event.getRequestModeration());
        assertEquals(State.PUBLISHED, event.getState());
        assertEquals("Title", event.getTitle());
        assertEquals(1000L, event.getViews());
    }
}