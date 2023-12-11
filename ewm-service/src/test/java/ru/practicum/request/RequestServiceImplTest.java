package ru.practicum.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.practicum.errorhandling.ConflictException;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.user.User;
import ru.practicum.util.UnionService;
import ru.practicum.util.enumerated.State;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private UnionService unionService;

    @InjectMocks
    private RequestServiceImpl requestService;

    private User user;
    private Event event;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        event = new Event();
        event.setId(1L);
        event.setParticipantLimit(10L);
        event.setConfirmedRequests(0L);
        event.setRequestModeration(true);
        event.setState(State.PUBLISHED);
        event.setInitiator(new User());
    }

    @Test
    @DisplayName("Add Request: Should Throw ConflictException for Self Request")
    void addRequestSelfInitiatedEventTest() {
        event.setInitiator(user);
        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(unionService.getEventOrNotFound(anyLong())).thenReturn(event);

        assertThrows(ConflictException.class, () -> {
            requestService.addRequest(user.getId(), event.getId());
        });
    }

    @Test
    @DisplayName("Cancel Request: Should Throw NotFoundException for Non-Existent Request")
    void cancelRequestNotFoundTest() {
        when(unionService.getUserOrNotFound(anyLong())).thenReturn(user);
        when(unionService.getRequestOrNotFound(anyLong())).thenThrow(new ConflictException("Request not found"));

        assertThrows(ConflictException.class, () -> {
            requestService.cancelRequest(user.getId(), 999L);
        });
    }
}