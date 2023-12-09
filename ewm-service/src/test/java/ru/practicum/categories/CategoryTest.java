package ru.practicum.categories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("Test Category Lombok annotations are working as expected")
    void testLombokFunctionality() {
        Long id = 1L;
        String name = "Test Category";

        Category category = Category.builder()
                .id(id)
                .name(name)
                .build();

        assertEquals(id, category.getId());
        assertEquals(name, category.getName());

        String updatedName = "Updated Test Category";
        category.setName(updatedName);

        assertEquals(updatedName, category.getName());

        Category sameCategory = new Category(id, updatedName);
        assertEquals(category, sameCategory);
        assertEquals(category.hashCode(), sameCategory.hashCode());

        Category defaultCategory = new Category();
        assertNotNull(defaultCategory);

        Category fullCategory = new Category(id, name);
        assertNotNull(fullCategory);
        assertEquals(id, fullCategory.getId());
        assertEquals(name, fullCategory.getName());
    }
}
