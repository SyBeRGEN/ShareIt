package ru.practicum.shareit.item.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemStorageImpl implements ItemStorage {

    private final ItemRepository repository;

    @Override
    public Item create(Item item) {
        repository.save(item);
        return item;
    }

    @Override
    public Item update(Item item) {
        repository.save(item);
        return item;
    }

    @Override
    public Item getById(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Item> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
