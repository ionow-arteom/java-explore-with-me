package ru.practicum.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.events.EventRepository;
import ru.practicum.util.UnionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UnionService unionService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Sports");
        categoryDto = new CategoryDto(1L, "Sports");
    }

    @Test
    @DisplayName("Add a new category and return it")
    void addCategoryTest() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryDto result = categoryService.add(categoryDto);
        assertEquals(categoryDto.getName(), result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Update an existing category")
    void updateCategoryTest() {
        when(unionService.getCategoryOrNotFound(category.getId())).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryDto result = categoryService.update(categoryDto, category.getId());
        assertEquals(categoryDto.getName(), result.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    @DisplayName("Delete a category when it's not used in any events")
    void deleteCategoryTest() {
        when(eventRepository.findByCategoryId(category.getId())).thenReturn(Collections.emptyList());
        doNothing().when(categoryRepository).deleteById(category.getId());
        categoryService.delete(category.getId());
        verify(categoryRepository).deleteById(category.getId());
    }

    @Test
    @DisplayName("Get a list of categories with pagination")
    void getListCategoriesTest() {
        Page<Category> page = new PageImpl<>(Collections.singletonList(category));
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(page);
        List<CategoryDto> results = categoryService.getList(0, 1);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(categoryDto.getName(), results.get(0).getName());
        verify(categoryRepository).findAll(any(PageRequest.class));
    }

    @Test
    @DisplayName("Get a category by its ID")
    void getCategoryByIdTest() {
        when(unionService.getCategoryOrNotFound(category.getId())).thenReturn(category);
        CategoryDto result = categoryService.getById(category.getId());
        assertNotNull(result);
        assertEquals(categoryDto.getName(), result.getName());
        verify(unionService).getCategoryOrNotFound(category.getId());
    }
}