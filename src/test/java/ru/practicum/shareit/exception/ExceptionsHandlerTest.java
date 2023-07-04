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
    void testHandleAccessExceptionHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleAccessExceptionHandlerResult = exceptionsHandler
                .handleAccessExceptionHandler(new Exception("foo"));
        assertEquals(2, actualHandleAccessExceptionHandlerResult.size());
        assertEquals("Ошибка авторизации: ", actualHandleAccessExceptionHandlerResult.get("error"));
        assertEquals("foo", actualHandleAccessExceptionHandlerResult.get("errorMessage"));
    }

    @Test
    void testHandleInvalidStatusExceptionHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleInvalidStatusExceptionHandlerResult = exceptionsHandler
                .handleInvalidStatusExceptionHandler(new Exception("foo"));
        assertEquals(2, actualHandleInvalidStatusExceptionHandlerResult.size());
        assertEquals("Ошибка статуса: ", actualHandleInvalidStatusExceptionHandlerResult.get("error"));
        assertEquals("foo", actualHandleInvalidStatusExceptionHandlerResult.get("errorMessage"));
    }

    @Test
    void testHandleNotValidExceptionHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleNotValidExceptionHandlerResult = exceptionsHandler
                .handleNotValidExceptionHandler(new AuthorizationException("Not all who wander are lost"));
        assertEquals(2, actualHandleNotValidExceptionHandlerResult.size());
        assertEquals("Not all who wander are lost", actualHandleNotValidExceptionHandlerResult.get("errorMessage"));
        assertEquals("Ошибка: ", actualHandleNotValidExceptionHandlerResult.get("error"));
    }

    @Test
    void testHandleInternalServerErrorHandler() {
        ExceptionsHandler exceptionsHandler = new ExceptionsHandler();
        Map<String, String> actualHandleInternalServerErrorHandlerResult = exceptionsHandler
                .handleInternalServerErrorHandler(new Exception("foo"));
        assertEquals(1, actualHandleInternalServerErrorHandlerResult.size());
        assertEquals("foo", actualHandleInternalServerErrorHandlerResult.get("error"));
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