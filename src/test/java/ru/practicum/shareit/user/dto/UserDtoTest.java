package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.Test;

class UserDtoTest {
    private final UserDto userDto = new UserDto(0L, "name", "email");

    @Test
    void testSetId() {
        userDto.setId(0L);
    }

    @Test
    void testSetName() {
        userDto.setName("name");
    }

    @Test
    void testSetEmail() {
        userDto.setEmail("email");
    }
}