package ru.practicum.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.events.controller.PrivateControllerEvents;
import ru.practicum.events.dto.*;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.events.dto.RequestUpdate;
import ru.practicum.events.dto.RequestUpdateResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PrivateControllerEventsTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private PrivateControllerEvents controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create event successfully")
    void createEvent() {
        EventNew eventNew = new EventNew();
        EventFull eventFull = new EventFull();
        Long userId = 1L;

        when(eventService.addEvent(userId, eventNew)).thenReturn(eventFull);

        EventFull result = controller.createEvent(eventNew, userId);

        assertEquals(eventFull, result);
        verify(eventService).addEvent(userId, eventNew);
    }

    @Test
    @DisplayName("List events by user")
    void listEventsByUser() {
        List<EventShort> eventShortList = Arrays.asList(new EventShort(), new EventShort());
        Long userId = 1L;
        Integer from = 0;
        Integer size = 10;

        when(eventService.getAllEventsByUserId(userId, from, size)).thenReturn(eventShortList);

        List<EventShort> result = controller.listEventsByUser(userId, from, size);

        assertEquals(eventShortList, result);
        verify(eventService).getAllEventsByUserId(userId, from, size);
    }

    @Test
    @DisplayName("Get event details")
    void getEventDetails() {
        EventFull eventFull = new EventFull();
        Long userId = 1L;
        Long eventId = 2L;

        when(eventService.getUserEventById(userId, eventId)).thenReturn(eventFull);

        EventFull result = controller.getEventDetails(userId, eventId);

        assertEquals(eventFull, result);
        verify(eventService).getUserEventById(userId, eventId);
    }

    @Test
    @DisplayName("Update event")
    void updateEvent() {
        EventUpdate eventUpdate = new EventUpdate();
        EventFull eventFull = new EventFull();
        Long userId = 1L;
        Long eventId = 2L;

        when(eventService.updateEventByUserId(eventUpdate, userId, eventId)).thenReturn(eventFull);

        EventFull result = controller.updateEvent(eventUpdate, userId, eventId);

        assertEquals(eventFull, result);
        verify(eventService).updateEventByUserId(eventUpdate, userId, eventId);
    }

    @Test
    @DisplayName("List event requests")
    void listEventRequests() {
        List<RequestDto> requestDtoList = Arrays.asList(new RequestDto(), new RequestDto());
        Long userId = 1L;
        Long eventId = 2L;

        when(eventService.getRequestsForEventIdByUserId(userId, eventId)).thenReturn(requestDtoList);

        List<RequestDto> result = controller.listEventRequests(userId, eventId);

        assertEquals(requestDtoList, result);
        verify(eventService).getRequestsForEventIdByUserId(userId, eventId);
    }

    @Test
    @DisplayName("Modify event requests")
    void modifyEventRequests() {
        RequestUpdate requestUpdate = new RequestUpdate();
        RequestUpdateResult requestUpdateResult = new RequestUpdateResult();
        Long userId = 1L;
        Long eventId = 2L;

        when(eventService.updateStatusRequestsForEventIdByUserId(requestUpdate, userId, eventId)).thenReturn(requestUpdateResult);

        RequestUpdateResult result = controller.modifyEventRequests(userId, eventId, requestUpdate);

        assertEquals(requestUpdateResult, result);
        verify(eventService).updateStatusRequestsForEventIdByUserId(requestUpdate, userId, eventId);
    }
}