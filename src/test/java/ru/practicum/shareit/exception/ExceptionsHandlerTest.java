package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionsHandlerTest {
    @Test
    void testHandleNotFoundExceptionHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleNotFoundExceptionHandlerResult = exceptionsHandler
                .handleNotFoundExceptionHandler(new NotFoundException("An error occurred"));
        assertEquals(2, actualHandleNotFoundExceptionHandlerResult.size());
        assertEquals("An error occurred", actualHandleNotFoundExceptionHandlerResult.get("errorMessage"));
        assertEquals("Не найдено: ", actualHandleNotFoundExceptionHandlerResult.get("error"));
    }

    @Test
    void testHandleNotValidExceptionHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleNotValidExceptionHandlerResult = exceptionsHandler
                .handleNotValidExceptionHandler(new AuthorizationExcpetion("Not all who wander are lost"));
        assertEquals(2, actualHandleNotValidExceptionHandlerResult.size());
        assertEquals("Not all who wander are lost", actualHandleNotValidExceptionHandlerResult.get("errorMessage"));
        assertEquals("Ошибка: ", actualHandleNotValidExceptionHandlerResult.get("error"));
    }

    @Test
    void testHandleNotValidExceptionHandler2() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleNotValidExceptionHandlerResult = exceptionsHandler
                .handleNotValidExceptionHandler(new NotValidException("An error occurred"));
        assertEquals(2, actualHandleNotValidExceptionHandlerResult.size());
        assertEquals("An error occurred", actualHandleNotValidExceptionHandlerResult.get("errorMessage"));
        assertEquals("Ошибка в теле: ", actualHandleNotValidExceptionHandlerResult.get("error"));
    }

    @Test
    void testHandleNotValidEmailExceptionHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleNotValidEmailExceptionHandlerResult = exceptionsHandler
                .handleNotValidEmailExceptionHandler(new NotValidEmailException("An error occurred"));
        assertEquals(1, actualHandleNotValidEmailExceptionHandlerResult.size());
        assertEquals("An error occurred", actualHandleNotValidEmailExceptionHandlerResult.get("error"));
    }
}

