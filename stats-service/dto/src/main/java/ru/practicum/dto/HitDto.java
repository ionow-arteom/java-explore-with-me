package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class HitDto {
    private Long id;

    @NotNull(message = "app cannot be null")
    @NotBlank(message = "app cannot consist only of spaces")
    private String app;

    @NotNull(message = "uri cannot be null")
    @NotBlank(message = "uri cannot consist only of spaces")
    private String uri;

    @NotNull(message = "ip cannot be null")
    @NotBlank(message = "ip cannot consist only of spaces")
    private String ip;

    @NotNull(message = "timestamp cannot be null")
    @NotBlank(message = "timestamp cannot consist only of spaces")
    private String timestamp;
}
