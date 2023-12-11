package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        log.info("Adding new user: {}", userDto.getName());
        UserDto newUser = userService.addUser(userDto);
        log.info("User added successfully: {}", newUser.getId());
        return newUser;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {

        log.info("Request to fetch users. IDs: {}, Page: {}, Size: {}", ids, from, size);
        List<UserDto> users = userService.getUsers(ids, from, size);
        log.info("Fetched {} users", users.size());
        return users;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") @Positive Long userId) {
        log.info("Request to delete user with ID: {}", userId);
        userService.deleteUser(userId);
        log.info("User with ID: {} deleted successfully", userId);
    }
}