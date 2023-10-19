package ru.practicum.server.hit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
    private final HitService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody HitDto hitDto) {
        log.info("Adding Hit");
        service.add(hitDto);
    }

    @GetMapping("/stats")
    public List<StatsDto> findStats(@RequestParam("start") String start,
                                    @RequestParam("end") String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);

        log.info("Finding Stats");
        return service.findStats(startTime, endTime, uris, unique);
    }
}
