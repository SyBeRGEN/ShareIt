package ru.practicum.shareit.user.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidEmailException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserStorageImpl.class})
@ExtendWith(SpringExtension.class)
class UserStorageImplTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserStorageImpl userStorageImpl;

    @Test
    void testGetById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(user, userStorageImpl.getById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetById2() {
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userStorageImpl.getById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetById3() {
        when(userRepository.findById(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("Пользователь с идентификатором %s не найден"));
        assertThrows(RuntimeException.class, () -> userStorageImpl.getById(1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetAll() {
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        Collection<User> actualAll = userStorageImpl.getAll();
        assertSame(userList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void testGetAll2() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> userStorageImpl.getAll());
        verify(userRepository).findAll();
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        assertSame(user2, userStorageImpl.create(user2));
        verify(userRepository).save(Mockito.<User>any());
    }

    @Test
    void testCreate2() {
        when(userRepository.save(Mockito.<User>any())).thenThrow(new RuntimeException("foo"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        assertThrows(NotValidEmailException.class, () -> userStorageImpl.create(user));
        verify(userRepository).save(Mockito.<User>any());
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById(Mockito.<Long>any());
        assertFalse(userStorageImpl.deleteById(1L));
        verify(userRepository).deleteById(Mockito.<Long>any());
    }

    @Test
    void testDeleteById2() {
        doThrow(new RuntimeException("foo")).when(userRepository).deleteById(Mockito.<Long>any());
        assertTrue(userStorageImpl.deleteById(1L));
        verify(userRepository).deleteById(Mockito.<Long>any());
    }
}

