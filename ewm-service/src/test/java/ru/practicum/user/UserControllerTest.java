package ru.practicum.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.user.controller.UserController;
import ru.practicum.user.dto.UserDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add user")
    void addUser() {
        UserDto userDto = new UserDto();
        UserDto newUser = new UserDto();
        newUser.setId(1L);
        when(userService.addUser(any(UserDto.class))).thenReturn(newUser);

        UserDto result = controller.addUser(userDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userService).addUser(any(UserDto.class));
    }

    @Test
    @DisplayName("Get users")
    void getUsers() {
        List<UserDto> users = Arrays.asList(new UserDto(), new UserDto());
        when(userService.getUsers(anyList(), anyInt(), anyInt())).thenReturn(users);

        List<UserDto> result = controller.getUsers(null, 0, 10);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userService).getUsers(null, 0, 10);
    }

    @Test
    @DisplayName("Delete user")
    void deleteUser() {
        Long userId = 1L;

        controller.deleteUser(userId);

        verify(userService).deleteUser(userId);
    }
}