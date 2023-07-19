package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item getById(long id);

    List<Item> getAll();

    Item create(Item item);

    Item update(Item item);

    void deleteById(long id);
}
