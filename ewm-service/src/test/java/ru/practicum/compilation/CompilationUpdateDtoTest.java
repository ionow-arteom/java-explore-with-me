package ru.practicum.compilation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.compilation.dto.CompilationUpdateDto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CompilationUpdateDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("CompilationUpdateDto title should adhere to size constraints")
    void titleSizeConstraint() {
        String tooLongTitle = "a".repeat(51);
        CompilationUpdateDto dto = CompilationUpdateDto.builder()
                .title(tooLongTitle)
                .pinned(true)
                .events(Set.of(1L, 2L))
                .build();

        Set<ConstraintViolation<CompilationUpdateDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<CompilationUpdateDto> violation = violations.iterator().next();
        assertEquals("title", violation.getPropertyPath().toString());
        assertFalse(violation.getMessage().contains("size must be between 1 and 50"));
    }

    @Test
    @DisplayName("CompilationUpdateDto should accept valid title")
    void validTitle() {
        CompilationUpdateDto dto = CompilationUpdateDto.builder()
                .title("Valid Title")
                .pinned(true)
                .events(Set.of(1L, 2L))
                .build();

        Set<ConstraintViolation<CompilationUpdateDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

}