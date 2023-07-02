package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemServiceImplTest {
    @MockBean
    private BookingMapper bookingMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private CommentMapper commentMapper;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private ItemMapper itemMapper;

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @MockBean
    private ItemStorage itemStorage;

    @MockBean
    private UserStorage userStorage;

    @Test
    void testGetById() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong()))
                .thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage, atLeast(1)).getById(anyLong());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetById2() {
        ItemDtoWithBooking itemDtoWithBooking = new ItemDtoWithBooking();
        itemDtoWithBooking.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(itemDtoWithBooking);

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any()))
                .thenReturn(bookingList);
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new ArrayList<>());
        ItemDtoWithBooking actualById = itemServiceImpl.getById(1L, 1L);
        assertSame(itemDtoWithBooking, actualById);
        assertNull(actualById.getNextBooking());
        assertEquals(bookingList, actualById.getComments());
        assertNull(actualById.getLastBooking());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage, atLeast(1)).getById(anyLong());
        verify(bookingRepository).findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any());
        verify(commentRepository).findByItem_IdOrderByCreatedDesc(anyLong());
    }

    @Test
    void testGetById3() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NotValidException("An error occurred"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new ArrayList<>());
        assertThrows(NotValidException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getDescription();
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testGetById4() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NullPointerException("foo"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getDescription();
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testGetById5() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NotValidException("An error occurred"));
        when(item.getName()).thenReturn("");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);
        when(bookingRepository.findByItem_IdAndStatusOrderByStartDesc(anyLong(), Mockito.<StatusType>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findByItem_IdOrderByCreatedDesc(anyLong())).thenReturn(new ArrayList<>());
        assertThrows(NotValidException.class, () -> itemServiceImpl.getById(1L, 1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testGetAllItemsByUserId() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.getAllItemsByUserId(1L).isEmpty());
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId2() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.getAllItemsByUserId(1L));
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId3() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("start");
        item.setOwner(owner);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId4() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("start");
        item.setOwner(owner);

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("start");
        item2.setId(2L);
        item2.setName("Name");
        item2.setOwner(owner2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item2);
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId5() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user);
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("start");
        item.setOwner(owner);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item2);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(bookingList);
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(bookingMapper.toBookingItemDto(Mockito.<Booking>any()))
                .thenReturn(new BookingItemDto(1L, start, end, new ItemDto(), 1L));
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(item).getOwner();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
        verify(bookingMapper).toBookingItemDto(Mockito.<Booking>any());
    }

    @Test
    void testGetAllItemsByUserId6() {
        UserDto owner = new UserDto(1L, "start", "jane.doe@example.org");

        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        BookingItemDto lastBooking = new BookingItemDto(1L, start, end, new ItemDto(), 1L);

        LocalDateTime start2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        BookingItemDto nextBooking = new BookingItemDto(1L, start2, end2, new ItemDto(), 1L);

        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking(1L, "start",
                "The characteristics of someone or something", true, owner, lastBooking, nextBooking, new ArrayList<>()));

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("start");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user);
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("start");
        item.setOwner(owner2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner3);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item2);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(bookingList);
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        LocalDateTime start3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(bookingMapper.toBookingItemDto(Mockito.<Booking>any()))
                .thenReturn(new BookingItemDto(1L, start3, end3, new ItemDto(), 1L));
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(item).getOwner();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
        verify(bookingMapper).toBookingItemDto(Mockito.<Booking>any());
    }

    @Test
    void testGetAllItemsByUserId7() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user);
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("start");
        item.setOwner(owner);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("start");

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("start");
        item2.setOwner(owner2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item2);
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item3);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(bookingList);
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(bookingMapper.toBookingItemDto(Mockito.<Booking>any()))
                .thenReturn(new BookingItemDto(1L, start, end, new ItemDto(), 1L));
        assertEquals(2, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper, atLeast(1)).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(item).getOwner();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
        verify(bookingMapper).toBookingItemDto(Mockito.<Booking>any());
    }

    @Test
    void testGetAllItemsByUserId8() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Item item = mock(Item.class);
        when(item.getOwner()).thenReturn(user);
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("start");
        item.setOwner(owner);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item2);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);

        User booker2 = new User();
        booker2.setEmail("john.smith@example.org");
        booker2.setId(2L);
        booker2.setName("Name");

        User owner3 = new User();
        owner3.setEmail("john.smith@example.org");
        owner3.setId(2L);
        owner3.setName("Name");

        Item item3 = new Item();
        item3.setAvailable(false);
        item3.setDescription("start");
        item3.setId(2L);
        item3.setName("Name");
        item3.setOwner(owner3);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(2L);
        booking2.setItem(item3);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(StatusType.APPROVED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking2);
        bookingList.add(booking);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(bookingList);
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(bookingMapper.toBookingItemDto(Mockito.<Booking>any()))
                .thenReturn(new BookingItemDto(1L, start, end, new ItemDto(), 1L));
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(item).getOwner();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
        verify(bookingMapper, atLeast(1)).toBookingItemDto(Mockito.<Booking>any());
    }

    @Test
    void testCreate() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        when(itemStorage.create(Mockito.<Item>any())).thenReturn(item2);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        assertSame(itemDto, itemServiceImpl.create(new ItemDto(), 1L));
        verify(itemMapper).toDto(Mockito.<Item>any());
        verify(itemMapper).toEntity(Mockito.<ItemDto>any());
        verify(itemStorage).create(Mockito.<Item>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testCreate2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);
        when(itemStorage.create(Mockito.<Item>any())).thenThrow(new NotValidException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        assertThrows(NotValidException.class, () -> itemServiceImpl.create(new ItemDto(), 1L));
        verify(itemMapper).toEntity(Mockito.<ItemDto>any());
        verify(itemStorage).create(Mockito.<Item>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);
        when(itemStorage.update(Mockito.<Item>any())).thenReturn(item3);
        when(itemStorage.getById(anyLong())).thenReturn(item2);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        assertSame(itemDto, itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toDto(Mockito.<Item>any());
        verify(itemMapper).toEntity(Mockito.<ItemDto>any());
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).update(Mockito.<Item>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);
        when(itemStorage.update(Mockito.<Item>any())).thenReturn(item3);
        when(itemStorage.getById(anyLong())).thenReturn(item2);
        when(userStorage.getById(anyLong())).thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.update(new ItemDto(), 1L, 1L));
        verify(itemMapper).toEntity(Mockito.<ItemDto>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testDeleteById() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        doNothing().when(itemStorage).deleteById(anyLong());
        when(itemStorage.getById(anyLong())).thenReturn(item);
        itemServiceImpl.deleteById(1L);
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).deleteById(anyLong());
    }

    @Test
    void testDeleteById2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        doThrow(new NotValidException("An error occurred")).when(itemStorage).deleteById(anyLong());
        when(itemStorage.getById(anyLong())).thenReturn(item);
        assertThrows(NotValidException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getById(anyLong());
        verify(itemStorage).deleteById(anyLong());
    }

    @Test
    void testDeleteById3() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NotValidException("An error occurred"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        doNothing().when(itemStorage).deleteById(anyLong());
        when(itemStorage.getById(anyLong())).thenReturn(item);
        assertThrows(NotValidException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getDescription();
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testDeleteById4() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NullPointerException("foo"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        doNothing().when(itemStorage).deleteById(anyLong());
        when(itemStorage.getById(anyLong())).thenReturn(item);
        assertThrows(NotFoundException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getDescription();
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testDeleteById5() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenReturn("");
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        doNothing().when(itemStorage).deleteById(anyLong());
        when(itemStorage.getById(anyLong())).thenReturn(item);
        assertThrows(NotValidException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getDescription();
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testDeleteById6() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Item item = mock(Item.class);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        doNothing().when(itemStorage).deleteById(anyLong());
        when(itemStorage.getById(anyLong())).thenReturn(item);
        assertThrows(NotValidException.class, () -> itemServiceImpl.deleteById(1L));
        verify(itemStorage).getById(anyLong());
        verify(item).getName();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.<String>any());
        verify(item).setId(anyLong());
        verify(item).setName(Mockito.<String>any());
        verify(item).setOwner(Mockito.<User>any());
    }

    @Test
    void testSearchItemsByDescription() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.searchItemsByDescription("Text").isEmpty());
        verify(itemStorage).getAll();
    }

    @Test
    void testSearchItemsByDescription2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        assertTrue(itemServiceImpl.searchItemsByDescription("Text").isEmpty());
        verify(itemStorage).getAll();
    }

    @Test
    void testSearchItemsByDescription3() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("42");

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("Description");
        item2.setId(2L);
        item2.setName("42");
        item2.setOwner(owner2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item2);
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        assertTrue(itemServiceImpl.searchItemsByDescription("Text").isEmpty());
        verify(itemStorage).getAll();
    }

    @Test
    void testSearchItemsByDescription4() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.searchItemsByDescription("").isEmpty());
    }

    @Test
    void testSearchItemsByDescription5() {
        when(itemStorage.getAll()).thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.searchItemsByDescription("Text"));
        verify(itemStorage).getAll();
    }

    @Test
    void testCreateComment() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any())).thenReturn(Optional.of(new ArrayList<>()));

        User author = new User();
        author.setEmail("jane.doe@example.org");
        author.setId(1L);
        author.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        comment.setId(1L);
        comment.setItem(item2);
        comment.setText("Text");
        when(commentMapper.toEntity(Mockito.<CommentDto>any())).thenReturn(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        commentDto.setId(1L);
        commentDto.setText("Text");
        assertThrows(NotValidException.class, () -> itemServiceImpl.createComment(1L, 1L, commentDto));
        verify(itemStorage).getById(anyLong());
        verify(userStorage).getById(anyLong());
        verify(bookingRepository).findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any());
        verify(commentMapper).toEntity(Mockito.<CommentDto>any());
    }

    @Test
    void testCreateComment2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any())).thenReturn(Optional.of(new ArrayList<>()));
        when(commentMapper.toEntity(Mockito.<CommentDto>any())).thenThrow(new NotValidException("An error occurred"));

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        commentDto.setId(1L);
        commentDto.setText("Text");
        assertThrows(NotValidException.class, () -> itemServiceImpl.createComment(1L, 1L, commentDto));
        verify(commentMapper).toEntity(Mockito.<CommentDto>any());
    }

    @Test
    void testCreateComment3() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemStorage.getById(anyLong())).thenReturn(item);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("start");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("start");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("start");
        item2.setOwner(owner2);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item2);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        Optional<List<Booking>> ofResult = Optional.of(bookingList);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any())).thenReturn(ofResult);

        User author = new User();
        author.setEmail("jane.doe@example.org");
        author.setId(1L);
        author.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        comment.setId(1L);
        comment.setItem(item3);
        comment.setText("Text");
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(comment);

        User author2 = new User();
        author2.setEmail("jane.doe@example.org");
        author2.setId(1L);
        author2.setName("Name");

        User owner4 = new User();
        owner4.setEmail("jane.doe@example.org");
        owner4.setId(1L);
        owner4.setName("Name");

        Item item4 = new Item();
        item4.setAvailable(true);
        item4.setDescription("The characteristics of someone or something");
        item4.setId(1L);
        item4.setName("Name");
        item4.setOwner(owner4);

        Comment comment2 = new Comment();
        comment2.setAuthor(author2);
        comment2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        comment2.setId(1L);
        comment2.setItem(item4);
        comment2.setText("Text");
        when(commentMapper.toDto(Mockito.<Comment>any())).thenThrow(new NotValidException("An error occurred"));
        when(commentMapper.toEntity(Mockito.<CommentDto>any())).thenReturn(comment2);

        CommentDto commentDto = new CommentDto();
        commentDto.setAuthorName("JaneDoe");
        commentDto.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        commentDto.setId(1L);
        commentDto.setText("Text");
        assertThrows(NotValidException.class, () -> itemServiceImpl.createComment(1L, 1L, commentDto));
        verify(itemStorage).getById(anyLong());
        verify(userStorage).getById(anyLong());
        verify(bookingRepository).findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any());
        verify(commentMapper).toEntity(Mockito.<CommentDto>any());
    }
}

