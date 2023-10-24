package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.categories.CategoryDto;
import ru.practicum.user.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventShort {

    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime eventDate;

    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}