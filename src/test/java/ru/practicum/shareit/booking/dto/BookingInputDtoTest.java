package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

class BookingInputDtoTest {
    BookingInputDto bookingInputDto = new BookingInputDto(0L, LocalDateTime.of(2023, Month.JULY, 5, 17, 44, 18), LocalDateTime.of(2023, Month.JULY, 5, 17, 44, 18));

    @Test
    void testSetItemId() {
        bookingInputDto.setItemId(0L);
    }

    @Test
    void testSetStart() {
        bookingInputDto.setStart(LocalDateTime.of(2023, Month.JULY, 5, 17, 44, 18));
    }

    @Test
    void testSetEnd() {
        bookingInputDto.setEnd(LocalDateTime.of(2023, Month.JULY, 5, 17, 44, 18));
    }

    @Test
    void testEquals() {
        boolean result = bookingInputDto.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = bookingInputDto.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = bookingInputDto.toString();
        Assertions.assertEquals("BookingInputDto(itemId=0, start=2023-07-05T17:44:18, end=2023-07-05T17:44:18)", result);
    }
}