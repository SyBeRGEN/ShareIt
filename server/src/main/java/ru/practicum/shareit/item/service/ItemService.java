package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    ItemDtoWithBooking getById(long id, long userId);

    List<ItemDtoWithBooking> getAllItemsByUserId(long userId);

    ItemDto create(ItemDto itemDto, long ownerId);

    ItemDto update(ItemDto itemDto, long itemId, long userId);

    void deleteById(long id);

    Collection<ItemDto> searchItemsByDescription(String keyword);

    CommentDto createComment(long userId, long itemId, CommentDto commentDto);
}
