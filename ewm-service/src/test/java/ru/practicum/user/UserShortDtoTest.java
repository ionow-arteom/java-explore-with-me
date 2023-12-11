package ru.practicum.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.user.dto.UserShortDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserShortDtoTest {

    @Test
    @DisplayName("Create UserShortDto: Should Create an Instance")
    void createUserShortDto() {
        UserShortDto userShortDto = new UserShortDto();

        assertNotNull(userShortDto);
    }

    @Test
    @DisplayName("Set and Get UserShortDto Properties: Should Set and Get Properties Correctly")
    void setAndGetUserShortDtoProperties() {
        UserShortDto userShortDto = new UserShortDto();

        userShortDto.setId(1L);
        userShortDto.setName("John");

        assertEquals(1L, userShortDto.getId());
        assertEquals("John", userShortDto.getName());
    }
}