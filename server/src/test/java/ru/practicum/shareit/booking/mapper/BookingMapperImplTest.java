package ru.practicum.shareit.booking.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BookingMapperImplTest {
    @Autowired
    private BookingMapperImpl bookingMapperImpl;

    @MockBean
    private SqlDataSourceScriptDatabaseInitializer sqlDataSourceScriptDatabaseInitializer;

    @Test
    void testToEntity() {
        Booking actualToEntityResult = bookingMapperImpl.toEntity(new BookingInputDto());
        assertNull(actualToEntityResult.getStart());
        assertNull(actualToEntityResult.getEnd());
        assertEquals(0L, actualToEntityResult.getItem().getId());
    }

    @Test
    void testToEntity2() {
        assertNull(bookingMapperImpl.toEntity(null));
    }

    @Test
    void testToDto() {
        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

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

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);
        BookingOutputDto actualToDtoResult = bookingMapperImpl.toDto(booking);
        assertEquals(StatusType.WAITING, actualToDtoResult.getStatus());
        assertEquals("00:00", actualToDtoResult.getEnd().toLocalTime().toString());
        assertEquals("00:00", actualToDtoResult.getStart().toLocalTime().toString());
        assertEquals(1L, actualToDtoResult.getId());
        ItemDto item2 = actualToDtoResult.getItem();
        assertEquals(1L, item2.getId().longValue());
        assertEquals("The characteristics of someone or something", item2.getDescription());
        assertTrue(item2.getAvailable());
        UserDto booker2 = actualToDtoResult.getBooker();
        UserDto owner2 = item2.getOwner();
        assertEquals(booker2, owner2);
        assertEquals("jane.doe@example.org", booker2.getEmail());
        assertEquals(1L, item2.getRequestId().longValue());
        assertEquals("Name", booker2.getName());
        assertEquals(1L, booker2.getId());
        assertEquals("Name", item2.getName());
        assertEquals("Name", owner2.getName());
        assertEquals(1L, owner2.getId());
        assertEquals("jane.doe@example.org", owner2.getEmail());
    }

    @Test
    void testToBookingItemDto() {
        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

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

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);
        BookingItemDto actualToBookingItemDtoResult = bookingMapperImpl.toBookingItemDto(booking);
        assertEquals(1L, actualToBookingItemDtoResult.getBookerId());
        assertEquals("00:00", actualToBookingItemDtoResult.getStart().toLocalTime().toString());
        assertEquals("1970-01-01", actualToBookingItemDtoResult.getEnd().toLocalDate().toString());
        assertEquals(1L, actualToBookingItemDtoResult.getId());
        ItemDto item2 = actualToBookingItemDtoResult.getItem();
        assertEquals(1L, item2.getId().longValue());
        assertEquals("The characteristics of someone or something", item2.getDescription());
        assertTrue(item2.getAvailable());
        assertEquals("Name", item2.getName());
        assertEquals(1L, item2.getRequestId().longValue());
        UserDto owner2 = item2.getOwner();
        assertEquals(1L, owner2.getId());
        assertEquals("jane.doe@example.org", owner2.getEmail());
        assertEquals("Name", owner2.getName());
    }

    @Test
    void testBookingInputDtoToItem() {
        assertEquals(0L, bookingMapperImpl.bookingInputDtoToItem(new BookingInputDto()).getId());
        assertNull(bookingMapperImpl.bookingInputDtoToItem(null));
    }
}