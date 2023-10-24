package ru.practicum.compilation;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.compilation.dto.CompilationUpdateDto;

import java.util.List;

public interface CompilationService {

    /**
     * Adds a new compilation.
     * @param compilationData Data for the new compilation.
     * @return The created compilation DTO.
     */
    CompilationDto addCompilation(CompilationNewDto compilationData);

    /**
     * Deletes a compilation by its ID.
     * @param compilationId ID of the compilation to delete.
     */
    void deleteCompilation(Long compilationId);

    /**
     * Updates an existing compilation.
     * @param compilationId ID of the compilation to update.
     * @param updateData Data to update the compilation with.
     * @return The updated compilation DTO.
     */
    CompilationDto updateCompilation(Long compilationId, CompilationUpdateDto updateData);

    /**
     * Retrieves a list of compilations based on the provided criteria.
     * @param pinned Whether to retrieve pinned compilations.
     * @param from The starting index.
     * @param size The number of compilations to retrieve.
     * @return A list of compilation DTOs.
     */
    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Retrieves a compilation by its ID.
     * @param compilationId ID of the compilation to retrieve.
     * @return The compilation DTO.
     */
    CompilationDto getCompilationById(Long compilationId);
}