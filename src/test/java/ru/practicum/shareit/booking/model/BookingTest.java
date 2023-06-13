package ru.practicum.shareit.booking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Statuses;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

class BookingTest {
    @Test
    void testCanEqual() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        assertFalse((new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING))
                .canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertTrue(booking.canEqual(
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING)));
    }

    @Test
    void testConstructor() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        User booker = new User(1L, "Name", "jane.doe@example.org");

        Booking actualBooking = new Booking(1, start, end, item, booker, Statuses.WAITING);
        User booker2 = new User(1L, "Name", "jane.doe@example.org");

        actualBooking.setBooker(booker2);
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBooking.setEnd(end2);
        actualBooking.setId(1);
        Item item2 = new Item();
        actualBooking.setItem(item2);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualBooking.setStart(start2);
        actualBooking.setStatus(Statuses.WAITING);
        String actualToStringResult = actualBooking.toString();
        User booker3 = actualBooking.getBooker();
        assertSame(booker2, booker3);
        assertEquals(booker, booker3);
        assertSame(end2, actualBooking.getEnd());
        assertEquals(1, actualBooking.getId());
        Item item3 = actualBooking.getItem();
        assertSame(item2, item3);
        assertEquals(item, item3);
        assertSame(start2, actualBooking.getStart());
        assertEquals(Statuses.WAITING, actualBooking.getStatus());
        assertEquals(
                "Booking(id=1, start=1970-01-01T00:00, end=1970-01-01T00:00, item=Item(id=0, name=null, description=null,"
                        + " available=null, owner=null, request=null), booker=User(id=1, name=Name, email=jane.doe@example.org),"
                        + " status=WAITING)",
                actualToStringResult);
    }

    @Test
    void testEquals() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        assertNotEquals(new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING),
                null);
    }

    @Test
    void testEquals2() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        assertNotEquals(new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING),
                "Different type to Booking");
    }

    @Test
    void testEquals3() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        assertEquals(booking, booking);
        int expectedHashCodeResult = booking.hashCode();
        assertEquals(expectedHashCodeResult, booking.hashCode());
    }

    @Test
    void testEquals4() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        Booking booking2 = new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);

        assertEquals(booking, booking2);
        int expectedHashCodeResult = booking.hashCode();
        assertEquals(expectedHashCodeResult, booking2.hashCode());
    }

    @Test
    void testEquals5() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(2, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals6() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals7() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.now().atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals8() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Booking booking = new Booking(1, start, end, null, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals9() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        User owner = new User(1L, "Name", "jane.doe@example.org");

        Item item = new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest());

        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals10() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = mock(Item.class);
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals11() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(2L, "Name", "jane.doe@example.org"),
                Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals12() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Booking booking = new Booking(1, start, end, new Item(), null, Statuses.WAITING);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals13() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"), null);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }

    @Test
    void testEquals14() {
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item = new Item();
        Booking booking = new Booking(1, start, end, item, new User(1L, "Name", "jane.doe@example.org"),
                Statuses.APPROVED);
        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        Item item2 = new Item();
        assertNotEquals(booking,
                new Booking(1, start2, end2, item2, new User(1L, "Name", "jane.doe@example.org"), Statuses.WAITING));
    }
}

