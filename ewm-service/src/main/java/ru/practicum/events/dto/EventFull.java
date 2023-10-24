package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.Setter;
import lombok.Getter;
import ru.practicum.categories.CategoryDto;
import ru.practicum.user.UserShortDto;
import ru.practicum.util.enumerated.State;

import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFull {

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime eventDate;

    private Long id;

    private UserShortDto initiator;

    private LocationDto location;

    private Boolean paid;

    private Long participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Long views;
}