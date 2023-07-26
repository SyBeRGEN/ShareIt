package ru.practicum.shareit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class NotValidEmailExceptionTest {
    @Test
    void testConstructor() {
        NotValidEmailException actualNotValidEmailException = new NotValidEmailException("An error occurred");
        assertNull(actualNotValidEmailException.getCause());
        assertEquals(0, actualNotValidEmailException.getSuppressed().length);
        assertEquals("An error occurred", actualNotValidEmailException.getMessage());
        assertEquals("An error occurred", actualNotValidEmailException.getLocalizedMessage());
    }
}