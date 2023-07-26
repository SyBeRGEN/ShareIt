package ru.practicum.shareit.booking.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.utils.AccessLevel;
import ru.practicum.shareit.booking.utils.State;

import java.util.List;

public interface BookingService {
    BookingOutputDto createdBookingRequest(BookingInputDto bookingInputDto, long userId);

    BookingOutputDto updateBookingRequest(long userId, long bookingId, boolean approved, AccessLevel accessLevel);

    BookingOutputDto findBookingById(long userId, long bookingId, AccessLevel accessLevel);

    List<BookingOutputDto> getBookingsByUser(Pageable pageable, long userId, State state);

    List<BookingOutputDto> getBookingsForOwner(Pageable pageable, long userId, State state);
}
