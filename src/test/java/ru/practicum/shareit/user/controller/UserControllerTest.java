package ru.practicum.shareit.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService service;
    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        when(service.getById(anyLong())).thenReturn(new UserDto(0L, "name", "email"));

        UserDto result = userController.getById(0L);
        Assertions.assertEquals(new UserDto(0L, "name", "email"), result);
    }

    @Test
    void testGetAllUsers() {
        when(service.getAll()).thenReturn(List.of(new UserDto(0L, "name", "email")));

        Collection<UserDto> result = userController.getAllUsers();
        Assertions.assertEquals(List.of(new UserDto(0L, "name", "email")), result);
    }

    @Test
    void testCreate() {
        when(service.create(any())).thenReturn(new UserDto(0L, "name", "email"));

        UserDto result = userController.create(new UserDto(0L, "name", "email"));
        Assertions.assertEquals(new UserDto(0L, "name", "email"), result);
    }

    @Test
    void testUpdate() {
        when(service.update(any(), anyLong())).thenReturn(new UserDto(0L, "name", "email"));

        UserDto result = userController.update(new UserDto(0L, "name", "email"), 0L);
        Assertions.assertEquals(new UserDto(0L, "name", "email"), result);
    }

    @Test
    void testDeleteById() {
        when(service.deleteById(anyLong())).thenReturn(Boolean.TRUE);

        userController.deleteById(0L);
    }
}