package ru.practicum.compilation;

import lombok.experimental.UtilityClass;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.events.EventMapper;
import ru.practicum.events.dto.EventShort;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@UtilityClass
public class CompilationMapper {

    public CompilationDto returnCompilationDto(Compilation compilation) {
        Set<EventShort> eventShortDtoSet = new HashSet<>(EventMapper.toEventShortDtoList(compilation.getEvents()));
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(eventShortDtoSet)
                .build();
    }

    public Compilation returnCompilation(CompilationNewDto compilationNewDto) {
        return Compilation.builder()
                .title(compilationNewDto.getTitle())
                .pinned(compilationNewDto.getPinned())
                .build();
    }

    public Set<CompilationDto> returnCompilationDtoSet(Iterable<Compilation> compilations) {
        Set<CompilationDto> result = new LinkedHashSet<>();
        for (Compilation compilation : compilations) {
            result.add(returnCompilationDto(compilation));
        }
        return result;
    }
}