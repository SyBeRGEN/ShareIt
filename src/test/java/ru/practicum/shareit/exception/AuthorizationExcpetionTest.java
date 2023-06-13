package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AuthorizationExcpetionTest {
    @Test
    void testConstructor() {
        AuthorizationExcpetion actualAuthorizationExcpetion = new AuthorizationExcpetion("Not all who wander are lost");
        assertNull(actualAuthorizationExcpetion.getCause());
        assertEquals(0, actualAuthorizationExcpetion.getSuppressed().length);
        assertEquals("Not all who wander are lost", actualAuthorizationExcpetion.getMessage());
        assertEquals("Not all who wander are lost", actualAuthorizationExcpetion.getLocalizedMessage());
    }
}

