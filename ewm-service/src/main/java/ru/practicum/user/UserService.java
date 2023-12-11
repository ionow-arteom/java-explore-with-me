package ru.practicum.user;

import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * Adds a new user to the system.
     * @param userDetails details of the user to be added.
     * @return details of the added user.
     */
    UserDto addUser(UserDto userDetails);

    /**
     * Deletes a user from the system.
     * @param id unique identifier of the user to be deleted.
     */
    void deleteUser(long id);

    void setAllowSubscriptions(Long userId, boolean allow);

    /**
     * Retrieves a list of users based on given criteria
     * @param userIds list of user ids to filter. Can be null for no filtering.
     * @param offset starting index for pagination.
     * @param limit maximum number of users to be returned.
     * @return list of users matching the criteria.
     */
    List<UserDto> getUsers(List<Long> userIds, Integer offset, Integer limit);
}