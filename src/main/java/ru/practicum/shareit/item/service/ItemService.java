package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    ItemDto getById(long id);
    List<ItemDto> getAllItemsByUserId(long userId);
    ItemDto create(ItemDto itemDto, long ownerId);
    ItemDto update(ItemDto itemDto, long itemId, long userId);
    void deleteById(long id);
    Collection<ItemDto> searchItemsByDescription(String keyword);
}
