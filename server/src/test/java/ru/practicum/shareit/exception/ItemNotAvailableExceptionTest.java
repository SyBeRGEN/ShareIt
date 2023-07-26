package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ItemNotAvailableExceptionTest {
    @Test
    void testConstructor() {
        ItemNotAvailableException actualItemNotAvailableException = new ItemNotAvailableException("An error occurred");
        assertNull(actualItemNotAvailableException.getCause());
        assertEquals(0, actualItemNotAvailableException.getSuppressed().length);
        assertEquals("An error occurred", actualItemNotAvailableException.getMessage());
        assertEquals("An error occurred", actualItemNotAvailableException.getLocalizedMessage());
    }
}