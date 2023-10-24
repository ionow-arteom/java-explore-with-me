package ru.practicum.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.util.enumerated.Status;

import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    private LocalDateTime created;

    private Long event;

    private Long requester;

    private Status status;
}