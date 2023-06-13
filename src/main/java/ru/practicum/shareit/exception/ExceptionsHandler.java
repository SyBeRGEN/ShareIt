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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundExceptionHandler(NotFoundException exception) {
        log.error("Не найдено: ", exception);
        return Map.of(
                "error", "Не найдено: ",
                "errorMessage", exception.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotValidExceptionHandler(NotValidException exception) {
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
    public Map<String, String> handleNotValidExceptionHandler(AuthorizationExcpetion exception) {
        log.error("Ошибка: ", exception);
        return Map.of(
                "error", "Ошибка: ",
                "errorMessage", exception.getMessage()
        );
    }
}
