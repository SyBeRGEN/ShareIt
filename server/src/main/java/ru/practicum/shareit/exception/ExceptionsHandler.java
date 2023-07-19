package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionsHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundExceptionHandler(Exception exception) {
        log.error("Не найдено: ", exception);
        return Map.of(
                "error", "Не найдено: ",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler(AccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleAccessExceptionHandler(Exception exception) {
        log.error("Ошибка авторизации: ", exception);
        return Map.of(
                "error", "Ошибка авторизации: ",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler(InvalidStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidStatusExceptionHandler(Exception exception) {
        log.error("Ошибка статуса: ", exception);
        return Map.of(
                "error", "Ошибка статуса: ",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler({NotValidException.class, ItemNotAvailableException.class, InvalidLocalDateTimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotValidExceptionHandler(Exception exception) {
        log.error("Ошибка в теле: ", exception);
        return Map.of(
                "error", "Ошибка в теле: ",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleNotValidEmailExceptionHandler(NotValidEmailException exception) {
        log.error(exception.getMessage());
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleNotValidExceptionHandler(AuthorizationException exception) {
        log.error("Ошибка: ", exception);
        return Map.of(
                "error", "Ошибка: ",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler(NotFoundStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerErrorHandler(Exception exception) {
        log.error(exception.getMessage());
        return Map.of("error", exception.getMessage());
    }
}
