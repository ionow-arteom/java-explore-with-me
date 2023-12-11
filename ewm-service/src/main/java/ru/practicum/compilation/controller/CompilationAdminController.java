package ru.practicum.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.CompilationService;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.compilation.dto.CompilationUpdateDto;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Valid @RequestBody CompilationNewDto newCompilation) {
        log.info("Request to create a new compilation: {}", newCompilation.getTitle());
        CompilationDto createdCompilation = compilationService.add(newCompilation);
        log.info("Created new compilation with ID: {}", createdCompilation.getId());
        return createdCompilation;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCompilation(@PathVariable Long compId) {
        log.info("Request to delete compilation with ID: {}", compId);
        compilationService.delete(compId);
        log.info("Deleted compilation with ID: {}", compId);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto editCompilation(@PathVariable Long compId,
                                          @Valid @RequestBody CompilationUpdateDto updateCompilation) {
        log.info("Request to update compilation with ID: {}", compId);
        CompilationDto updatedCompilation = compilationService.update(compId, updateCompilation);
        log.info("Updated compilation with ID: {}", compId);
        return updatedCompilation;
    }
}