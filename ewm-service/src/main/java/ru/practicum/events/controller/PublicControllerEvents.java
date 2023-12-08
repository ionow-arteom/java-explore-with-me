package ru.practicum.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.EventService;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.EventShort;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicControllerEvents {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShort> getEventsByPublic(@RequestParam(required = false, name = "text") String text,
                                              @RequestParam(required = false, name = "categories") List<Long> categories,
                                              @RequestParam(required = false, name = "paid") Boolean paid,
                                              @RequestParam(required = false, name = "rangeStart") String rangeStart,
                                              @RequestParam(required = false, name = "rangeEnd") String rangeEnd,
                                              @RequestParam(required = false, defaultValue = "false", name = "onlyAvailable") Boolean onlyAvailable,
                                              @RequestParam(required = false, name = "sort") String sort,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                              HttpServletRequest request) {
        String uri = getUriFromRequest(request);
        String ip = getIpFromRequest(request);
        log.info("Fetching public events (URI: {}, IP: {}) with params: text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}",
                uri, ip, text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventService.getEventsByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, uri, ip);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFull getEventById(@PathVariable Long id, HttpServletRequest request) {
        String uri = getUriFromRequest(request);
        String ip = getIpFromRequest(request);
        log.info("Fetching details for Event ID {} (URI: {}, IP: {})", id, uri, ip);
        return eventService.getEventById(id, uri, ip);
    }

    private String getUriFromRequest(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private String getIpFromRequest(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}