package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
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
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.repository.ItemRequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
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

    @MockBean
    private ItemRequestRepository itemRequestRepository;

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
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NotValidException("An error occurred"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
    }

    @Test
    void testGetById4() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

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
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NullPointerException("foo"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
    }

    @Test
    void testGetById5() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

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
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NotValidException("An error occurred"));
        when(item.getName()).thenReturn("");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
    }

    @Test
    void testGetAllItemsByUserId() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        assertTrue(itemServiceImpl.getAllItemsByUserId(1L).isEmpty());
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId2() {
        when(itemStorage.getAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.getAllItemsByUserId(1L));
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId3() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("start");

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
        item.setName("start");
        item.setOwner(owner);
        item.setRequest(request);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        when(bookingRepository.findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testGetAllItemsByUserId4() {
        when(itemMapper.toDtoWithBooking(Mockito.<Item>any())).thenReturn(new ItemDtoWithBooking());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("start");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("start");

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
        item.setName("start");
        item.setOwner(owner);
        item.setRequest(request);

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("john.smith@example.org");
        requester2.setId(2L);
        requester2.setName("Name");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("start");
        request2.setId(2L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("start");
        item2.setId(2L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(item2);
        itemList.add(item);
        when(itemStorage.getAll()).thenReturn(itemList);
        when(bookingRepository.findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(commentRepository.findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any()))
                .thenReturn(new ArrayList<>());
        assertEquals(1, itemServiceImpl.getAllItemsByUserId(1L).size());
        verify(itemMapper).toDtoWithBooking(Mockito.<Item>any());
        verify(itemStorage).getAll();
        verify(bookingRepository).findAllByOwnerIdWithoutPaging(anyLong(), Mockito.<Sort>any());
        verify(commentRepository).findAllByItemIdInOrderByCreatedDesc(Mockito.<List<Long>>any());
    }

    @Test
    void testCreate() {
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
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);

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
        ItemDto itemDto = new ItemDto();
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(itemDto);
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);

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
        when(itemMapper.toDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(itemMapper.toEntity(Mockito.<ItemDto>any())).thenReturn(item);

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
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NotValidException("An error occurred"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
    }

    @Test
    void testDeleteById4() {
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
        Item item = mock(Item.class);
        when(item.getDescription()).thenThrow(new NullPointerException("foo"));
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
    }

    @Test
    void testDeleteById5() {
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
        Item item = mock(Item.class);
        when(item.getDescription()).thenReturn("");
        when(item.getName()).thenReturn("Name");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
    }

    @Test
    void testDeleteById6() {
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
        Item item = mock(Item.class);
        when(item.getDescription()).thenReturn("The characteristics of someone or something");
        when(item.getName()).thenReturn("");
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.<String>any());
        doNothing().when(item).setId(anyLong());
        doNothing().when(item).setName(Mockito.<String>any());
        doNothing().when(item).setOwner(Mockito.<User>any());
        doNothing().when(item).setRequest(Mockito.<ItemRequest>any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
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
        verify(item).setRequest(Mockito.<ItemRequest>any());
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
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("42");

        User requester2 = new User();
        requester2.setEmail("john.smith@example.org");
        requester2.setId(2L);
        requester2.setName("42");

        ItemRequest request2 = new ItemRequest();
        request2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("Description");
        request2.setId(2L);
        request2.setItems(new HashSet<>());
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("Description");
        item2.setId(2L);
        item2.setName("42");
        item2.setOwner(owner2);
        item2.setRequest(request2);

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

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        comment.setId(1L);
        comment.setItem(item2);
        comment.setText("Text");
        when(commentMapper.toEntity(Mockito.<CommentDto>any())).thenReturn(comment);
        assertThrows(NotValidException.class, () -> itemServiceImpl.createComment(1L, 1L, new CommentDto()));
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
        when(itemStorage.getById(anyLong())).thenReturn(item);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        when(userStorage.getById(anyLong())).thenReturn(user);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any())).thenReturn(Optional.of(new ArrayList<>()));
        when(commentMapper.toEntity(Mockito.<CommentDto>any())).thenThrow(new NotValidException("An error occurred"));
        assertThrows(NotValidException.class, () -> itemServiceImpl.createComment(1L, 1L, new CommentDto()));
        verify(commentMapper).toEntity(Mockito.<CommentDto>any());
    }

    @Test
    void testCreateComment3() {
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

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("start");

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
        item2.setName("start");
        item2.setOwner(owner2);
        item2.setRequest(request2);

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

        User requester4 = new User();
        requester4.setEmail("jane.doe@example.org");
        requester4.setId(1L);
        requester4.setName("Name");

        ItemRequest request4 = new ItemRequest();
        request4.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request4.setDescription("The characteristics of someone or something");
        request4.setId(1L);
        request4.setItems(new HashSet<>());
        request4.setRequester(requester4);

        Item item4 = new Item();
        item4.setAvailable(true);
        item4.setDescription("The characteristics of someone or something");
        item4.setId(1L);
        item4.setName("Name");
        item4.setOwner(owner4);
        item4.setRequest(request4);

        Comment comment2 = new Comment();
        comment2.setAuthor(author2);
        comment2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        comment2.setId(1L);
        comment2.setItem(item4);
        comment2.setText("Text");
        when(commentMapper.toDto(Mockito.<Comment>any())).thenThrow(new NotValidException("An error occurred"));
        when(commentMapper.toEntity(Mockito.<CommentDto>any())).thenReturn(comment2);
        assertThrows(NotValidException.class, () -> itemServiceImpl.createComment(1L, 1L, new CommentDto()));
        verify(itemStorage).getById(anyLong());
        verify(userStorage).getById(anyLong());
        verify(bookingRepository).findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any());
        verify(commentMapper).toEntity(Mockito.<CommentDto>any());
    }

    @Test
    void create_ThrowsResponseStatusException_WhenItemRequestNotFound() {
        long itemId = 1L;
        long userId = 100L;
        ItemDto itemDto = new ItemDto();
        itemDto.setRequestId(itemId);

        when(itemRequestRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> itemServiceImpl.create(itemDto, userId),
                String.format("Запроса с id = %d нет", itemId));

        verify(itemRequestRepository, times(1)).findById(itemId);
        verifyNoMoreInteractions(itemRequestRepository, userStorage, itemStorage, bookingMapper);
    }

    @Test
    public void createComment_ThrowsNotFoundException() {
        long userId = 1;
        long itemId = 1;
        CommentDto commentDto = new CommentDto();

        when(userStorage.getById(userId)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> {
            itemServiceImpl.createComment(userId, itemId, commentDto);
        });
    }
}