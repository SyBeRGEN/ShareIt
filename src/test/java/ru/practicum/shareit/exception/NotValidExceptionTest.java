package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NotValidExceptionTest {
    @Test
    void testConstructor() {
        NotValidException actualNotValidException = new NotValidException("An error occurred");
        assertNull(actualNotValidException.getCause());
        assertEquals(0, actualNotValidException.getSuppressed().length);
        assertEquals("An error occurred", actualNotValidException.getMessage());
        assertEquals("An error occurred", actualNotValidException.getLocalizedMessage());
    }
}