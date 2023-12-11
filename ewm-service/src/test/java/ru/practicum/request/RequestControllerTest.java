package ru.practicum.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.request.controller.RequestController;
import ru.practicum.request.dto.RequestDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestControllerTest {

    @Mock
    private RequestService requestService;

    @InjectMocks
    private RequestController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create request")
    void createRequest() {
        Long userId = 1L;
        Long eventId = 2L;
        RequestDto requestDto = new RequestDto();
        when(requestService.addRequest(userId, eventId)).thenReturn(requestDto);

        RequestDto result = controller.createRequest(userId, eventId);

        assertNotNull(result);
        verify(requestService).addRequest(userId, eventId);
    }

    @Test
    @DisplayName("Get requests by user ID")
    void getRequestsByUserId() {
        Long userId = 1L;
        List<RequestDto> requests = Arrays.asList(new RequestDto(), new RequestDto());
        when(requestService.getRequestsByUserId(userId)).thenReturn(requests);

        List<RequestDto> result = controller.getRequestsByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(requestService).getRequestsByUserId(userId);
    }

    @Test
    @DisplayName("Cancel request")
    void cancelRequest() {
        Long userId = 1L;
        Long requestId = 2L;
        RequestDto requestDto = new RequestDto();
        when(requestService.cancelRequest(userId, requestId)).thenReturn(requestDto);

        RequestDto result = controller.cancelRequest(userId, requestId);

        assertNotNull(result);
        verify(requestService).cancelRequest(userId, requestId);
    }
}