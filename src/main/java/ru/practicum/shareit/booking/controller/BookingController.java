package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.utils.AccessLevel;
import ru.practicum.shareit.booking.utils.State;

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
                                                                    @RequestParam(defaultValue = "ALL") String state) {
        return ResponseEntity.ok().body(service.getBookingsByUser(userId, State.convert(state)));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingOutputDto>> getBookingsForOwner(@RequestHeader(userIdHeader) long userId,
                                                                      @RequestParam(defaultValue = "ALL") String state) {
        return ResponseEntity.ok().body(service.getBookingsForOwner(userId, State.convert(state)));
    }
}
