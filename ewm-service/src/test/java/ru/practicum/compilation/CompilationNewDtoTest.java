package ru.practicum.compilation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.compilation.dto.CompilationNewDto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CompilationNewDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("CompilationNewDto should have a title that is not blank")
    void titleShouldNotBeBlank() {
        CompilationNewDto dto = CompilationNewDto.builder()
                .title(" ")
                .pinned(true)
                .events(Set.of(1L, 2L))
                .build();

        Set<ConstraintViolation<CompilationNewDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<CompilationNewDto> violation = violations.iterator().next();
        assertEquals("title", violation.getPropertyPath().toString());
        assertEquals("не должно быть пустым", violation.getMessage());
    }

    @Test
    @DisplayName("CompilationNewDto should have a valid title size")
    void titleShouldHaveValidSize() {
        String invalidTitle = "a".repeat(51);
        CompilationNewDto dto = CompilationNewDto.builder()
                .title(invalidTitle)
                .pinned(true)
                .events(Set.of(1L, 2L))
                .build();

        Set<ConstraintViolation<CompilationNewDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<CompilationNewDto> violation = violations.iterator().next();
        assertEquals("title", violation.getPropertyPath().toString());
        assertFalse(violation.getMessage().contains("size must be between 1 and 50"));
    }

    @Test
    @DisplayName("CompilationNewDto should accept valid titles")
    void validTitle() {
        CompilationNewDto dto = CompilationNewDto.builder()
                .title("Valid Title")
                .pinned(true)
                .events(Set.of(1L, 2L))
                .build();

        Set<ConstraintViolation<CompilationNewDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }
}
