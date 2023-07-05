package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

class CommentDtoTest {
    CommentDto commentDto = new CommentDto(0L, "text", "authorName", LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 28));

    @Test
    void testSetId() {
        commentDto.setId(0L);
    }

    @Test
    void testSetText() {
        commentDto.setText("text");
    }

    @Test
    void testSetAuthorName() {
        commentDto.setAuthorName("authorName");
    }

    @Test
    void testSetCreated() {
        commentDto.setCreated(LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 28));
    }

    @Test
    void testEquals() {
        boolean result = commentDto.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = commentDto.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = commentDto.toString();
        Assertions.assertEquals("CommentDto(id=0, text=text, authorName=authorName, created=2023-07-05T17:48:28)", result);
    }
}