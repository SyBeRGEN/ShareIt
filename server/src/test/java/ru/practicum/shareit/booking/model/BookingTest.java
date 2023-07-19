package ru.practicum.shareit.booking.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BookingTest {
    @Test
    void testConstructor() {
        Booking actualBooking = new Booking();
        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        actualBooking.setBooker(booker);
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBooking.setEnd(end);
        actualBooking.setId(1L);
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setItems(new HashSet<>());
        request.setRequester(requester);
        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        actualBooking.setItem(item);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBooking.setStart(start);
        actualBooking.setStatus(StatusType.WAITING);
        assertSame(booker, actualBooking.getBooker());
        assertSame(end, actualBooking.getEnd());
        assertEquals(1L, actualBooking.getId());
        assertSame(item, actualBooking.getItem());
        assertSame(start, actualBooking.getStart());
        assertEquals(StatusType.WAITING, actualBooking.getStatus());
    }
}