package ru.practicum.shareit.booking.storage;

import lombok.RequiredArgsConstructor;
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
    public List<Booking> getBookingsByUser(User booker, Sort sort, State state) {
        List<Booking> bookings;

        switch (state) {
            case WAITING:
                bookings = repository.findAllByBookerIdAndStatus(booker.getId(),
                        StatusType.WAITING, sort);
                break;
            case REJECTED:
                bookings = repository.findAllByBookerIdAndStatus(booker.getId(),
                        StatusType.REJECTED, sort);
                break;
            case PAST:
                bookings = repository.findAllByBookerIdAndEndBefore(booker.getId(),
                        LocalDateTime.now(), sort);
                break;
            case FUTURE:
                bookings = repository.findAllByBookerIdAndStartAfter(booker.getId(),
                        LocalDateTime.now(), sort);
                break;
            case CURRENT:
                bookings = repository.findAllByBookerIdAndStartBeforeAndEndAfter(booker.getId(),
                        LocalDateTime.now(), sort);
                break;
            default:
                bookings = repository.findAllByBookerId(booker.getId(), sort);
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsForOwner(User owner, Sort sort, State state) {
        List<Booking> bookings;

        switch (state) {
            case WAITING:
                bookings = repository.findAllByOwnerIdAndStatus(owner.getId(),
                        StatusType.WAITING, sort);
                break;
            case REJECTED:
                bookings = repository.findAllByOwnerIdAndStatus(owner.getId(),
                        StatusType.REJECTED, sort);
                break;
            case PAST:
                bookings = repository.findAllByOwnerIdAndEndBefore(owner.getId(),
                        LocalDateTime.now(), sort);
                break;
            case FUTURE:
                bookings = repository.findAllByOwnerIdAndStartAfter(owner.getId(),
                        LocalDateTime.now(), sort);
                break;
            case CURRENT:
                bookings = repository.findAllByOwnerIdAndStartBeforeAndEndAfter(owner.getId(),
                        LocalDateTime.now(), sort);
                break;
            default:
                bookings = repository.findAllByOwnerId(owner.getId(), sort);
        }
        return bookings;
    }
}
