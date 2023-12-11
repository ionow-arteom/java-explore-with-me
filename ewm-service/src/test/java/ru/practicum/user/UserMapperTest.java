package ru.practicum.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    @DisplayName("Convert User to UserDto")
    void testToUserDto() {
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .name("User Name")
                .build();

        UserDto userDto = UserMapper.toUserDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getName(), userDto.getName());
    }

    @Test
    @DisplayName("Convert User to UserShortDto")
    void testToUserShortDto() {
        User user = User.builder()
                .id(1L)
                .name("User Name")
                .build();

        UserShortDto userShortDto = UserMapper.toUserShortDto(user);

        assertNotNull(userShortDto);
        assertEquals(user.getId(), userShortDto.getId());
        assertEquals(user.getName(), userShortDto.getName());
    }

    @Test
    @DisplayName("Convert UserDto to User")
    void testToUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("user@example.com")
                .name("User Name")
                .build();

        User user = UserMapper.toUser(userDto);

        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getName(), user.getName());
    }

    @Test
    @DisplayName("Convert Iterable of Users to List of UserDto")
    void testToUserDtoList() {
        Iterable<User> users = Arrays.asList(
                User.builder().id(1L).name("User One").email("user1@example.com").build(),
                User.builder().id(2L).name("User Two").email("user2@example.com").build()
        );

        List<UserDto> userDtos = UserMapper.toUserDtoList(users);

        assertNotNull(userDtos);
        assertEquals(2, userDtos.size());
        assertEquals(users.iterator().next().getId(), userDtos.get(0).getId());
        assertEquals(users.iterator().next().getEmail(), userDtos.get(0).getEmail());
    }
}