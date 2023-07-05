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
import java.util.List;
import java.util.Set;

class ItemRequestListDtoTest {
    @Mock
    List<ItemRequestDtoResponseWithItems> requests;
    @InjectMocks
    ItemRequestListDto itemRequestListDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetRequests() {
        itemRequestListDto.setRequests(List.of(new ItemRequestDtoResponseWithItems(Long.valueOf(1), "description", LocalDateTime.of(2023, Month.JULY, 5, 17, 50, 58), Set.of(new ItemDto(Long.valueOf(1), "name", "description", Boolean.TRUE, new UserDto(0L, "name", "email"), Long.valueOf(1))))));
    }

    @Test
    void testEquals() {
        boolean result = itemRequestListDto.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = itemRequestListDto.canEqual("other");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testToString() {
        String result = itemRequestListDto.toString();
        Assertions.assertEquals("ItemRequestListDto(requests=requests)", result);
    }
}