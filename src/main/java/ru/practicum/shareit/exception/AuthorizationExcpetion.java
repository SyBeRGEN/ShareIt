package ru.practicum.shareit.exception;

public class AuthorizationExcpetion extends RuntimeException {
    public AuthorizationExcpetion(String message) {
        super(message);
    }
}
