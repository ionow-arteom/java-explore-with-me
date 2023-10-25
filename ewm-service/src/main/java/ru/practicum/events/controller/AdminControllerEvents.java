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
    public List<EventFull> getEventsByAdmin(@RequestParam(required = false, name = "users") List<Long> users,
                                            @RequestParam(required = false, name = "states") List<String> states,
                                            @RequestParam(required = false, name = "categories") List<Long> categories,
                                            @RequestParam(required = false, name = "rangeStart") String rangeStart,
                                            @RequestParam(required = false, name = "rangeEnd") String rangeEnd,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Admin is fetching events with parameters: users={}, states={}, categories={}, rangeStart={}, rangeEnd={}, from={}, size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFull updateEventByAdmin(
            @Valid @RequestBody EventUpdate eventUpdate,
            @PathVariable Long eventId) {

        log.info("Admin is updating Event with ID: {}", eventId);
        return eventService.updateEventByAdmin(eventUpdate, eventId);
    }
}