package ru.practicum.shareit.booking.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingStorageImpl implements BookingStorage {
    private final BookingRepository repository;

    @Override
    public Booking saveBooking(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public Booking findBookingById(long bookingId) {
        return repository.findById(bookingId).orElseThrow(
                () -> new NotFoundException(String.format("Booking с id %d не найден", bookingId)));
    }

    @Override
    public List<Booking> getBookingsByUser(Pageable pageable, User booker, Sort sort, State state) {
        List<Booking> bookings;

        switch (state) {
            case WAITING:
                bookings = repository.findByBooker_IdAndStatusOrderByStartDesc(booker.getId(),
                        StatusType.WAITING, pageable);
                break;
            case REJECTED:
                bookings = repository.findByBooker_IdAndStatusOrderByStartDesc(booker.getId(),
                        StatusType.REJECTED, pageable);
                break;
            case PAST:
                bookings = repository.findByBooker_IdAndEndBeforeOrderByStartDesc(booker.getId(),
                        LocalDateTime.now(), pageable);
                break;
            case FUTURE:
                bookings = repository.findByBooker_IdAndStartAfterOrderByStartDesc(booker.getId(),
                        LocalDateTime.now(), pageable);
                break;
            case CURRENT:
                bookings = repository.findByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(booker.getId(),
                        LocalDateTime.now(), LocalDateTime.now(), pageable);
                break;
            default:
                bookings = repository.findByBooker_IdOrderByStartDesc(booker.getId(), pageable);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsForOwner(Pageable pageable, User owner, Sort sort, State state) {
        List<Booking> bookings;

        switch (state) {
            case WAITING:
                bookings = repository.findByItem_Owner_IdAndStatusOrderByStartDesc(owner.getId(),
                        StatusType.WAITING, pageable);
                break;
            case REJECTED:
                bookings = repository.findByItem_Owner_IdAndStatusOrderByStartDesc(owner.getId(),
                        StatusType.REJECTED, pageable);
                break;
            case PAST:
                bookings = repository.findByItem_Owner_IdAndEndBeforeOrderByStartDesc(owner.getId(),
                        LocalDateTime.now(), pageable);
                break;
            case FUTURE:
                bookings = repository.findByItem_Owner_IdAndStartAfterOrderByStartDesc(owner.getId(),
                        LocalDateTime.now(), pageable);
                break;
            case CURRENT:
                bookings = repository.findByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(owner.getId(),
                        LocalDateTime.now(), LocalDateTime.now(), pageable);
                break;
            default:
                bookings = repository.findByItem_Owner_IdOrderByStartDesc(owner.getId(), pageable);
        }
        return bookings;
    }
}
