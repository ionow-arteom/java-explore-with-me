package ru.practicum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

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

    @NotBlank(message = "timestamp should not be blank")
    private String timestamp;
}