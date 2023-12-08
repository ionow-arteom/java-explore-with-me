package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.EventService;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.EventUpdate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminControllerEvents {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFull> fetchEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {

        log.info("Admin requesting events: users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        List<EventFull> events = eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        log.info("Retrieved {} events for admin", events.size());
        return events;
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFull modifyEvent(
            @Valid @RequestBody EventUpdate eventUpdate,
            @PathVariable Long eventId) {

        log.info("Admin updating event with ID: {}", eventId);
        EventFull updatedEvent = eventService.updateEventByAdmin(eventUpdate, eventId);
        log.info("Event with ID: {} updated successfully", eventId);
        return updatedEvent;
    }
}