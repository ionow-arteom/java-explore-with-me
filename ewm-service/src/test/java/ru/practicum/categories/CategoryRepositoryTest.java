package ru.practicum.categories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Save a category and then retrieve it by ID")
    void testSaveAndFindById() {
        Category category = new Category();
        category.setName("Education");

        Category savedCategory = categoryRepository.save(category);
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        assertTrue(foundCategory.isPresent());
        assertEquals("Education", foundCategory.get().getName());
    }

    @Test
    @DisplayName("Save a category and then delete it")
    void testSaveAndDelete() {
        Category category = new Category();
        category.setName("Technology");
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.delete(savedCategory);
        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        assertFalse(foundCategory.isPresent());
    }
}
