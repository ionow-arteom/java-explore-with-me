package ru.practicum.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicControllerCategories {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> fetchCategories(
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("Fetching categories: starting from {} with size of {}", from, size);
        List<CategoryDto> categories = categoryService.getList(from, size);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> fetchCategory(@PathVariable Long categoryId) {

        log.info("Fetching category with ID: {}", categoryId);
        CategoryDto category = categoryService.getById(categoryId);
        return ResponseEntity.ok(category);
    }
}

