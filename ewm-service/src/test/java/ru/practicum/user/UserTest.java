package ru.practicum.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("John")
                .email("john@example.com")
                .build();
    }

    @Test
    @DisplayName("Create User: Should Create a User Successfully")
    void createUser() {
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    @DisplayName("Create User with Null Name: Should Throw DataIntegrityViolationException")
    void createUserWithNullName() {
        user.setName(null);
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    @DisplayName("Create User with Duplicate Email: Should Throw DataIntegrityViolationException")
    void createUserWithDuplicateEmail() {
        userRepository.save(user);
        User duplicateUser = User.builder()
                .name("Jane")
                .email("john@example.com")
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(duplicateUser));
    }
}