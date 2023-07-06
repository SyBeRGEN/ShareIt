package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InvalidLocalDateTimeExceptionTest {
    @Test
    void testConstructor() {
        InvalidLocalDateTimeException actualInvalidLocalDateTimeException = new InvalidLocalDateTimeException(
                "An error occurred");
        assertNull(actualInvalidLocalDateTimeException.getCause());
        assertEquals(0, actualInvalidLocalDateTimeException.getSuppressed().length);
        assertEquals("An error occurred", actualInvalidLocalDateTimeException.getMessage());
        assertEquals("An error occurred", actualInvalidLocalDateTimeException.getLocalizedMessage());
    }
}