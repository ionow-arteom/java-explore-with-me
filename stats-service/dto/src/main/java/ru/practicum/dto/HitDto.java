package ru.practicum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.TIME_ORDER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {

    private Long id;

    @NotBlank(message = "app should not be blank")
    private String app;

    @NotBlank(message = "uri should not be blank")
    private String uri;

    @NotBlank(message = "ip should not be blank")
    private String ip;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_ORDER)
    LocalDateTime timestamp;
}