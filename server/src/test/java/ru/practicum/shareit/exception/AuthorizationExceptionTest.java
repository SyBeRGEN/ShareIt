package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AuthorizationExceptionTest {
    @Test
    void testConstructor() {
        AuthorizationException actualAuthorizationException = new AuthorizationException("Not all who wander are lost");
        assertNull(actualAuthorizationException.getCause());
        assertEquals(0, actualAuthorizationException.getSuppressed().length);
        assertEquals("Not all who wander are lost", actualAuthorizationException.getMessage());
        assertEquals("Not all who wander are lost", actualAuthorizationException.getLocalizedMessage());
    }
}