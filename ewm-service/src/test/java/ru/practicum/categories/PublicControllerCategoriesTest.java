package ru.practicum.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.categories.controller.PublicControllerCategories;
import ru.practicum.categories.dto.CategoryDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PublicControllerCategoriesTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private PublicControllerCategories controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get categories list")
    void getCategories() {
        List<CategoryDto> categories = Arrays.asList(new CategoryDto(), new CategoryDto());
        when(categoryService.getList(anyInt(), anyInt())).thenReturn(categories);

        List<CategoryDto> result = controller.getCategories(0, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoryService).getList(0, 10);
    }

    @Test
    @DisplayName("Get category by ID")
    void getCategory() {
        Long catId = 1L;
        CategoryDto category = new CategoryDto();
        when(categoryService.getById(catId)).thenReturn(category);

        CategoryDto result = controller.getCategory(catId);

        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryService).getById(catId);
    }
}