package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.booking.utils.AccessLevel;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.InvalidLocalDateTimeException;
import ru.practicum.shareit.exception.InvalidStatusException;
import ru.practicum.shareit.exception.ItemNotAvailableException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;
    private final BookingStorage storage;
    private final UserService userService;
    private final ItemService itemService;

    @Transactional
    @Override
    public BookingOutputDto createdBookingRequest(BookingInputDto bookingInputDto, long userId) {
        Booking booking = bookingMapper.toEntity(bookingInputDto);

        User booker = userMapper.toEntity(userService.getById(userId));
        Item item = itemMapper.toEntityFromBooking(itemService.getById(booking.getItem().getId(), userId));


        validateAddBooking(booking, item, booker);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(StatusType.WAITING);
        return bookingMapper.toDto(storage.saveBooking(booking));
    }

    @Transactional
    @Override
    public BookingOutputDto updateBookingRequest(long userId, long bookingId, boolean approved, AccessLevel accessLevel) {
        User owner = userMapper.toEntity(userService.getById(userId));
        Booking booking = storage.findBookingById(bookingId);

        if (isUnableToAccess(owner.getId(), booking, accessLevel)) {
            throw new AccessException(String.format("У пользователя с id: %d нет прав на просмотр бронирования с id: %d,",
                    userId, bookingId));
        }
        if (booking.getStatus().equals(StatusType.APPROVED)) {
            throw new InvalidStatusException(String.format("У бронирования с id %d уже стоит статус %s",
                    bookingId, StatusType.APPROVED.name()));
        }
        if (approved) {
            booking.setStatus(StatusType.APPROVED);
        } else {
            booking.setStatus(StatusType.REJECTED);
        }
        return bookingMapper.toDto(storage.saveBooking(booking));
    }

    @Transactional(readOnly = true)
    @Override
    public BookingOutputDto findBookingById(long userId, long bookingId, AccessLevel accessLevel) {
        User booker = userMapper.toEntity(userService.getById(userId));
        Booking booking = storage.findBookingById(bookingId);

        if (isUnableToAccess(booker.getId(), booking, accessLevel)) {
            throw new AccessException(String.format("У пользователя с id: %d нет прав на просмотр бронирования с id: %d,",
                    userId, bookingId));
        }
        return bookingMapper.toDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutputDto> getBookingsByUser(Pageable pageable, long userId, State state) {
        User booker = userMapper.toEntity(userService.getById(userId));
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        List<Booking> bookings = storage.getBookingsByUser(pageable, booker, sort, state);

        return bookings
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutputDto> getBookingsForOwner(Pageable pageable, long userId, State state) {
        User owner = userMapper.toEntity(userService.getById(userId));
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        List<Booking> bookings = storage.getBookingsForOwner(pageable, owner, sort, state);

        bookings.sort(Comparator.comparing(Booking::getStart).reversed());

        return bookings
                .stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateAddBooking(Booking booking, Item item, User booker) {
        if (booker.getId() == item.getOwner().getId()) {
            throw new AccessException("Владелец не может бронировать свои вещи");
        } else if (!item.getAvailable()) {
            throw new ItemNotAvailableException(String.format("Вещь с id %d не доступна уже занята", item.getId()));
        } else if (isNotValidDate(booking.getStart(), booking.getEnd())) {
            throw new InvalidLocalDateTimeException("Ошибка в датах бронирования");
        }
    }

    private boolean isNotValidDate(LocalDateTime startBooking, LocalDateTime endBooking) {
        return startBooking.isBefore(LocalDateTime.now()) || endBooking.isBefore(LocalDateTime.now())
                || endBooking.isBefore(startBooking) || startBooking.isEqual(endBooking);
    }

    private boolean isUnableToAccess(long userId, Booking booking, AccessLevel accessLevel) {
        boolean isUnable = true;
        switch (accessLevel) {
            case OWNER:
                isUnable = booking.getItem().getOwner().getId() != userId;
                break;
            case BOOKER:
                isUnable = booking.getBooker().getId() != userId;
                break;
            case BOTH:
                isUnable = !(booking.getItem().getOwner().getId() == userId || booking.getBooker().getId() == userId);
                break;
        }
        return isUnable;
    }
}
