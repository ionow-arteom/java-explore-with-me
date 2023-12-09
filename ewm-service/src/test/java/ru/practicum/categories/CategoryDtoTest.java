package ru.practicum.categories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.categories.dto.CategoryDto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDtoTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("CategoryDto should have valid name")
    void whenNameIsValid_thenNoConstraintViolations() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("Valid Category Name")
                .build();

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(categoryDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("CategoryDto should not validate with blank name")
    void whenNameIsBlank_thenConstraintViolationOccurs() {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name(" ")
                .build();

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(categoryDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The name field shouldn't be blank or only spaces.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("CategoryDto should not validate with name longer than 50 characters")
    void whenNameIsTooLong_thenConstraintViolationOccurs() {
        String name = "This name is way too long and should cause a constraint violation to occur";
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name(name)
                .build();

        Set<ConstraintViolation<CategoryDto>> violations = validator.validate(categoryDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("The name should have a maximum length of 50 characters.", violations.iterator().next().getMessage());
    }
}