package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.mapper.BookingMapperImpl;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.mapper.CommentMapperImpl;
import ru.practicum.shareit.item.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.ItemStorageImpl;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.repository.ItemRequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.storage.UserStorageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemController.class})
@ExtendWith(SpringExtension.class)
class ItemControllerTest {
    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    @Test
    void testUpdate() throws Exception {
        when(itemService.update(Mockito.<ItemDto>any(), anyLong(), anyLong())).thenReturn(new ItemDto());

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        itemDto.setDescription("The characteristics of someone or something");
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        itemDto.setRequestId(1L);
        String content = (new ObjectMapper()).writeValueAsString(itemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/items/{itemId}", 1L)
                .header("X-Sharer-User-Id", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":null,\"description\":null,\"available\":null,\"owner\":null,\"requestId\":null}"));
    }

    @Test
    void testDeleteById() throws Exception {
        doNothing().when(itemService).deleteById(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/items/{itemId}", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteById2() throws Exception {
        doNothing().when(itemService).deleteById(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/items/{itemId}", 1L);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        when(itemService.getById(anyLong(), anyLong())).thenReturn(new ItemDtoWithBooking());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items/{itemId}", 1L)
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":null,\"description\":null,\"available\":null,\"owner\":null,\"lastBooking\":null,\"nextBooking"
                                        + "\":null,\"comments\":[]}"));
    }

    @Test
    void testGetById2() throws Exception {
        when(itemService.getAllItemsByUserId(anyLong())).thenReturn(new ArrayList<>());
        when(itemService.getById(anyLong(), anyLong())).thenReturn(new ItemDtoWithBooking());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items/{itemId}", "", "Uri Variables")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
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
        ItemRepository repository = mock(ItemRepository.class);
        when(repository.findById(Mockito.<Long>any())).thenReturn(Optional.of(item));
        ItemStorageImpl itemStorage = new ItemStorageImpl(repository);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserRepository repository2 = mock(UserRepository.class);
        when(repository2.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));
        UserStorageImpl userStorage = new UserStorageImpl(repository2);

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
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any())).thenReturn(Optional.of(bookingList));

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
        CommentRepository commentRepository = mock(CommentRepository.class);
        when(commentRepository.save(Mockito.<Comment>any())).thenReturn(comment);
        ItemMapperImpl mapper = new ItemMapperImpl();
        ItemRequestRepository itemRequestRepository = mock(ItemRequestRepository.class);
        BookingMapperImpl bookingMapper = new BookingMapperImpl();
        ItemController itemController = new ItemController(new ItemServiceImpl(mapper, itemStorage, userStorage,
                bookingRepository, commentRepository, itemRequestRepository, bookingMapper, new CommentMapperImpl()));
        itemController.createComment(1L, 1L, new CommentDto());
        verify(repository).findById(Mockito.<Long>any());
        verify(repository2).findById(Mockito.<Long>any());
        verify(bookingRepository).findAllByItemIdAndBookerIdAndStatus(anyLong(), anyLong(), Mockito.<StatusType>any(),
                Mockito.<Sort>any());
    }

    @Test
    void testCreate() throws Exception {
        when(itemService.getAllItemsByUserId(anyLong())).thenReturn(new ArrayList<>());

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        itemDto.setDescription("The characteristics of someone or something");
        itemDto.setId(1L);
        itemDto.setName("Name");
        itemDto.setOwner(new UserDto(1L, "Name", "jane.doe@example.org"));
        String content = (new ObjectMapper()).writeValueAsString(itemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items")
                .header("X-Sharer-User-Id", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testFindAll() throws Exception {
        when(itemService.getAllItemsByUserId(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testSearchItems() throws Exception {
        when(itemService.searchItemsByDescription(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items/search").param("text", "foo");
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

