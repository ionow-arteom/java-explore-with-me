package ru.practicum.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminControllerCategories {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logEntry("createCategory", categoryDto.getName());
        CategoryDto createdCategory = categoryService.add(categoryDto);
        logExit("createCategory", createdCategory.getName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> modifyCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable Long catId) {
        logEntry("modifyCategory", String.format("ID %d - %s", catId, categoryDto.getName()));
        CategoryDto updatedCategory = categoryService.update(categoryDto, catId);
        logExit("modifyCategory", String.format("ID %d - %s", catId, updatedCategory.getName()));
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> removeCategory(@PathVariable Long catId) {
        logEntry("removeCategory", String.valueOf(catId));
        categoryService.delete(catId);
        logExit("removeCategory", String.valueOf(catId));
        return ResponseEntity.noContent().build();
    }

    private void logEntry(String method, String detail) {
        log.info("Start {} - details: {}", method, detail);
    }

    private void logExit(String method, String detail) {
        log.info("End {} - details: {}", method, detail);
    }
}