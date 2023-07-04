package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccessExceptionTest {
    @Test
    void testConstructor() {
        AccessException actualAccessException = new AccessException("An error occurred");
        assertNull(actualAccessException.getCause());
        assertEquals(0, actualAccessException.getSuppressed().length);
        assertEquals("An error occurred", actualAccessException.getMessage());
        assertEquals("An error occurred", actualAccessException.getLocalizedMessage());
    }
}