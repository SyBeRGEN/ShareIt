package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.Month;

class BookingItemDtoTest {
    @Mock
    ItemDto item;
    @InjectMocks
    BookingItemDto bookingItemDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetId() {
        bookingItemDto.setId(0L);
    }

    @Test
    void testSetStart() {
        bookingItemDto.setStart(LocalDateTime.of(2023, Month.JULY, 5, 17, 41, 17));
    }

    @Test
    void testSetEnd() {
        bookingItemDto.setEnd(LocalDateTime.of(2023, Month.JULY, 5, 17, 41, 17));
    }

    @Test
    void testSetItem() {
        bookingItemDto.setItem(new ItemDto(Long.valueOf(1), "name", "description", Boolean.TRUE, new UserDto(0L, "name", "email"), Long.valueOf(1)));
    }

    @Test
    void testSetBookerId() {
        bookingItemDto.setBookerId(0L);
    }

    @Test
    void testEquals() {
        boolean result = bookingItemDto.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = bookingItemDto.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = bookingItemDto.toString();
        Assertions.assertEquals("BookingItemDto(id=0, start=null, end=null, item=item, bookerId=0)", result);
    }
}