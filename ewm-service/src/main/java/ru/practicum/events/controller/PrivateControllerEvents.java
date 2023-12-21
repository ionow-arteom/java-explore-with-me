package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.EventService;
import ru.practicum.events.dto.*;
import ru.practicum.request.dto.RequestDto;

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
    public EventFull createEvent(@Valid @RequestBody EventNew eventNew,
                                 @PathVariable Long userId) {
        log.info("Creating event for User ID {}: {}", userId, eventNew.getAnnotation());
        return eventService.addEvent(userId, eventNew);
    }

    @GetMapping
    public List<EventShort> listEventsByUser(@PathVariable Long userId,
                                             @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                             @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Listing events for User ID {}. From: {}, Size: {}", userId, from, size);
        return eventService.getAllEventsByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFull getEventDetails(@PathVariable Long userId,
                                     @PathVariable Long eventId) {
        log.info("Retrieving details for Event ID {} by User ID {}", eventId, userId);
        return eventService.getUserEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFull updateEvent(@Valid @RequestBody EventUpdate eventUpdate,
                                 @PathVariable Long userId,
                                 @PathVariable Long eventId) {
        log.info("Updating event ID {} by User ID {}. Annotation: {}", eventId, userId, eventUpdate.getAnnotation());
        return eventService.updateEventByUserId(eventUpdate, userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> listEventRequests(@PathVariable Long userId,
                                              @PathVariable Long eventId) {
        log.info("Listing requests for Event ID {} by User ID {}", eventId, userId);
        return eventService.getRequestsForEventIdByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestUpdateResult modifyEventRequests(@PathVariable Long userId,
                                                   @PathVariable Long eventId,
                                                   @Valid @RequestBody RequestUpdate requestUpdate) {
        log.info("Modifying requests for Event ID {} by User ID {}", eventId, userId);
        return eventService.updateStatusRequestsForEventIdByUserId(requestUpdate, userId, eventId);
    }

    @GetMapping("/subscribed-events")
    public ResponseEntity<?> getEventsFromSubscribedUsers(
            @PathVariable Long userId,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Listing events from subscriptions for User ID {}. From: {}, Size: {}", userId, from, size);
        List<EventShort> eventShorts = eventService.getEventsFromSubscribedUsers(userId, from, size);

        if (eventShorts.isEmpty()) {
            return ResponseEntity.ok("К сожалению, пользователи на которых Вы подписаны, на данный момент не имеют" +
                    " опубликованных актуальных событий =(");
        } else {
            return ResponseEntity.ok(eventShorts);
        }
    }
}