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
        User user = UserMapper.toUser(userDto);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (ids == null) {
            return UserMapper.toUserDtoList(userRepository.findAll(pageRequest));
        } else {
            return UserMapper.toUserDtoList(userRepository.findByIdInOrderByIdAsc(ids, pageRequest));
        }
    }

    @Transactional
    @Override
    public void deleteUser(long userId) {
        unionService.getUserOrNotFound(userId);
        userRepository.deleteById(userId);
    }
}