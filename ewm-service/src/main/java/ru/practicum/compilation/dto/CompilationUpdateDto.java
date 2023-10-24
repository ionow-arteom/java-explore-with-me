package ru.practicum.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Builder
public class CompilationUpdateDto {

    private Set<Long> events;

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;
}