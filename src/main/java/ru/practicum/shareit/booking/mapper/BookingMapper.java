package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {ItemMapper.class, UserMapper.class})
public interface BookingMapper {
    @Mapping(target = "item.id", source = "itemId")
    Booking toEntity(BookingInputDto bookingInputDto);

    BookingOutputDto toDto(Booking booking);

    @Mapping(target = "bookerId", source = "booker.id")
    BookingItemDto toBookingItemDto(Booking booking);
}