package ru.practicum.compilation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.compilation.controller.CompilationPublicController;
import ru.practicum.compilation.dto.CompilationDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompilationPublicControllerTest {

    @Mock
    private CompilationService compilationService;

    @InjectMocks
    private CompilationPublicController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get compilations")
    void getCompilations() {
        Boolean pinned = true;
        Integer from = 0;
        Integer size = 10;
        List<CompilationDto> compilations = Arrays.asList(new CompilationDto(), new CompilationDto());
        when(compilationService.getList(pinned, from, size)).thenReturn(compilations);

        List<CompilationDto> result = controller.getCompilations(pinned, from, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(compilationService).getList(pinned, from, size);
    }

    @Test
    @DisplayName("Get compilation by ID")
    void getCompilationById() {
        Long compId = 1L;
        CompilationDto compilation = new CompilationDto();
        when(compilationService.getById(compId)).thenReturn(compilation);

        CompilationDto result = controller.getCompilationById(compId);

        assertNotNull(result);
        assertEquals(compilation, result);
        verify(compilationService).getById(compId);
    }
}