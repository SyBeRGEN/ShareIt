package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InvalidStatusExceptionTest {
    @Test
    void testConstructor() {
        InvalidStatusException actualInvalidStatusException = new InvalidStatusException("An error occurred");
        assertNull(actualInvalidStatusException.getCause());
        assertEquals(0, actualInvalidStatusException.getSuppressed().length);
        assertEquals("An error occurred", actualInvalidStatusException.getMessage());
        assertEquals("An error occurred", actualInvalidStatusException.getLocalizedMessage());
    }
}