package ru.practicum.user;

import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.dto.UserDto;
import ru.practicum.util.UnionService;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UnionService unionService;

    @Transactional
    @Override
    public UserDto addUser(UserDto userDto) {
        log.info("Creating user: {}", userDto.getName());
        User user = UserMapper.toUser(userDto);
        userRepository.save(user);
        log.info("User created with ID: {}", user.getId());
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest pageRequest = createPageRequest(from, size);
        List<UserDto> users;

        if (ids == null || ids.isEmpty()) {
            log.info("Fetching all users with pagination: Page {}, Size {}", from, size);
            users = UserMapper.toUserDtoList(userRepository.findAll(pageRequest).getContent());
        } else {
            log.info("Fetching users by IDs: {} with pagination: Page {}, Size {}", ids, from, size);
            users = UserMapper.toUserDtoList(userRepository.findByIdInOrderByIdAsc(ids, pageRequest));
        }

        log.info("Fetched {} users", users.size());
        return users;
    }

    private PageRequest createPageRequest(Integer from, Integer size) {
        return PageRequest.of(from / size, size);
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        log.info("Deleting user with ID: {}", userId);
        unionService.getUserOrNotFound(userId);
        userRepository.deleteById(userId);
        log.info("User with ID: {} has been deleted", userId);
    }
}