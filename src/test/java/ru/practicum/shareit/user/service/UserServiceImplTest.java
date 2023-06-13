package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserMapper mapper;
    @Mock
    UserStorage storage;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        when(mapper.toUserDto(any())).thenReturn(new UserDto(0L, "name", "email"));
        when(storage.getById(anyLong())).thenReturn(new User(0L, "name", "email"));

        UserDto result = userServiceImpl.getById(0L);
        Assertions.assertEquals(new UserDto(0L, "name", "email"), result);
    }

    @Test
    void testCreate() {
        when(mapper.toUserDto(any())).thenReturn(new UserDto(0L, "name", "email"));
        when(mapper.toUser(any())).thenReturn(new User(0L, "name", "email"));
        when(storage.create(any())).thenReturn(new User(0L, "name", "email"));

        UserDto result = userServiceImpl.create(new UserDto(0L, "name", "email"));
        Assertions.assertEquals(new UserDto(0L, "name", "email"), result);
    }

    @Test
    void testUpdate() {
        when(mapper.toUserDto(any())).thenReturn(new UserDto(0L, "name", "email"));
        when(mapper.toUser(any())).thenReturn(new User(0L, "name", "email"));
        when(storage.update(any())).thenReturn(new User(0L, "name", "email"));

        UserDto result = userServiceImpl.update(new UserDto(0L, "name", "email"), 0L);
        Assertions.assertEquals(new UserDto(0L, "name", "email"), result);
    }

    @Test
    void testDeleteById() {
        when(storage.deleteById(anyLong())).thenReturn(Boolean.TRUE);

        Boolean result = userServiceImpl.deleteById(0L);
        Assertions.assertEquals(Boolean.TRUE, result);
    }
}