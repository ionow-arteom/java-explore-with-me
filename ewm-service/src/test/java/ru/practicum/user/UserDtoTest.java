package ru.practicum.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.user.dto.UserDto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("UserDto Validation: Should Pass for Valid Data")
    void whenValidUserDto_thenNoConstraintViolations() {
        UserDto user = new UserDto(null, "John Doe", "john.doe@example.com", true);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("UserDto Validation: Should Fail for Blank Name")
    void whenBlankName_thenConstraintViolation() {
        UserDto user = new UserDto(null, "", "john.doe@example.com", true);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("UserDto Validation: Should Fail for Invalid Email")
    void whenInvalidEmail_thenConstraintViolation() {
        UserDto user = new UserDto(null, "John Doe", "invalid-email", true);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("UserDto Validation: Should Fail for Too Short Name")
    void whenNameTooShort_thenConstraintViolation() {
        UserDto user = new UserDto(null, "J", "john.doe@example.com", true);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }
}