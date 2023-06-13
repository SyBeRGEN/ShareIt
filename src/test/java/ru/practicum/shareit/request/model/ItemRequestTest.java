package ru.practicum.shareit.request.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.time.Month;

class ItemRequestTest {
    @Mock
    User requester;
    @InjectMocks
    ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetId() {
        itemRequest.setId(0);
    }

    @Test
    void testSetDescription() {
        itemRequest.setDescription("description");
    }

    @Test
    void testSetRequester() {
        itemRequest.setRequester(new User(0L, "name", "email"));
    }

    @Test
    void testSetCreated() {
        itemRequest.setCreated(LocalDateTime.of(2023, Month.JUNE, 13, 19, 2, 0));
    }

}