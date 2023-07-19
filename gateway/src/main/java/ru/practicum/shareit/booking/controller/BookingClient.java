package ru.practicum.shareit.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.web.BaseWebClient;
import ru.practicum.shareit.handler.exception.StateException;

import java.util.Map;

@Service
public class BookingClient extends BaseWebClient {
    private static final String API_PREFIX = "/bookings";
    private static final String CACHE = "bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String url) {
        super(WebClient.builder()
                .baseUrl(url + API_PREFIX)
                .build());
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> createBookingRequest(Long bookerId, BookingDto bookingDto) {
        return post("", bookerId, bookingDto);
    }

    @CachePut(CACHE)
    public Mono<ResponseEntity<Object>> updateBookingRequest(Long ownerId, String approved, Long bookingId) {
        Map<String, Object> parameters = Map.of(
                "approved", approved
        );
        return patch("/" + bookingId + "?approved={approved}", ownerId, parameters, null);
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> getBooking(Long bookingId, Long userId) {
        return get("/" + bookingId, userId);
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> getBookingsByUser(Long userId, String state, Integer from,
                                                          Integer size) {
        validateState(state);
        Map<String, Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("?state={state}&&from={from}&&size={size}", userId, parameters);
    }

    @Cacheable(CACHE)
    public Mono<ResponseEntity<Object>> getBookingsForOwner(Long userId, String state, Integer from,
                                                            Integer size) {
        validateState(state);
        Map<String, Object> parameters = Map.of(
                "state", state,
                "from", from,
                "size", size
        );
        return get("/owner?state={state}&&from={from}&&size={size}", userId, parameters);
    }

    private void validateState(String state) {
        if (State.fromValue(state).equals(State.UNSUPPORTED_STATUS)) {
            throw new StateException("Unknown state: " + state);
        }
    }
}
