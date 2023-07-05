package ru.practicum.shareit.itemRequest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

class ItemRequestDtoResponseTest {
    ItemRequestDtoResponse itemRequestDtoResponse = new ItemRequestDtoResponse(Long.valueOf(1), "description", LocalDateTime.of(2023, Month.JULY, 5, 17, 50, 50));

    @Test
    void testSetId() {
        itemRequestDtoResponse.setId(Long.valueOf(1));
    }

    @Test
    void testSetDescription() {
        itemRequestDtoResponse.setDescription("description");
    }

    @Test
    void testSetCreated() {
        itemRequestDtoResponse.setCreated(LocalDateTime.of(2023, Month.JULY, 5, 17, 50, 50));
    }

    @Test
    void testEquals() {
        boolean result = itemRequestDtoResponse.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = itemRequestDtoResponse.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = itemRequestDtoResponse.toString();
        Assertions.assertEquals("ItemRequestDtoResponse(id=1, description=description, created=2023-07-05T17:50:50)", result);
    }
}