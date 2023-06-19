package ru.practicum.shareit.booking.dto;

import lombok.Value;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class BookingOutputDto implements Serializable {
    @Positive
    long id;
    LocalDateTime start;
    LocalDateTime end;
    StatusType status;
    ItemDto item;
    UserDto booker;
}