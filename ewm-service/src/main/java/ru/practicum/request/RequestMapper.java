package ru.practicum.request;

import lombok.experimental.UtilityClass;
import ru.practicum.events.model.Event;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.user.User;
import ru.practicum.util.enumerated.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RequestMapper {
    public RequestDto mapToRequestDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }

    public Request mapToRequest(RequestDto requestDto, Event event, User user) {
        return Request.builder()
                .id(requestDto.getId())
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(Status.PENDING)
                .build();
    }

    public List<RequestDto> mapToRequestDtoList(Iterable<Request> requests) {
        List<RequestDto> result = new ArrayList<>();
        for (Request request : requests) {
            result.add(mapToRequestDto(request));
        }
        return result;
    }
}