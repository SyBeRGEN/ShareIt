package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {ItemMapperImpl.class})
@ExtendWith(SpringExtension.class)
class ItemMapperImplTest {
    @Autowired
    private ItemMapperImpl itemMapperImpl;

    @Test
    void testToEntity() {
        Item actualToEntityResult = itemMapperImpl.toEntity(new ItemDto());
        assertNull(actualToEntityResult.getAvailable());
        assertNull(actualToEntityResult.getOwner());
        assertNull(actualToEntityResult.getName());
        assertNull(actualToEntityResult.getDescription());
    }

    @Test
    void testToEntity2() {
        ItemDto itemDto = new ItemDto();
        itemDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        Item actualToEntityResult = itemMapperImpl.toEntity(itemDto);
        assertNull(actualToEntityResult.getAvailable());
        assertNull(actualToEntityResult.getName());
        assertNull(actualToEntityResult.getDescription());
        User owner = actualToEntityResult.getOwner();
        assertEquals("jane.doe@example.org", owner.getEmail());
        assertEquals(1L, owner.getId());
        assertEquals("Name", owner.getName());
    }

    @Test
    void testToEntity3() {
        assertNull(itemMapperImpl.toEntity(null));
    }

    @Test
    void testToEntity4() {
        Item actualToEntityResult = itemMapperImpl.toEntity(new ItemDto(1L, "Name",
                "The characteristics of someone or something", true, new UserDto(1L, "Name", "jane.doe@example.org"), 1L));
        assertTrue(actualToEntityResult.getAvailable());
        assertEquals("Name", actualToEntityResult.getName());
        assertEquals("The characteristics of someone or something", actualToEntityResult.getDescription());
        assertEquals(1L, actualToEntityResult.getId());
        User owner = actualToEntityResult.getOwner();
        assertEquals("jane.doe@example.org", owner.getEmail());
        assertEquals(1L, owner.getId());
        assertEquals("Name", owner.getName());
    }

    @Test
    void testToDto() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

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

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        ItemDto actualToDtoResult = itemMapperImpl.toDto(item);
        assertTrue(actualToDtoResult.getAvailable());
        assertEquals(1L, actualToDtoResult.getRequestId().longValue());
        assertEquals("Name", actualToDtoResult.getName());
        assertEquals("The characteristics of someone or something", actualToDtoResult.getDescription());
        assertEquals(1L, actualToDtoResult.getId().longValue());
        UserDto owner2 = actualToDtoResult.getOwner();
        assertEquals("jane.doe@example.org", owner2.getEmail());
        assertEquals(1L, owner2.getId());
        assertEquals("Name", owner2.getName());
    }

    @Test
    void testToDto2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(null);
        request.setItems(new HashSet<>());
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        ItemDto actualToDtoResult = itemMapperImpl.toDto(item);
        assertTrue(actualToDtoResult.getAvailable());
        assertNull(actualToDtoResult.getRequestId());
        assertEquals("Name", actualToDtoResult.getName());
        assertEquals("The characteristics of someone or something", actualToDtoResult.getDescription());
        assertEquals(1L, actualToDtoResult.getId().longValue());
        UserDto owner2 = actualToDtoResult.getOwner();
        assertEquals("jane.doe@example.org", owner2.getEmail());
        assertEquals(1L, owner2.getId());
        assertEquals("Name", owner2.getName());
    }

    @Test
    void testToDtoWithBooking() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

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

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        ItemDtoWithBooking actualToDtoWithBookingResult = itemMapperImpl.toDtoWithBooking(item);
        assertTrue(actualToDtoWithBookingResult.getAvailable());
        assertTrue(actualToDtoWithBookingResult.getComments().isEmpty());
        assertEquals(1L, actualToDtoWithBookingResult.getId().longValue());
        assertEquals("Name", actualToDtoWithBookingResult.getName());
        assertEquals("The characteristics of someone or something", actualToDtoWithBookingResult.getDescription());
        UserDto owner2 = actualToDtoWithBookingResult.getOwner();
        assertEquals("Name", owner2.getName());
        assertEquals(1L, owner2.getId());
        assertEquals("jane.doe@example.org", owner2.getEmail());
    }

    @Test
    void testToEntityFromBooking() {
        Item actualToEntityFromBookingResult = itemMapperImpl.toEntityFromBooking(new ItemDtoWithBooking());
        assertNull(actualToEntityFromBookingResult.getAvailable());
        assertNull(actualToEntityFromBookingResult.getOwner());
        assertNull(actualToEntityFromBookingResult.getName());
        assertNull(actualToEntityFromBookingResult.getDescription());
    }

    @Test
    void testToEntityFromBooking2() {
        ItemDtoWithBooking itemDtoWithBooking = new ItemDtoWithBooking();
        itemDtoWithBooking.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        Item actualToEntityFromBookingResult = itemMapperImpl.toEntityFromBooking(itemDtoWithBooking);
        assertNull(actualToEntityFromBookingResult.getAvailable());
        assertNull(actualToEntityFromBookingResult.getName());
        assertNull(actualToEntityFromBookingResult.getDescription());
        User owner = actualToEntityFromBookingResult.getOwner();
        assertEquals("jane.doe@example.org", owner.getEmail());
        assertEquals(1L, owner.getId());
        assertEquals("Name", owner.getName());
    }

    @Test
    void testToEntityFromBooking3() {
        assertNull(itemMapperImpl.toEntityFromBooking(null));
    }

    @Test
    void testToEntityFromBooking4() {
        UserDto owner = new UserDto(1L, "Name", "jane.doe@example.org");

        BookingItemDto lastBooking = new BookingItemDto();
        BookingItemDto nextBooking = new BookingItemDto();
        Item actualToEntityFromBookingResult = itemMapperImpl.toEntityFromBooking(new ItemDtoWithBooking(1L, "Name",
                "The characteristics of someone or something", true, owner, lastBooking, nextBooking, new ArrayList<>()));
        assertTrue(actualToEntityFromBookingResult.getAvailable());
        assertEquals("Name", actualToEntityFromBookingResult.getName());
        assertEquals("The characteristics of someone or something", actualToEntityFromBookingResult.getDescription());
        assertEquals(1L, actualToEntityFromBookingResult.getId());
        User owner2 = actualToEntityFromBookingResult.getOwner();
        assertEquals("jane.doe@example.org", owner2.getEmail());
        assertEquals(1L, owner2.getId());
        assertEquals("Name", owner2.getName());
    }

    @Test
    void testUserDtoToUser() {
        User actualUserDtoToUserResult = itemMapperImpl.userDtoToUser(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualUserDtoToUserResult.getEmail());
        assertEquals("Name", actualUserDtoToUserResult.getName());
        assertEquals(1L, actualUserDtoToUserResult.getId());
    }

    @Test
    void testUserDtoToUser2() {
        assertNull(itemMapperImpl.userDtoToUser(null));
    }

    @Test
    void testUserToUserDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto actualUserToUserDtoResult = itemMapperImpl.userToUserDto(user);
        assertEquals("jane.doe@example.org", actualUserToUserDtoResult.getEmail());
        assertEquals("Name", actualUserToUserDtoResult.getName());
        assertEquals(1L, actualUserToUserDtoResult.getId());
    }
}