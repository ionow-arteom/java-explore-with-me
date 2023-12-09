package ru.practicum.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.events.controller.AdminControllerEvents;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.EventUpdate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminControllerEventsTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private AdminControllerEvents controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Fetch events as admin")
    void fetchEvents() {
        List<EventFull> events = Arrays.asList(new EventFull(), new EventFull());
        when(eventService.getEventsByAdmin(anyList(), anyList(), anyList(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(events);

        List<EventFull> result = controller.fetchEvents(null, null, null, null, null, 0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(eventService).getEventsByAdmin(null, null, null, null, null, 0, 10);
    }

    @Test
    @DisplayName("Modify event as admin")
    void modifyEvent() {
        Long eventId = 1L;
        EventUpdate eventUpdate = new EventUpdate();
        EventFull updatedEvent = new EventFull();
        when(eventService.updateEventByAdmin(any(EventUpdate.class), eq(eventId))).thenReturn(updatedEvent);

        EventFull result = controller.modifyEvent(eventUpdate, eventId);

        assertNotNull(result);
        assertEquals(updatedEvent, result);
        verify(eventService).updateEventByAdmin(any(EventUpdate.class), eq(eventId));
    }
}