package ru.practicum.compilation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.compilation.controller.CompilationAdminController;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.compilation.dto.CompilationUpdateDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CompilationAdminControllerTest {

    @Mock
    private CompilationService compilationService;

    @InjectMocks
    private CompilationAdminController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create compilation")
    void createCompilation() {
        CompilationNewDto newCompilation = CompilationNewDto.builder()
                .title("New Compilation")
                .build();
        CompilationDto createdCompilation = CompilationDto.builder()
                .id(1L)
                .title("New Compilation")
                .build();
        when(compilationService.add(any(CompilationNewDto.class))).thenReturn(createdCompilation);

        CompilationDto result = controller.createCompilation(newCompilation);

        assertNotNull(result);
        assertEquals(createdCompilation.getId(), result.getId());
        verify(compilationService).add(any(CompilationNewDto.class));
    }

    @Test
    @DisplayName("Remove compilation")
    void removeCompilation() {
        Long compId = 1L;

        controller.removeCompilation(compId);

        verify(compilationService).delete(compId);
    }

    @Test
    @DisplayName("Edit compilation")
    void editCompilation() {
        Long compId = 1L;
        CompilationUpdateDto updateCompilation = CompilationUpdateDto.builder()
                .title("Updated Compilation")
                .build();
        CompilationDto updatedCompilation = CompilationDto.builder()
                .id(compId)
                .title("Updated Compilation")
                .build();
        when(compilationService.update(eq(compId), any(CompilationUpdateDto.class))).thenReturn(updatedCompilation);

        CompilationDto result = controller.editCompilation(compId, updateCompilation);

        assertNotNull(result);
        assertEquals(updatedCompilation.getId(), result.getId());
        verify(compilationService).update(eq(compId), any(CompilationUpdateDto.class));
    }
}