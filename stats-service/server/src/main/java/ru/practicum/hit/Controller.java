package ru.practicum.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static ru.practicum.dto.utilities.Constants.DATE_TIME_FORMATTER;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

    private final HitService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Received request to add hit: {}", hitDto);
        service.add(hitDto);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StatsDto>> retrieveStats(@RequestParam String start,
                                                        @RequestParam String end,
                                                        @RequestParam(required = false) List<String> uris,
                                                        @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        try {
            LocalDateTime startTime = parseDateTime(start);
            LocalDateTime endTime = parseDateTime(end);

            if (endTime.isBefore(startTime)) {
                return ResponseEntity.badRequest().build();
            }

            log.info("Received request to retrieve stats from {} to {}", startTime, endTime);
            List<StatsDto> stats = service.findStats(startTime, endTime, uris, unique);
            return ResponseEntity.ok(stats);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }
}