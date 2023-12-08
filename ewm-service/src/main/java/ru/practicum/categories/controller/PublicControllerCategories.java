package ru.practicum.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.CategoryService;

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
    public List<CategoryDto> listCategories(@PositiveOrZero @RequestParam(defaultValue = "0") Integer offset,
                                            @Positive @RequestParam(defaultValue = "10") Integer limit) {
        log.info("Fetching categories with offset {} and limit {}", offset, limit);
        List<CategoryDto> categories = categoryService.getList(offset, limit);
        log.debug("Fetched {} categories starting from position {}", categories.size(), offset);
        return categories;
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryDetails(@PathVariable Long catId) {
        log.info("Requesting details for category with ID: {}", catId);
        CategoryDto category = categoryService.getById(catId);
        log.debug("Retrieved details for category: {}", category.getName());
        return category;
    }
}