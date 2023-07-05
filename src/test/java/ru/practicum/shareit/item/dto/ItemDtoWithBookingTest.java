package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

class ItemDtoWithBookingTest {
    @Mock
    UserDto owner;
    @Mock
    BookingItemDto lastBooking;
    @Mock
    BookingItemDto nextBooking;
    @Mock
    List<CommentDto> comments;
    @InjectMocks
    ItemDtoWithBooking itemDtoWithBooking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetId() {
        itemDtoWithBooking.setId(Long.valueOf(1));
    }

    @Test
    void testSetName() {
        itemDtoWithBooking.setName("name");
    }

    @Test
    void testSetDescription() {
        itemDtoWithBooking.setDescription("description");
    }

    @Test
    void testSetAvailable() {
        itemDtoWithBooking.setAvailable(Boolean.TRUE);
    }

    @Test
    void testSetOwner() {
        itemDtoWithBooking.setOwner(new UserDto(0L, "name", "email"));
    }

    @Test
    void testSetLastBooking() {
        itemDtoWithBooking.setLastBooking(new BookingItemDto(0L, LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 42), LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 42), new ItemDto(Long.valueOf(1), "name", "description", Boolean.TRUE, new UserDto(0L, "name", "email"), Long.valueOf(1)), 0L));
    }

    @Test
    void testSetNextBooking() {
        itemDtoWithBooking.setNextBooking(new BookingItemDto(0L, LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 42), LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 42), new ItemDto(Long.valueOf(1), "name", "description", Boolean.TRUE, new UserDto(0L, "name", "email"), Long.valueOf(1)), 0L));
    }

    @Test
    void testSetComments() {
        itemDtoWithBooking.setComments(List.of(new CommentDto(0L, "text", "authorName", LocalDateTime.of(2023, Month.JULY, 5, 17, 48, 42))));
    }

    @Test
    void testEquals() {
        boolean result = itemDtoWithBooking.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = itemDtoWithBooking.canEqual("other");
        Assertions.assertEquals(false, result);
    }
}