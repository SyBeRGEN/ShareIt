package ru.practicum.shareit.itemRequest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemRequestDtoTest {
    ItemRequestDto itemRequestDto = new ItemRequestDto("description");

    @Test
    void testSetDescription() {
        itemRequestDto.setDescription("description");
    }

    @Test
    void testEquals() {
        boolean result = itemRequestDto.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = itemRequestDto.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = itemRequestDto.toString();
        Assertions.assertEquals("ItemRequestDto(description=description)", result);
    }
}