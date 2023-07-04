package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.storage.BookingStorage;
import ru.practicum.shareit.booking.utils.AccessLevel;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BookingServiceImplTest {
    @MockBean
    private BookingMapper bookingMapper;

    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @MockBean
    private BookingStorage bookingStorage;

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Test
    void testCreatedBookingRequest() {
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
        when(bookingMapper.toEntity(Mockito.<BookingInputDto>any())).thenReturn(booking);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);
        when(itemMapper.toEntityFromBooking(Mockito.<ItemDtoWithBooking>any())).thenReturn(item2);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemService.getById(anyLong(), anyLong())).thenReturn(new ItemDtoWithBooking());
        assertThrows(AccessException.class, () -> bookingServiceImpl.createdBookingRequest(new BookingInputDto(), 1L));
        verify(bookingMapper).toEntity(Mockito.<BookingInputDto>any());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(itemMapper).toEntityFromBooking(Mockito.<ItemDtoWithBooking>any());
        verify(userService).getById(anyLong());
        verify(itemService).getById(anyLong(), anyLong());
    }

    @Test
    void testCreatedBookingRequest2() {
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
        when(bookingMapper.toEntity(Mockito.<BookingInputDto>any())).thenReturn(booking);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);
        when(itemMapper.toEntityFromBooking(Mockito.<ItemDtoWithBooking>any())).thenReturn(item2);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemService.getById(anyLong(), anyLong())).thenThrow(new AccessException("An error occurred"));
        assertThrows(AccessException.class, () -> bookingServiceImpl.createdBookingRequest(new BookingInputDto(), 1L));
        verify(bookingMapper).toEntity(Mockito.<BookingInputDto>any());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(userService).getById(anyLong());
        verify(itemService).getById(anyLong(), anyLong());
    }

    @Test
    void testUpdateBookingRequest() {
        BookingOutputDto bookingOutputDto = new BookingOutputDto();
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(bookingOutputDto);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(StatusType.WAITING);
        when(bookingStorage.saveBooking(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertSame(bookingOutputDto, bookingServiceImpl.updateBookingRequest(1L, 1L, true, AccessLevel.OWNER));
        verify(bookingMapper).toDto(Mockito.<Booking>any());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(bookingStorage).findBookingById(anyLong());
        verify(bookingStorage).saveBooking(Mockito.<Booking>any());
        verify(userService).getById(anyLong());
    }

    @Test
    void testUpdateBookingRequest2() {
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(new BookingOutputDto());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(StatusType.WAITING);
        when(bookingStorage.saveBooking(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenThrow(new AccessException("An error occurred"));
        assertThrows(AccessException.class,
                () -> bookingServiceImpl.updateBookingRequest(1L, 1L, true, AccessLevel.OWNER));
        verify(userService).getById(anyLong());
    }

    @Test
    void testUpdateBookingRequest3() {
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(new BookingOutputDto());
        User user = mock(User.class);
        when(user.getId()).thenReturn(-1L);
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(anyLong());
        doNothing().when(user).setName(Mockito.<String>any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(StatusType.WAITING);
        when(bookingStorage.saveBooking(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertThrows(AccessException.class,
                () -> bookingServiceImpl.updateBookingRequest(1L, 1L, true, AccessLevel.OWNER));
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(user).getId();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setId(anyLong());
        verify(user).setName(Mockito.<String>any());
        verify(bookingStorage).findBookingById(anyLong());
        verify(userService).getById(anyLong());
    }

    @Test
    void testUpdateBookingRequest4() {
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(new BookingOutputDto());
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(anyLong());
        doNothing().when(user).setName(Mockito.<String>any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);
        Booking booking = mock(Booking.class);
        when(booking.getStatus()).thenThrow(new AccessException("An error occurred"));
        when(booking.getItem()).thenReturn(item2);
        doNothing().when(booking).setBooker(Mockito.<User>any());
        doNothing().when(booking).setEnd(Mockito.<LocalDateTime>any());
        doNothing().when(booking).setId(anyLong());
        doNothing().when(booking).setItem(Mockito.<Item>any());
        doNothing().when(booking).setStart(Mockito.<LocalDateTime>any());
        doNothing().when(booking).setStatus(Mockito.<StatusType>any());
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        ItemRequest request3 = new ItemRequest();
        request3.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setItems(new HashSet<>());
        request3.setRequester(requester3);

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);
        item3.setRequest(request3);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item3);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(StatusType.WAITING);
        when(bookingStorage.saveBooking(Mockito.<Booking>any())).thenReturn(booking2);
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertThrows(AccessException.class,
                () -> bookingServiceImpl.updateBookingRequest(1L, 1L, true, AccessLevel.OWNER));
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(user).getId();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setId(anyLong());
        verify(user).setName(Mockito.<String>any());
        verify(bookingStorage).findBookingById(anyLong());
        verify(booking).getStatus();
        verify(booking).getItem();
        verify(booking).setBooker(Mockito.<User>any());
        verify(booking).setEnd(Mockito.<LocalDateTime>any());
        verify(booking).setId(anyLong());
        verify(booking).setItem(Mockito.<Item>any());
        verify(booking).setStart(Mockito.<LocalDateTime>any());
        verify(booking).setStatus(Mockito.<StatusType>any());
        verify(userService).getById(anyLong());
    }

    @Test
    void testFindBookingById() {
        BookingOutputDto bookingOutputDto = new BookingOutputDto();
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(bookingOutputDto);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertSame(bookingOutputDto, bookingServiceImpl.findBookingById(1L, 1L, AccessLevel.OWNER));
        verify(bookingMapper).toDto(Mockito.<Booking>any());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(bookingStorage).findBookingById(anyLong());
        verify(userService).getById(anyLong());
    }

    @Test
    void testFindBookingById2() {
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(new BookingOutputDto());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenThrow(new AccessException("An error occurred"));
        assertThrows(AccessException.class, () -> bookingServiceImpl.findBookingById(1L, 1L, AccessLevel.OWNER));
        verify(userService).getById(anyLong());
    }

    @Test
    void testFindBookingById3() {
        when(bookingMapper.toDto(Mockito.<Booking>any())).thenReturn(new BookingOutputDto());
        User user = mock(User.class);
        when(user.getId()).thenReturn(-1L);
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setId(anyLong());
        doNothing().when(user).setName(Mockito.<String>any());
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);

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
        when(bookingStorage.findBookingById(anyLong())).thenReturn(booking);
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertThrows(AccessException.class, () -> bookingServiceImpl.findBookingById(1L, 1L, AccessLevel.OWNER));
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(user).getId();
        verify(user).setEmail(Mockito.<String>any());
        verify(user).setId(anyLong());
        verify(user).setName(Mockito.<String>any());
        verify(bookingStorage).findBookingById(anyLong());
        verify(userService).getById(anyLong());
    }

    @Test
    void testGetBookingsByUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);
        when(bookingStorage.getBookingsByUser(Mockito.<Pageable>any(), Mockito.<User>any(), Mockito.<Sort>any(),
                Mockito.<State>any())).thenReturn(new ArrayList<>());
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertTrue(bookingServiceImpl.getBookingsByUser(null, 1L, State.ALL).isEmpty());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(bookingStorage).getBookingsByUser(Mockito.<Pageable>any(), Mockito.<User>any(), Mockito.<Sort>any(),
                Mockito.<State>any());
        verify(userService).getById(anyLong());
    }

    @Test
    void testGetBookingsByUser2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);
        when(bookingStorage.getBookingsByUser(Mockito.<Pageable>any(), Mockito.<User>any(), Mockito.<Sort>any(),
                Mockito.<State>any())).thenReturn(new ArrayList<>());
        when(userService.getById(anyLong())).thenThrow(new AccessException("An error occurred"));
        assertThrows(AccessException.class, () -> bookingServiceImpl.getBookingsByUser(null, 1L, State.ALL));
        verify(userService).getById(anyLong());
    }

    @Test
    void testGetBookingsForOwner() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);
        when(bookingStorage.getBookingsForOwner(Mockito.<Pageable>any(), Mockito.<User>any(), Mockito.<Sort>any(),
                Mockito.<State>any())).thenReturn(new ArrayList<>());
        when(userService.getById(anyLong())).thenReturn(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertTrue(bookingServiceImpl.getBookingsForOwner(null, 1L, State.ALL).isEmpty());
        verify(userMapper).toEntity(Mockito.<UserDto>any());
        verify(bookingStorage).getBookingsForOwner(Mockito.<Pageable>any(), Mockito.<User>any(), Mockito.<Sort>any(),
                Mockito.<State>any());
        verify(userService).getById(anyLong());
    }

    @Test
    void testGetBookingsForOwner2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userMapper.toEntity(Mockito.<UserDto>any())).thenReturn(user);
        when(bookingStorage.getBookingsForOwner(Mockito.<Pageable>any(), Mockito.<User>any(), Mockito.<Sort>any(),
                Mockito.<State>any())).thenReturn(new ArrayList<>());
        when(userService.getById(anyLong())).thenThrow(new AccessException("An error occurred"));
        assertThrows(AccessException.class, () -> bookingServiceImpl.getBookingsForOwner(null, 1L, State.ALL));
        verify(userService).getById(anyLong());
    }
}