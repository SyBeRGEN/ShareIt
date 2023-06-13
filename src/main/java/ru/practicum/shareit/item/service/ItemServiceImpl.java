package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper mapper;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto getById(long id) {
        itemIdValidator(itemStorage.getById(id));
        return mapper.toItemDto(itemStorage.getById(id));
    }

    @Override
    public List<ItemDto> getAllItemsByUserId(long userId) {
        return itemStorage.getAll()
                .stream()
                .filter(i -> Objects.equals(i.getOwner().getId(), userId))
                .map(mapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(ItemDto itemDto, long userId) {
        Item newItem = mapper.toItem(itemDto);
        User owner = userStorage.getById(userId);
        itemOwnerCheckValidator(owner, newItem, userId);
        Item createdItem = itemStorage.create(newItem);
        return mapper.toItemDto(createdItem);
    }

    @Override
    public ItemDto update(ItemDto itemDto, long itemId, long userId) {
        Item item  = mapper.toItem(itemDto);
        userIdValidator(userId);
        Item oldItem = itemStorage.getById(itemId);
        itemOwnerNameDescAvailValidator(item, oldItem, userId);
        Item changedItem = itemStorage.update(oldItem);
        return mapper.toItemDto(changedItem);
    }

    public void deleteById(long id) {
        itemIdValidator(itemStorage.getById(id));
        itemStorage.deleteById(id);
    }

    @Override
    public Collection<ItemDto> searchItemsByDescription(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getAll()
                .stream()
                .filter(i -> i.getDescription().toLowerCase().contains(text.toLowerCase()) && i.getAvailable())
                .map(mapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void itemIdValidator(Item item) {
        if (!itemStorage.getAll().contains(itemStorage.getById(item.getId()))) {
            throw new NotFoundException("Предмет с id: " + itemStorage.getById(item.getId()) + "не найден");
        }
        if (item.getName().isBlank()) {
            throw new NotValidException("Имя не может быть пустым");
        }
        if (item.getDescription().isBlank()) {
            throw new NotValidException("Описание не может быть пустым");
        }
    }

    private void itemOwnerCheckValidator(User owner, Item newItem, long id) {
        if (owner == null) {
            throw new NotFoundException(String.format("Пользователь с id = %s не найден", id));
        } else {
            newItem.setOwner(owner);
        }
    }

    private void itemOwnerNameDescAvailValidator(Item item, Item oldItem, long userId) {
        if (oldItem.getOwner().getId() != userId) {
            throw new NotFoundException("Пользователь не является носителем данного предмета!");
        }
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
    }

    private void userIdValidator(long userId) {
        if (!userStorage.getAll().contains(userStorage.getById(userId))) {
            throw new NotFoundException(String.format("Пользователь с id = %s не найден", userId));
        }
    }
}
