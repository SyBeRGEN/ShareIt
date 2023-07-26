package ru.practicum.shareit.itemRequest.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ItemRequestTest {
    @Test
    void testConstructor() {
        ItemRequest actualItemRequest = new ItemRequest();
        LocalDateTime created = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualItemRequest.setCreated(created);
        actualItemRequest.setDescription("The characteristics of someone or something");
        actualItemRequest.setId(1L);
        HashSet<Item> items = new HashSet<>();
        actualItemRequest.setItems(items);
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");
        actualItemRequest.setRequester(requester);
        assertSame(created, actualItemRequest.getCreated());
        assertEquals("The characteristics of someone or something", actualItemRequest.getDescription());
        assertEquals(1L, actualItemRequest.getId().longValue());
        assertSame(items, actualItemRequest.getItems());
        assertSame(requester, actualItemRequest.getRequester());
    }
}