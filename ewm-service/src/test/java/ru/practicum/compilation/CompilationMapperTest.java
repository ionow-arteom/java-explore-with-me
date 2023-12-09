package ru.practicum.compilation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompilationMapperTest {

    @Test
    @DisplayName("returnCompilationDto should map Compilation to CompilationDto")
    void returnCompilationDtoTest() {
        Compilation compilation = new Compilation();
        compilation.setId(1L);
        compilation.setTitle("Compilation Title");
        compilation.setPinned(true);
        compilation.setEvents(new HashSet<>());

        CompilationDto dto = CompilationMapper.returnCompilationDto(compilation);

        assertEquals(compilation.getId(), dto.getId());
        assertEquals(compilation.getTitle(), dto.getTitle());
        assertEquals(compilation.getPinned(), dto.getPinned());
        assertTrue(dto.getEvents().isEmpty());
    }

    @Test
    @DisplayName("returnCompilation should map CompilationNewDto to Compilation")
    void returnCompilationTest() {
        // Setup dummy data
        CompilationNewDto newDto = CompilationNewDto.builder()
                .title("New Compilation")
                .pinned(true)
                .events(Collections.emptySet())
                .build();

        Compilation compilation = CompilationMapper.returnCompilation(newDto);

        assertEquals(newDto.getTitle(), compilation.getTitle());
        assertEquals(newDto.getPinned(), compilation.getPinned());
    }

    @Test
    @DisplayName("returnCompilationDtoSet should map Iterable<Compilation> to Set<CompilationDto>")
    void returnCompilationDtoSetTest() {
        Compilation compilation1 = new Compilation();
        compilation1.setId(1L);
        compilation1.setTitle("Compilation 1");
        compilation1.setPinned(true);
        compilation1.setEvents(new HashSet<>());

        Compilation compilation2 = new Compilation();
        compilation2.setId(2L);
        compilation2.setTitle("Compilation 2");
        compilation2.setPinned(false);
        compilation2.setEvents(new HashSet<>());

        Set<Compilation> compilations = new HashSet<>();
        compilations.add(compilation1);
        compilations.add(compilation2);

        Set<CompilationDto> dtoSet = CompilationMapper.returnCompilationDtoSet(compilations);

        assertEquals(2, dtoSet.size());
        assertTrue(dtoSet.stream().anyMatch(dto -> dto.getId().equals(1L) && dto.getTitle().equals("Compilation 1")));
        assertTrue(dtoSet.stream().anyMatch(dto -> dto.getId().equals(2L) && dto.getTitle().equals("Compilation 2")));
    }
}