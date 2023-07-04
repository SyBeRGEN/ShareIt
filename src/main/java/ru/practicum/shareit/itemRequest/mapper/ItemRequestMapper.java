package ru.practicum.shareit.itemRequest.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class, UserMapper.class})
public interface ItemRequestMapper {
    ItemRequest toEntity(ItemRequestDto itemRequestDto);

    ItemRequestDtoResponse toOutputDto(ItemRequest itemRequest);

    ItemRequestDtoResponseWithItems toOutputDtoWithItems(ItemRequest itemRequest);

    List<ItemRequestDtoResponseWithItems> toOutputDtoWithItemsList(List<ItemRequest> itemRequests);

}