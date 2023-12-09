package ru.practicum.compilation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.compilation.dto.CompilationUpdateDto;
import ru.practicum.events.EventRepository;
import ru.practicum.util.UnionService;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompilationServiceImplTest {

    @Mock
    private CompilationRepository compilationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UnionService unionService;

    @InjectMocks
    private CompilationServiceImpl compilationService;

    private CompilationNewDto compilationNewDto;
    private CompilationUpdateDto compilationUpdateDto;
    private Compilation compilation;

    @BeforeEach
    void setUp() {
        compilationNewDto = CompilationNewDto.builder()
                .title("New Compilation")
                .pinned(true)
                .events(Set.of(1L, 2L))
                .build();

        compilationUpdateDto = CompilationUpdateDto.builder()
                .title("Updated Compilation")
                .events(Set.of(3L, 4L))
                .build();

        compilation = new Compilation();
        compilation.setId(1L);
        compilation.setTitle("Some Compilation");
        compilation.setPinned(false);
    }

    @Test
    void whenUpdateCompilation_thenSuccess() {
        when(compilationRepository.save(any())).thenReturn(compilation);
        when(unionService.getCompilationOrNotFound(anyLong())).thenReturn(compilation);

        CompilationDto result = compilationService.update(1L, compilationUpdateDto);

        assertNotNull(result);
        assertEquals("Updated Compilation", result.getTitle());
        verify(compilationRepository).save(compilation);
    }

    @Test
    void whenDeleteCompilation_thenSuccess() {
        doNothing().when(compilationRepository).deleteById(anyLong());
        when(unionService.getCompilationOrNotFound(anyLong())).thenReturn(compilation);

        compilationService.delete(1L);

        verify(compilationRepository).deleteById(1L);
    }
}