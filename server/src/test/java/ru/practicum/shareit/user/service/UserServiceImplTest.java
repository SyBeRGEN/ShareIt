package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserStorage userStorage;

    @Test
    void testGetById() {
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        assertSame(userDto, userServiceImpl.getById(1L));
        verify(userMapper).toDto(Mockito.<User>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testGetAll() {
        when(userStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(userServiceImpl.getAll().isEmpty());
        verify(userStorage).getAll();
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userStorage.create(Mockito.<User>any())).thenReturn(user2);
        assertSame(userDto, userServiceImpl.create(new UserDto(1L, "Name", "jane.doe@example.org")));
        verify(userMapper).toDto(Mockito.<User>any());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(userStorage).create(Mockito.<User>any());
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");

        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        when(userStorage.update(Mockito.<User>any())).thenReturn(user2);
        assertSame(userDto, userServiceImpl.update(new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        verify(userMapper).toDto(Mockito.<User>any());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(userStorage).update(Mockito.<User>any());
    }

    @Test
    void testDeleteById() {
        when(userStorage.deleteById(anyLong())).thenReturn(true);
        assertTrue(userServiceImpl.deleteById(1L));
        verify(userStorage).deleteById(anyLong());
    }

    @Test
    void testDeleteById2() {
        when(userStorage.deleteById(anyLong())).thenReturn(false);
        assertFalse(userServiceImpl.deleteById(1L));
        verify(userStorage).deleteById(anyLong());
    }
}