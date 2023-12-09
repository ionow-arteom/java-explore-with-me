package ru.practicum.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.events.model.Event;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.user.User;
import ru.practicum.util.enumerated.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestMapperTest {

    @Test
    @DisplayName("Map Request to RequestDto: Should Convert Correctly")
    void mapToRequestDtoTest() {
        Request request = createSampleRequest();

        RequestDto requestDto = RequestMapper.mapToRequestDto(request);

        assertNotNull(requestDto);
        assertEquals(request.getId(), requestDto.getId());
        assertEquals(request.getEvent().getId(), requestDto.getEvent());
        assertEquals(request.getRequester().getId(), requestDto.getRequester());
        assertEquals(request.getStatus(), requestDto.getStatus());
    }

    @Test
    @DisplayName("Map RequestDto to Request: Should Convert Correctly")
    void mapToRequestTest() {
        RequestDto requestDto = createSampleRequestDto();
        Event event = new Event();
        User user = new User();

        Request request = RequestMapper.mapToRequest(requestDto, event, user);

        assertNotNull(request);
        assertEquals(requestDto.getId(), request.getId());
        assertSame(event, request.getEvent());
        assertSame(user, request.getRequester());
        assertEquals(Status.PENDING, request.getStatus());
    }

    @Test
    @DisplayName("Map Request Iterable to RequestDto List: Should Convert Correctly")
    void mapToRequestDtoListTest() {
        List<Request> requests = new ArrayList<>();
        requests.add(createSampleRequest());

        List<RequestDto> requestDtos = RequestMapper.mapToRequestDtoList(requests);

        assertNotNull(requestDtos);
        assertFalse(requestDtos.isEmpty());
        assertEquals(requests.size(), requestDtos.size());
        assertEquals(requests.get(0).getId(), requestDtos.get(0).getId());
    }

    private Request createSampleRequest() {
        Event event = new Event();
        event.setId(1L);
        User user = new User();
        user.setId(1L);

        return Request.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(Status.CONFIRMED)
                .build();
    }

    private RequestDto createSampleRequestDto() {
        return RequestDto.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .event(1L)
                .requester(1L)
                .status(Status.CONFIRMED)
                .build();
    }
}