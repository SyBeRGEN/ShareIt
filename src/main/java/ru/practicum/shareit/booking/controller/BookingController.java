package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.utils.AccessLevel;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.exception.InvalidStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<BookingOutputDto> createBookingRequest(@RequestHeader(userIdHeader) long userId,
                                                                 @RequestBody @Valid BookingInputDto bookingInputDto) {
        return ResponseEntity.status(201).body(service.createdBookingRequest(bookingInputDto, userId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingOutputDto> updateBookingRequest(@RequestHeader(userIdHeader) long userId,
                                                                 @PathVariable long bookingId,
                                                                 @RequestParam boolean approved) {
        return ResponseEntity.ok().body(service.updateBookingRequest(userId, bookingId, approved, AccessLevel.OWNER));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingOutputDto> getBooking(@RequestHeader(userIdHeader) long userId,
                                                       @PathVariable long bookingId) {
        return ResponseEntity.ok().body(service.findBookingById(userId, bookingId, AccessLevel.BOTH));
    }

    @GetMapping
    public ResponseEntity<List<BookingOutputDto>> getBookingsByUser(@RequestHeader(userIdHeader) long userId,
                                                                    @RequestParam(defaultValue = "ALL") String state,
                                                                    @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (from < 0) {
            throw new InvalidStatusException("from must be more than 0");
        }
        if (size < 1 || size > 20) {
            throw new InvalidStatusException("size must be more than 1 and less than 20");
        }
        return ResponseEntity.ok().body(service.getBookingsByUser(PageRequest.of(from / size, size), userId, State.convert(state)));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingOutputDto>> getBookingsForOwner(@RequestHeader(userIdHeader) long userId,
                                                                      @RequestParam(defaultValue = "ALL") String state,
                                                                      @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (from < 0) {
            throw new InvalidStatusException("from must be more than 0");
        }
        if (size < 1 || size > 20) {
            throw new InvalidStatusException("size must be more than 1 and less than 20");
        }
        return ResponseEntity.ok().body(service.getBookingsForOwner(PageRequest.of(from / size, size), userId, State.convert(state)));
    }
}
