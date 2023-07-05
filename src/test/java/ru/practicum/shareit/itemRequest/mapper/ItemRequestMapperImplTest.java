package ru.practicum.shareit.itemRequest.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ItemRequestMapperImplTest {
    @Autowired
    private ItemRequestMapperImpl itemRequestMapperImpl;

    @MockBean
    private SqlDataSourceScriptDatabaseInitializer sqlDataSourceScriptDatabaseInitializer;

    @Test
    void testToEntity() {
        assertEquals("The characteristics of someone or something",
                itemRequestMapperImpl.toEntity(new ItemRequestDto("The characteristics of someone or something"))
                        .getDescription());
        assertNull(itemRequestMapperImpl.toEntity(null));
    }

    @Test
    void testToOutputDto() {
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new HashSet<>());
        itemRequest.setRequester(requester);
        ItemRequestDtoResponse actualToOutputDtoResult = itemRequestMapperImpl.toOutputDto(itemRequest);
        assertEquals(1L, actualToOutputDtoResult.getId().longValue());
        assertEquals("00:00", actualToOutputDtoResult.getCreated().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualToOutputDtoResult.getDescription());
    }

    @Test
    void testToOutputDtoWithItems() {
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new HashSet<>());
        itemRequest.setRequester(requester);
        ItemRequestDtoResponseWithItems actualToOutputDtoWithItemsResult = itemRequestMapperImpl
                .toOutputDtoWithItems(itemRequest);
        assertTrue(actualToOutputDtoWithItemsResult.getItems().isEmpty());
        assertEquals("00:00", actualToOutputDtoWithItemsResult.getCreated().toLocalTime().toString());
        assertEquals(1L, actualToOutputDtoWithItemsResult.getId().longValue());
        assertEquals("The characteristics of someone or something", actualToOutputDtoWithItemsResult.getDescription());
    }

    @Test
    void testToOutputDtoWithItems2() {
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

        HashSet<Item> items = new HashSet<>();
        items.add(item);

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(items);
        itemRequest.setRequester(requester2);
        ItemRequestDtoResponseWithItems actualToOutputDtoWithItemsResult = itemRequestMapperImpl
                .toOutputDtoWithItems(itemRequest);
        assertEquals(1, actualToOutputDtoWithItemsResult.getItems().size());
        assertEquals("00:00", actualToOutputDtoWithItemsResult.getCreated().toLocalTime().toString());
        assertEquals(1L, actualToOutputDtoWithItemsResult.getId().longValue());
        assertEquals("The characteristics of someone or something", actualToOutputDtoWithItemsResult.getDescription());
    }

    @Test
    void testToOutputDtoWithItemsList() {
        assertTrue(itemRequestMapperImpl.toOutputDtoWithItemsList(new ArrayList<>()).isEmpty());
    }

    @Test
    void testToOutputDtoWithItemsList2() {
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new HashSet<>());
        itemRequest.setRequester(requester);

        ArrayList<ItemRequest> itemRequests = new ArrayList<>();
        itemRequests.add(itemRequest);
        List<ItemRequestDtoResponseWithItems> actualToOutputDtoWithItemsListResult = itemRequestMapperImpl
                .toOutputDtoWithItemsList(itemRequests);
        assertEquals(1, actualToOutputDtoWithItemsListResult.size());
        ItemRequestDtoResponseWithItems getResult = actualToOutputDtoWithItemsListResult.get(0);
        assertTrue(getResult.getItems().isEmpty());
        assertEquals("00:00", getResult.getCreated().toLocalTime().toString());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
    }

    @Test
    void testToOutputDtoWithItemsList3() {
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

        HashSet<Item> items = new HashSet<>();
        items.add(item);

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(items);
        itemRequest.setRequester(requester2);

        ArrayList<ItemRequest> itemRequests = new ArrayList<>();
        itemRequests.add(itemRequest);
        List<ItemRequestDtoResponseWithItems> actualToOutputDtoWithItemsListResult = itemRequestMapperImpl
                .toOutputDtoWithItemsList(itemRequests);
        assertEquals(1, actualToOutputDtoWithItemsListResult.size());
        ItemRequestDtoResponseWithItems getResult = actualToOutputDtoWithItemsListResult.get(0);
        assertEquals(1, getResult.getItems().size());
        assertEquals("00:00", getResult.getCreated().toLocalTime().toString());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("The characteristics of someone or something", getResult.getDescription());
    }

    @Test
    void testItemSetToItemDtoSet() {
        assertTrue(itemRequestMapperImpl.itemSetToItemDtoSet(new HashSet<>()).isEmpty());
    }

    @Test
    void testItemSetToItemDtoSet2() {
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

        HashSet<Item> set = new HashSet<>();
        set.add(item);
        assertEquals(1, itemRequestMapperImpl.itemSetToItemDtoSet(set).size());
    }
}