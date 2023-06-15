package ru.practicum.shareit.item.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

class ItemTest {
    @Test
    void testCanEqual() {
        assertFalse((new Item()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        Item item = new Item();
        assertTrue(item.canEqual(new Item()));
    }

    @Test
    void testConstructor() {
        Item actualItem = new Item();
        actualItem.setAvailable(true);
        actualItem.setDescription("The characteristics of someone or something");
        actualItem.setId(1L);
        actualItem.setName("Name");
        User owner = new User(1L, "Name", "jane.doe@example.org");

        actualItem.setOwner(owner);
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        User requester = new User(1L, "Name", "jane.doe@example.org");

        request.setRequester(requester);
        actualItem.setRequest(request);
        String actualToStringResult = actualItem.toString();
        assertTrue(actualItem.getAvailable());
        assertEquals("The characteristics of someone or something", actualItem.getDescription());
        assertEquals(1L, actualItem.getId());
        assertEquals("Name", actualItem.getName());
        User owner2 = actualItem.getOwner();
        assertSame(owner, owner2);
        assertEquals(requester, owner2);
        assertSame(request, actualItem.getRequest());
        assertEquals("Item(id=1, name=Name, description=The characteristics of someone or something, available=true,"
                + " owner=User(id=1, name=Name, email=jane.doe@example.org), request=ItemRequest(id=1, description=The"
                + " characteristics of someone or something, requester=User(id=1, name=Name, email=jane.doe@example.org),"
                + " created=1970-01-01T00:00))", actualToStringResult);
    }

    @Test
    void testConstructor2() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        User requester = new User(1L, "Name", "jane.doe@example.org");

        request.setRequester(requester);
        Item actualItem = new Item(1L, "Name", "The characteristics of someone or something", true, owner, request);

        assertTrue(actualItem.getAvailable());
        assertSame(request, actualItem.getRequest());
        assertEquals(requester, actualItem.getOwner());
        assertEquals("The characteristics of someone or something", actualItem.getDescription());
        assertEquals("Name", actualItem.getName());
        assertEquals(1L, actualItem.getId());
    }

    @Test
    void testEquals() {
        assertNotEquals(new Item(), null);
        assertNotEquals(new Item(), "Different type to Item");
    }

    @Test
    void testEquals2() {
        Item item = new Item();
        assertEquals(item, item);
        int expectedHashCodeResult = item.hashCode();
        assertEquals(expectedHashCodeResult, item.hashCode());
    }

    @Test
    void testEquals3() {
        Item item = new Item();
        Item item2 = new Item();
        assertEquals(item, item2);
        int expectedHashCodeResult = item.hashCode();
        assertEquals(expectedHashCodeResult, item2.hashCode());
    }

    @Test
    void testEquals4() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        Item item = new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest());
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals5() {
        Item item = new Item();
        item.setName("Name");
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals6() {
        Item item = new Item();
        item.setDescription("The characteristics of someone or something");
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals7() {
        Item item = new Item();
        item.setAvailable(true);
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals8() {
        Item item = new Item();
        item.setOwner(new User(1L, "Name", "jane.doe@example.org"));
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals9() {
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        request.setRequester(new User(1L, "Name", "jane.doe@example.org"));

        Item item = new Item();
        item.setRequest(request);
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals10() {
        User owner = mock(User.class);
        Item item = new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest());
        assertNotEquals(item, new Item());
    }

    @Test
    void testEquals11() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        Item item = new Item(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest());
        User owner2 = new User(1L, "Name", "jane.doe@example.org");

        Item item2 = new Item(1L, "Name", "The characteristics of someone or something", true, owner2, new ItemRequest());

        assertEquals(item, item2);
        int expectedHashCodeResult = item.hashCode();
        assertEquals(expectedHashCodeResult, item2.hashCode());
    }

    @Test
    void testEquals12() {
        Item item = new Item();

        Item item2 = new Item();
        item2.setName("Name");
        assertNotEquals(item, item2);
    }

    @Test
    void testEquals13() {
        Item item = new Item();

        Item item2 = new Item();
        item2.setDescription("The characteristics of someone or something");
        assertNotEquals(item, item2);
    }

    @Test
    void testEquals14() {
        Item item = new Item();

        Item item2 = new Item();
        item2.setAvailable(true);
        assertNotEquals(item, item2);
    }

    @Test
    void testEquals15() {
        Item item = new Item();

        Item item2 = new Item();
        item2.setOwner(new User(1L, "Name", "jane.doe@example.org"));
        assertNotEquals(item, item2);
    }

    @Test
    void testEquals16() {
        Item item = new Item();

        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        request.setRequester(new User(1L, "Name", "jane.doe@example.org"));

        Item item2 = new Item();
        item2.setRequest(request);
        assertNotEquals(item, item2);
    }
}

