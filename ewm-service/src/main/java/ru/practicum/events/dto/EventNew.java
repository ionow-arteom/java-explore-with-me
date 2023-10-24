package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.Setter;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventNew {

    @NotBlank(message = "Please provide a valid annotation. It cannot be empty or just spaces.")
    @Size(min = 20, max = 2000, message = "Annotation length should be between 20 and 2000 characters.")
    private String annotation;

    @NotNull(message = "Please provide a category for the event.")
    private Long category;

    @NotBlank(message = "Please provide a description. It cannot be empty or just spaces.")
    @Size(min = 20, max = 7000, message = "Description length should be between 20 and 7000 characters.")
    private String description;

    @NotNull(message = "Please specify the date of the event.")
    @FutureOrPresent(message = "The event date should be in the present or future.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime eventDate;

    @NotNull(message = "Location details are required.")
    private LocationDto location;

    @NotNull(message = "Please specify if the event is paid or not.")
    @Builder.Default
    private Boolean paid = false;

    @Builder.Default
    private Long participantLimit = 0L;

    @Builder.Default
    private Boolean requestModeration = true;

    @NotBlank(message = "Please provide a title for the event. It cannot be empty or just spaces.")
    @Size(min = 3, max = 120, message = "Title length should be between 3 and 120 characters.")
    private String title;
}