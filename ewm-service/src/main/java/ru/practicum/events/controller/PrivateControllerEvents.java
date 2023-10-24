package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.EventService;
import ru.practicum.events.dto.*;
import ru.practicum.request.RequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateControllerEvents {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFull addEvent(@Valid @RequestBody EventNew eventNew,
                                 @PathVariable Long userId) {

        log.info("User with ID {} is adding an event with annotation: {}", userId, eventNew.getAnnotation());
        return eventService.addEvent(userId, eventNew);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShort> getAllEventsByUserId(@PathVariable Long userId,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {

        log.info("Fetching events for User with ID {}. Pagination details: from={}, size={}", userId, from, size);
        return eventService.getAllEventsByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFull getUserEventById(@PathVariable Long userId,
                                      @PathVariable Long eventId) {

        log.info("Fetching Event with ID {} for User with ID {}", eventId, userId);
        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFull updateEventByUserId(@Valid @RequestBody EventUpdate eventUpdate,
                                            @PathVariable Long userId,
                                            @PathVariable Long eventId) {

        log.info("User with ID {} is updating Event with ID {}. Annotation: {}", userId, eventId, eventUpdate.getAnnotation());
        return eventService.updateEventByUserId(eventUpdate, userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDto> getRequestsForEventIdByUserId(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {

        log.info("Fetching all requests for Event with ID {} by User with ID {}", eventId, userId);
        return eventService.getRequestsForEventIdByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public RequestUpdateResult updateStatusRequestsForEventIdByUserId(@PathVariable Long userId,
                                                                      @PathVariable Long eventId,
                                                                      @Valid @RequestBody RequestUpdate requestDto) {

        log.info("Updating status request for Event with ID {} by User with ID {}", eventId, userId);
        return eventService.updateStatusRequestsForEventIdByUserId(requestDto, userId, eventId);
    }
}