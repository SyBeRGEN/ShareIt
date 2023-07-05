package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingOutputDtoTest {
    @Test
    void testCanEqual() {
        assertFalse((new BookingOutputDto()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        BookingOutputDto bookingOutputDto = new BookingOutputDto();
        assertTrue(bookingOutputDto.canEqual(new BookingOutputDto()));
    }

    @Test
    void testConstructor() {
        BookingOutputDto actualBookingOutputDto = new BookingOutputDto();
        UserDto booker = new UserDto(1L, "Name", "jane.doe@example.org");

        actualBookingOutputDto.setBooker(booker);
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBookingOutputDto.setEnd(end);
        actualBookingOutputDto.setId(1L);
        ItemDto item = new ItemDto();
        actualBookingOutputDto.setItem(item);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBookingOutputDto.setStart(start);
        actualBookingOutputDto.setStatus(StatusType.WAITING);
        String actualToStringResult = actualBookingOutputDto.toString();
        assertSame(booker, actualBookingOutputDto.getBooker());
        assertSame(end, actualBookingOutputDto.getEnd());
        assertEquals(1L, actualBookingOutputDto.getId());
        assertSame(item, actualBookingOutputDto.getItem());
        assertSame(start, actualBookingOutputDto.getStart());
        assertEquals(StatusType.WAITING, actualBookingOutputDto.getStatus());
        assertEquals(
                "BookingOutputDto(id=1, start=1970-01-01T00:00, end=1970-01-01T00:00, status=WAITING, item=ItemDto(id=null,"
                        + " name=null, description=null, available=null, owner=null, requestId=null), booker=UserDto(id=1,"
                        + " name=Name, email=jane.doe@example.org))",
                actualToStringResult);
    }

    @Test
    void testConstructor2() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        ItemDto item = new ItemDto();
        UserDto booker = new UserDto(1L, "Name", "jane.doe@example.org");

        BookingOutputDto actualBookingOutputDto = new BookingOutputDto(1L, start, end, StatusType.WAITING, item, booker);

        assertSame(booker, actualBookingOutputDto.getBooker());
        assertEquals(StatusType.WAITING, actualBookingOutputDto.getStatus());
        assertSame(item, actualBookingOutputDto.getItem());
        assertEquals("00:00", actualBookingOutputDto.getEnd().toLocalTime().toString());
        assertEquals("00:00", actualBookingOutputDto.getStart().toLocalTime().toString());
        assertEquals(1L, actualBookingOutputDto.getId());
    }

    @Test
    void testEquals() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        ItemDto item = new ItemDto();
        BookingOutputDto bookingOutputDto = new BookingOutputDto(1L, start, end, StatusType.WAITING, item,
                new UserDto(1L, "Name", "jane.doe@example.org"));
        assertNotEquals(bookingOutputDto, new BookingOutputDto());
    }

}