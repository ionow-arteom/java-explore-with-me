package ru.practicum.categories;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/admin/categories")
public class AdminControllerCategories {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Attempting to add a new Category: {}", categoryDto.getName());
        CategoryDto result = categoryService.add(categoryDto);
        log.info("Successfully added Category: {}", result.getName());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable Long catId) {
        log.info("Attempting to update Category with ID {}: {}", catId, categoryDto.getName());
        CategoryDto result = categoryService.update(categoryDto, catId);
        log.info("Successfully updated Category with ID {}: {}", catId, result.getName());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long catId) {
        log.info("Attempting to delete Category with ID: {}", catId);
        categoryService.delete(catId);
        log.info("Successfully deleted Category with ID: {}", catId);
        return ResponseEntity.noContent().build();
    }
}