package ru.practicum.shareit.booking.utils;

import ru.practicum.shareit.exception.NotFoundStateException;

public enum State {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED;

    public static State convert(String source) {
        try {
            return State.valueOf(source);
        } catch (Exception e) {
            String message = String.format("Unknown state: %s", source);
            throw new NotFoundStateException(message);
        }
    }
}
