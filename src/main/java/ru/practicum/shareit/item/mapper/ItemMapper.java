package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

// 19 java анализирует все мапперы и сопоставляет, в 11 приходится прописывать ComponentModel
// Очень удобный автоматизированный маппер
@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toItemDto(Item item);
    Item toItem(ItemDto itemDto);
}
