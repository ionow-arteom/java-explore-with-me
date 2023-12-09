package ru.practicum.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.events.controller.PublicControllerEvents;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.EventShort;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PublicControllerEventsTest {

    @Mock
    private EventService eventService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PublicControllerEvents controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get events by public")
    void getEventsByPublic() {
        List<EventShort> eventShortList = Arrays.asList(new EventShort(), new EventShort());
        String text = "sample";
        List<Long> categories = Arrays.asList(1L, 2L);
        Boolean paid = true;
        String rangeStart = "2023-01-01T00:00";
        String rangeEnd = "2023-01-31T23:59";
        Boolean onlyAvailable = false;
        String sort = "date";
        Integer from = 0;
        Integer size = 10;
        String uri = "/events";
        String ip = "127.0.0.1";

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getRemoteAddr()).thenReturn(ip);
        when(eventService.getEventsByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, uri, ip)).thenReturn(eventShortList);

        List<EventShort> result = controller.getEventsByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);

        assertEquals(eventShortList, result);
        verify(eventService).getEventsByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, uri, ip);
    }

    @Test
    @DisplayName("Get event by ID")
    void getEventById() {
        EventFull eventFull = new EventFull();
        Long id = 1L;
        String uri = "/events/1";
        String ip = "127.0.0.1";

        when(request.getRequestURI()).thenReturn(uri);
        when(request.getRemoteAddr()).thenReturn(ip);
        when(eventService.getEventById(id, uri, ip)).thenReturn(eventFull);

        EventFull result = controller.getEventById(id, request);

        assertEquals(eventFull, result);
        verify(eventService).getEventById(id, uri, ip);
    }
}