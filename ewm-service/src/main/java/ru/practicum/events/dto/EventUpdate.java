package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.util.enumerated.StateAction;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdate {

    @Size(min = 20, max = 2000, message = "Annotation length should be between 20 to 2000 characters.")
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, message = "Description length should be between 20 to 7000 characters.")
    private String description;

    @Size(min = 3, max = 120, message = "Title length should be between 3 to 120 characters.")
    private String title;

    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime eventDate;

    private LocationDto location;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
}