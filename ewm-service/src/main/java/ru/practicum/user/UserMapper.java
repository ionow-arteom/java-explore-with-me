package ru.practicum.user;

import lombok.experimental.UtilityClass;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@UtilityClass
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .allowSubscriptions(user.isAllowSubscriptions())
                .build();
    }

    public UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public User toUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .allowSubscriptions(userDto.isAllowSubscriptions())
                .build();
        return user;
    }

    public List<UserDto> toUserDtoList(Iterable<User> users) {
        return StreamSupport.stream(users.spliterator(), false)
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}