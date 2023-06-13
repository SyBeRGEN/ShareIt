package ru.practicum.shareit.item.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @MockBean
    private ItemMapper itemMapper;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @MockBean
    private ItemStorage itemStorage;

    @MockBean
    private UserStorage userStorage;

    @Test
    void testGetById() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        when(itemStorage.getById(anyLong())).thenReturn(new Item());
        assertThrows(NotFoundException.class, () -> itemServiceImpl.getById(1L));
        verify(itemStorage).getAll();
        verify(itemStorage, atLeast(1)).getById(anyLong());
    }

    @Test
    void testGetById2() {
        when(itemStorage.getAll()).thenThrow(new NotValidException("An error occurred"));
        when(itemStorage.getById(anyLong())).thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.getById(1L));
        verify(itemStorage).getById(anyLong());
    }

    @Test
    void testGetAllItemsByUserId() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.getAllItemsByUserId(1L).isEmpty());
        verify(itemStorage).getAll();
    }

    @Test
    void testGetAllItemsByUserId2() {
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(new ItemDto());

        Item item = new Item();
        item.setOwner(new User(1L, "Name", "jane.doe@example.org"));

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toItemDto(Mockito.<Item>any());
        verify(itemStorage).getAll();
    }

    @Test
    void testGetAllItemsByUserId3() {
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(new ItemDto());

        Item item = new Item();
        item.setOwner(new User(2L, "Name", "jane.doe@example.org"));

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        assertTrue(itemServiceImpl.getAllItemsByUserId(1L).isEmpty());
        verify(itemStorage).getAll();
    }

    @Test
    void testCreate() {
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());
        when(itemStorage.create(Mockito.<Item>any())).thenReturn(new Item());
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        assertSame(itemDto, itemServiceImpl.create(new ItemDto(), 1L));
        verify(itemMapper).toItemDto(Mockito.<Item>any());
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(itemStorage).create(Mockito.<Item>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testCreate2() {
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());
        when(itemStorage.create(Mockito.<Item>any())).thenThrow(new NotFoundException("An error occurred"));
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        assertThrows(NotFoundException.class, () -> itemServiceImpl.create(new ItemDto(), 1L));
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(itemStorage).create(Mockito.<Item>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testCreate3() {
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());
        when(itemStorage.create(Mockito.<Item>any())).thenReturn(new Item());
        when(userStorage.getById(anyLong())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> itemServiceImpl.create(new ItemDto(), 1L));
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate() {
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());
        when(userStorage.getAll()).thenReturn(new ArrayList<>());
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        assertThrows(NotFoundException.class, () -> itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(userStorage).getAll();
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate2() {
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());
        when(userStorage.getAll()).thenThrow(new NotValidException("An error occurred"));
        when(userStorage.getById(anyLong())).thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(userStorage).getAll();
    }

    @Test
    void testUpdate3() {
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());

        Item item = new Item();
        item.setOwner(new User(1L, "Name", "jane.doe@example.org"));
        when(itemStorage.update(Mockito.<Item>any())).thenReturn(new Item());
        when(itemStorage.getById(anyLong())).thenReturn(item);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Name", "jane.doe@example.org"));
        userList.add(new User(1L, "Пользователь с id = %s не найден", "jane.doe@example.org"));
        when(userStorage.getAll()).thenReturn(userList);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        assertSame(itemDto, itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toItemDto(Mockito.<Item>any());
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).update(Mockito.<Item>any());
        verify(userStorage).getAll();
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate4() {
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(itemDto);
        User owner = new User(1L, "Name", "jane.doe@example.org");

        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(
                new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));

        Item item = new Item();
        item.setOwner(new User(1L, "Name", "jane.doe@example.org"));
        when(itemStorage.update(Mockito.<Item>any())).thenReturn(new Item());
        when(itemStorage.getById(anyLong())).thenReturn(item);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Name", "jane.doe@example.org"));
        userList.add(new User(1L, "Пользователь с id = %s не найден", "jane.doe@example.org"));
        when(userStorage.getAll()).thenReturn(userList);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        assertSame(itemDto, itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toItemDto(Mockito.<Item>any());
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).update(Mockito.<Item>any());
        verify(userStorage).getAll();
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate5() {
        when(itemMapper.toItemDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(itemMapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());

        Item item = new Item();
        item.setOwner(new User(2L, "Name", "jane.doe@example.org"));
        when(itemStorage.update(Mockito.<Item>any())).thenReturn(new Item());
        when(itemStorage.getById(anyLong())).thenReturn(item);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Name", "jane.doe@example.org"));
        userList.add(new User(1L, "Пользователь с id = %s не найден", "jane.doe@example.org"));
        when(userStorage.getAll()).thenReturn(userList);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        assertThrows(NotFoundException.class, () -> itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toItem(Mockito.<ItemDto>any());
        verify(itemStorage).getById(anyLong());
        verify(userStorage).getAll();
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testDeleteById() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        when(itemStorage.getById(anyLong())).thenReturn(new Item());
        assertThrows(NotFoundException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getAll();
        verify(itemStorage, atLeast(1)).getById(anyLong());
    }

    @Test
    void testDeleteById2() {
        when(itemStorage.getAll()).thenThrow(new NotValidException("An error occurred"));
        when(itemStorage.getById(anyLong())).thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getById(anyLong());
    }

    @Test
    void testSearchItemsByDescription() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.searchItemsByDescription("Text").isEmpty());
        verify(itemStorage).getAll();
    }

    @Test
    void testSearchItemsByDescription2() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.searchItemsByDescription("").isEmpty());
    }

    @Test
    void testSearchItemsByDescription3() {
        when(itemStorage.getAll()).thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> itemServiceImpl.searchItemsByDescription("Text"));
        verify(itemStorage).getAll();
    }

    @Test
    void testSearchItemsByDescription4() {
        Item item = new Item();
        item.setDescription("The characteristics of someone or something");

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        assertTrue(itemServiceImpl.searchItemsByDescription("Text").isEmpty());
        verify(itemStorage).getAll();
    }
}

