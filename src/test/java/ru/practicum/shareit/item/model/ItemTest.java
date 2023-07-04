package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    void testConstructor() {
        Item actualItem = new Item();
        actualItem.setAvailable(true);
        actualItem.setDescription("The characteristics of someone or something");
        actualItem.setId(1L);
        actualItem.setName("Name");
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        actualItem.setOwner(owner);
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setItems(new HashSet<>());
        request.setRequester(requester);
        actualItem.setRequest(request);
        assertTrue(actualItem.getAvailable());
        assertEquals("The characteristics of someone or something", actualItem.getDescription());
        assertEquals(1L, actualItem.getId());
        assertEquals("Name", actualItem.getName());
        assertSame(owner, actualItem.getOwner());
        assertSame(request, actualItem.getRequest());
    }
}