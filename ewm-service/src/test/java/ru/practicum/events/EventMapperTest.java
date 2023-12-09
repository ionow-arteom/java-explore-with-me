package ru.practicum.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.categories.Category;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.EventNew;
import ru.practicum.events.dto.EventShort;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.util.enumerated.State.PENDING;

class EventMapperTest {

    @Test
    @DisplayName("Convert EventNew to Event")
    void toEvent() {
        EventNew eventNew = EventNew.builder()
                .annotation("Annotation")
                .description("Description")
                .eventDate(LocalDateTime.now())
                .paid(true)
                .participantLimit(100L)
                .requestModeration(true)
                .title("Title")
                .build();
        Category category = new Category();
        Location location = new Location();
        User user = new User();

        Event event = EventMapper.toEvent(eventNew, category, location, user);

        assertEquals(eventNew.getAnnotation(), event.getAnnotation());
        assertEquals(eventNew.getDescription(), event.getDescription());
        assertEquals(eventNew.getEventDate(), event.getEventDate());
        assertEquals(eventNew.getPaid(), event.getPaid());
        assertEquals(eventNew.getParticipantLimit(), event.getParticipantLimit());
        assertEquals(eventNew.getRequestModeration(), event.getRequestModeration());
        assertEquals(eventNew.getTitle(), event.getTitle());
        assertEquals(PENDING, event.getState());
        assertEquals(0L, event.getViews());
        assertEquals(0L, event.getConfirmedRequests());
    }

    @Test
    @DisplayName("Convert Event to EventFull DTO")
    void toEventFullDto() {
        Event event = createSampleEvent();

        EventFull eventFullDto = EventMapper.toEventFullDto(event);

        assertEquals(event.getAnnotation(), eventFullDto.getAnnotation());
        assertEquals(event.getDescription(), eventFullDto.getDescription());
        assertEquals(event.getEventDate(), eventFullDto.getEventDate());
        assertEquals(event.getId(), eventFullDto.getId());
        assertEquals(event.getPaid(), eventFullDto.getPaid());
        assertEquals(event.getParticipantLimit(), eventFullDto.getParticipantLimit());
        assertEquals(event.getPublishedOn(), eventFullDto.getPublishedOn());
        assertEquals(event.getRequestModeration(), eventFullDto.getRequestModeration());
        assertEquals(event.getState(), eventFullDto.getState());
        assertEquals(event.getTitle(), eventFullDto.getTitle());
        assertEquals(event.getViews(), eventFullDto.getViews());
    }

    @Test
    @DisplayName("Convert Event to EventShort DTO")
    void toEventShortDto() {
        Event event = createSampleEvent();

        EventShort eventShortDto = EventMapper.toEventShortDto(event);

        assertEquals(event.getAnnotation(), eventShortDto.getAnnotation());
        assertEquals(event.getCategory().getId(), eventShortDto.getCategory().getId());
        assertEquals(event.getConfirmedRequests(), eventShortDto.getConfirmedRequests());
        assertEquals(event.getEventDate(), eventShortDto.getEventDate());
        assertEquals(event.getId(), eventShortDto.getId());
        assertEquals(event.getPaid(), eventShortDto.getPaid());
        assertEquals(event.getTitle(), eventShortDto.getTitle());
        assertEquals(event.getViews(), eventShortDto.getViews());
    }

    private Event createSampleEvent() {
        return Event.builder()
                .annotation("Annotation")
                .category(new Category())
                .description("Description")
                .eventDate(LocalDateTime.now())
                .initiator(new User())
                .location(new Location())
                .paid(true)
                .participantLimit(100L)
                .requestModeration(true)
                .createdOn(LocalDateTime.now())
                .views(0L)
                .state(PENDING)
                .confirmedRequests(0L)
                .title("Title")
                .build();
    }
}