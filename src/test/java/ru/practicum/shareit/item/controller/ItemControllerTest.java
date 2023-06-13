package ru.practicum.shareit.item.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.storage.ItemStorageImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;
import ru.practicum.shareit.user.storage.UserStorageImpl;

@ContextConfiguration(classes = {ItemController.class})
@ExtendWith(SpringExtension.class)
class ItemControllerTest {
    @Autowired
    private ItemController itemController;

    @MockBean
    private ItemService itemService;

    @Test
    void testCreate() {
        ItemMapperImpl mapper = mock(ItemMapperImpl.class);
        when(mapper.toItemDto(Mockito.<Item>any())).thenReturn(new ItemDto());
        when(mapper.toItem(Mockito.<ItemDto>any())).thenReturn(new Item());
        UserStorage userStorage = mock(UserStorage.class);
        when(userStorage.getById(anyLong())).thenReturn(new User(1L, "Name", "jane.doe@example.org"));
        ItemController itemController = new ItemController(
                new ItemServiceImpl(mapper, new ItemStorageImpl(), userStorage));
        ItemDto itemDto = new ItemDto();
        ResponseEntity<ItemDto> actualCreateResult = itemController.create(itemDto, 1L);
        assertEquals(itemDto, actualCreateResult.getBody());
        assertTrue(actualCreateResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateResult.getStatusCodeValue());
        verify(mapper).toItemDto(Mockito.<Item>any());
        verify(mapper).toItem(Mockito.<ItemDto>any());
        verify(userStorage).getById(anyLong());
    }

    @Test
    void testUpdate() {
        ItemService service = mock(ItemService.class);
        when(service.update(Mockito.<ItemDto>any(), anyLong(), anyLong())).thenReturn(new ItemDto());
        ItemController itemController = new ItemController(service);
        ItemDto itemDto = new ItemDto();
        ResponseEntity<ItemDto> actualUpdateResult = itemController.update(itemDto, 1L, 1L);
        assertEquals(itemDto, actualUpdateResult.getBody());
        assertTrue(actualUpdateResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateResult.getStatusCode());
        verify(service).update(Mockito.<ItemDto>any(), anyLong(), anyLong());
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
        when(itemService.getById(anyLong())).thenReturn(new ItemDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items/{itemId}", 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"name\":null,\"description\":null,\"available\":null,\"owner\":null,\"request\":null}"));
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

