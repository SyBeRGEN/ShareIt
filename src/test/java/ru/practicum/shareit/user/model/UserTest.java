package ru.practicum.shareit.user.model;

import org.junit.jupiter.api.Test;

class UserTest {
    User user = new User(0L, "name", "email");

    @Test
    void testSetId() {
        user.setId(0L);
    }

    @Test
    void testSetName() {
        user.setName("name");
    }

    @Test
    void testSetEmail() {
        user.setEmail("email");
    }
}