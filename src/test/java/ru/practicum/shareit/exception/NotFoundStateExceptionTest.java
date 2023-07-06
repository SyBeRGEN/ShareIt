package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NotFoundStateExceptionTest {
    @Test
    void testConstructor() {
        NotFoundStateException actualNotFoundStateException = new NotFoundStateException("An error occurred");
        assertNull(actualNotFoundStateException.getCause());
        assertEquals(0, actualNotFoundStateException.getSuppressed().length);
        assertEquals("An error occurred", actualNotFoundStateException.getMessage());
        assertEquals("An error occurred", actualNotFoundStateException.getLocalizedMessage());
    }
}