package ru.practicum.shareit.booking.storage;

import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface BookingStorage {
    Booking saveBooking(Booking booking);

    Booking findBookingById(long bookingId);

    List<Booking> getBookingsByUser(User booker, Sort sort, State state);

    List<Booking> getBookingsForOwner(User owner, Sort sort, State state);
}
