package ru.practicum.shareit.item.dto;

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

class ItemDtoTest {
    @Test
    void testCanEqual() {
        assertFalse((new ItemDto()).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        ItemDto itemDto = new ItemDto();
        assertTrue(itemDto.canEqual(new ItemDto()));
    }

    @Test
    void testConstructor() {
        ItemDto actualItemDto = new ItemDto();
        actualItemDto.setAvailable(true);
        actualItemDto.setDescription("The characteristics of someone or something");
        actualItemDto.setId(1L);
        actualItemDto.setName("Name");
        User owner = new User(1L, "Name", "jane.doe@example.org");

        actualItemDto.setOwner(owner);
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        User requester = new User(1L, "Name", "jane.doe@example.org");

        request.setRequester(requester);
        actualItemDto.setRequest(request);
        String actualToStringResult = actualItemDto.toString();
        assertTrue(actualItemDto.getAvailable());
        assertEquals("The characteristics of someone or something", actualItemDto.getDescription());
        assertEquals(1L, actualItemDto.getId().longValue());
        assertEquals("Name", actualItemDto.getName());
        User owner2 = actualItemDto.getOwner();
        assertSame(owner, owner2);
        assertEquals(requester, owner2);
        assertSame(request, actualItemDto.getRequest());
        assertEquals("ItemDto(id=1, name=Name, description=The characteristics of someone or something, available=true,"
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
        ItemDto actualItemDto = new ItemDto(1L, "Name", "The characteristics of someone or something", true, owner,
                request);
        actualItemDto.setAvailable(true);
        actualItemDto.setDescription("The characteristics of someone or something");
        actualItemDto.setId(1L);
        actualItemDto.setName("Name");
        User owner2 = new User(1L, "Name", "jane.doe@example.org");

        actualItemDto.setOwner(owner2);
        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1);
        User requester2 = new User(1L, "Name", "jane.doe@example.org");

        request2.setRequester(requester2);
        actualItemDto.setRequest(request2);
        String actualToStringResult = actualItemDto.toString();
        assertTrue(actualItemDto.getAvailable());
        assertEquals("The characteristics of someone or something", actualItemDto.getDescription());
        assertEquals(1L, actualItemDto.getId().longValue());
        assertEquals("Name", actualItemDto.getName());
        User owner3 = actualItemDto.getOwner();
        assertSame(owner2, owner3);
        assertEquals(owner, owner3);
        assertEquals(requester, owner3);
        assertEquals(requester2, owner3);
        ItemRequest request3 = actualItemDto.getRequest();
        assertSame(request2, request3);
        assertEquals(request, request3);
        assertEquals("ItemDto(id=1, name=Name, description=The characteristics of someone or something, available=true,"
                + " owner=User(id=1, name=Name, email=jane.doe@example.org), request=ItemRequest(id=1, description=The"
                + " characteristics of someone or something, requester=User(id=1, name=Name, email=jane.doe@example.org),"
                + " created=1970-01-01T00:00))", actualToStringResult);
    }

    @Test
    void testEquals() {
        assertNotEquals(new ItemDto(), null);
        assertNotEquals(new ItemDto(), "Different type to ItemDto");
    }

    @Test
    void testEquals2() {
        ItemDto itemDto = new ItemDto();
        assertEquals(itemDto, itemDto);
        int expectedHashCodeResult = itemDto.hashCode();
        assertEquals(expectedHashCodeResult, itemDto.hashCode());
    }

    @Test
    void testEquals3() {
        ItemDto itemDto = new ItemDto();
        ItemDto itemDto2 = new ItemDto();
        assertEquals(itemDto, itemDto2);
        int expectedHashCodeResult = itemDto.hashCode();
        assertEquals(expectedHashCodeResult, itemDto2.hashCode());
    }

    @Test
    void testEquals4() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        ItemDto itemDto = new ItemDto(1L, "Name", "The characteristics of someone or something", true, owner,
                new ItemRequest());
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals5() {
        ItemDto itemDto = new ItemDto();
        User owner = new User(1L, "Name", "jane.doe@example.org");

        assertNotEquals(itemDto,
                new ItemDto(1L, "Name", "The characteristics of someone or something", true, owner, new ItemRequest()));
    }

    @Test
    void testEquals6() {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Name");
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals7() {
        ItemDto itemDto = new ItemDto();
        itemDto.setDescription("The characteristics of someone or something");
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals8() {
        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals9() {
        ItemDto itemDto = new ItemDto();
        itemDto.setOwner(new User(1L, "Name", "jane.doe@example.org"));
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals10() {
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        request.setRequester(new User(1L, "Name", "jane.doe@example.org"));

        ItemDto itemDto = new ItemDto();
        itemDto.setRequest(request);
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals11() {
        User owner = mock(User.class);
        ItemDto itemDto = new ItemDto(1L, "Name", "The characteristics of someone or something", true, owner,
                new ItemRequest());
        assertNotEquals(itemDto, new ItemDto());
    }

    @Test
    void testEquals12() {
        User owner = new User(1L, "Name", "jane.doe@example.org");

        ItemDto itemDto = new ItemDto(1L, "Name", "The characteristics of someone or something", true, owner,
                new ItemRequest());
        User owner2 = new User(1L, "Name", "jane.doe@example.org");

        ItemDto itemDto2 = new ItemDto(1L, "Name", "The characteristics of someone or something", true, owner2,
                new ItemRequest());

        assertEquals(itemDto, itemDto2);
        int expectedHashCodeResult = itemDto.hashCode();
        assertEquals(expectedHashCodeResult, itemDto2.hashCode());
    }

    @Test
    void testEquals13() {
        ItemDto itemDto = new ItemDto();

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setName("Name");
        assertNotEquals(itemDto, itemDto2);
    }

    @Test
    void testEquals14() {
        ItemDto itemDto = new ItemDto();

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setDescription("The characteristics of someone or something");
        assertNotEquals(itemDto, itemDto2);
    }

    @Test
    void testEquals15() {
        ItemDto itemDto = new ItemDto();

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setAvailable(true);
        assertNotEquals(itemDto, itemDto2);
    }

    @Test
    void testEquals16() {
        ItemDto itemDto = new ItemDto();

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setOwner(new User(1L, "Name", "jane.doe@example.org"));
        assertNotEquals(itemDto, itemDto2);
    }

    @Test
    void testEquals17() {
        ItemDto itemDto = new ItemDto();

        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1);
        request.setRequester(new User(1L, "Name", "jane.doe@example.org"));

        ItemDto itemDto2 = new ItemDto();
        itemDto2.setRequest(request);
        assertNotEquals(itemDto, itemDto2);
    }
}

