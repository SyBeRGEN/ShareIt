package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface ItemMapper {

    Item toEntity(ItemDto itemDto);

    ItemDto toDto(Item item);

    ItemDtoWithBooking toDtoWithBooking(Item item);

    Item toEntityFromBooking(ItemDtoWithBooking itemDtoWithBooking);

}
