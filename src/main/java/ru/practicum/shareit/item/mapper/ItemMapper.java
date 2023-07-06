package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {

    Item toEntity(ItemDto itemDto);

    @Mapping(source = "request.id", target = "requestId")
    ItemDto toDto(Item item);

    ItemDtoWithBooking toDtoWithBooking(Item item);

    Item toEntityFromBooking(ItemDtoWithBooking itemDtoWithBooking);

}
