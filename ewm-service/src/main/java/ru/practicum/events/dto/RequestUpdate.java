package ru.practicum.events.dto;

import lombok.*;
import ru.practicum.util.enumerated.Status;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdate {

    private List<Long> requestIds;

    private Status status;
}
