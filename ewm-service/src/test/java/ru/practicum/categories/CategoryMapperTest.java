package ru.practicum.categories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.categories.dto.CategoryDto;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryMapperTest {

    @Test
    @DisplayName("Convert Category to CategoryDto")
    void toDtoTest() {
        Category category = new Category(1L, "Music");

        CategoryDto result = CategoryMapper.toDto(category);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
    }

    @Test
    @DisplayName("Convert CategoryDto to Category")
    void fromDtoTest() {
        CategoryDto categoryDto = new CategoryDto(1L, "Music");

        Category result = CategoryMapper.fromDto(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto.getId(), result.getId());
        assertEquals(categoryDto.getName(), result.getName());
    }

    @Test
    @DisplayName("Convert Iterable of Category to List of CategoryDto")
    void toDtoListTest() {
        Category category1 = new Category(1L, "Music");
        Category category2 = new Category(2L, "Art");
        Iterable<Category> categories = Arrays.asList(category1, category2);

        List<CategoryDto> resultList = CategoryMapper.toDtoList(categories);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(category1.getName(), resultList.get(0).getName());
        assertEquals(category2.getName(), resultList.get(1).getName());
    }
}