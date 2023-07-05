package ru.practicum.shareit.itemRequest.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

import static org.mockito.Mockito.*;

class ItemRequestDtoResponseWithItemsTest {
    @Mock
    Set<ItemDto> items;
    @InjectMocks
    ItemRequestDtoResponseWithItems itemRequestDtoResponseWithItems;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetId() {
        itemRequestDtoResponseWithItems.setId(Long.valueOf(1));
    }

    @Test
    void testSetDescription() {
        itemRequestDtoResponseWithItems.setDescription("description");
    }

    @Test
    void testSetCreated() {
        itemRequestDtoResponseWithItems.setCreated(LocalDateTime.of(2023, Month.JULY, 5, 17, 50, 53));
    }

    @Test
    void testSetItems() {
        itemRequestDtoResponseWithItems.setItems(Set.of(new ItemDto(Long.valueOf(1), "name", "description", Boolean.TRUE, new UserDto(0L, "name", "email"), Long.valueOf(1))));
    }

    @Test
    void testEquals() {
        boolean result = itemRequestDtoResponseWithItems.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = itemRequestDtoResponseWithItems.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = itemRequestDtoResponseWithItems.toString();
        Assertions.assertEquals("ItemRequestDtoResponseWithItems(id=null, description=null, created=null, items=items)", result);
    }
}