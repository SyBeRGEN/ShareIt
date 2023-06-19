package ru.practicum.shareit.booking.dto;

import lombok.Value;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class BookingItemDto implements Serializable {
    @Positive
    long id;
    LocalDateTime start;
    LocalDateTime end;
    ItemDto item;
    long bookerId;
}
