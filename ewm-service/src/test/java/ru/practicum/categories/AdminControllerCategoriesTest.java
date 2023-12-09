package ru.practicum.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.categories.controller.AdminControllerCategories;
import ru.practicum.categories.dto.CategoryDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminControllerCategoriesTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private AdminControllerCategories controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create category")
    void createCategory() {
        CategoryDto categoryDto = new CategoryDto();
        CategoryDto createdCategory = new CategoryDto();
        when(categoryService.add(any(CategoryDto.class))).thenReturn(createdCategory);

        ResponseEntity<CategoryDto> response = controller.createCategory(categoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdCategory, response.getBody());
        verify(categoryService).add(any(CategoryDto.class));
    }

    @Test
    @DisplayName("Modify category")
    void modifyCategory() {
        Long catId = 1L;
        CategoryDto categoryDto = new CategoryDto();
        CategoryDto updatedCategory = new CategoryDto();
        when(categoryService.update(any(CategoryDto.class), eq(catId))).thenReturn(updatedCategory);

        ResponseEntity<CategoryDto> response = controller.modifyCategory(categoryDto, catId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCategory, response.getBody());
        verify(categoryService).update(any(CategoryDto.class), eq(catId));
    }

    @Test
    @DisplayName("Remove category")
    void removeCategory() {
        Long catId = 1L;

        ResponseEntity<Void> response = controller.removeCategory(catId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService).delete(catId);
    }
}