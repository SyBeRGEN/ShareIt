package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.user.dto.UserDto;

class ItemDtoTest {
    @Mock
    UserDto owner;
    @InjectMocks
    ItemDto itemDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetId() {
        itemDto.setId(Long.valueOf(1));
    }

    @Test
    void testSetName() {
        itemDto.setName("name");
    }

    @Test
    void testSetDescription() {
        itemDto.setDescription("description");
    }

    @Test
    void testSetAvailable() {
        itemDto.setAvailable(Boolean.TRUE);
    }

    @Test
    void testSetOwner() {
        itemDto.setOwner(new UserDto(0L, "name", "email"));
    }

    @Test
    void testSetRequestId() {
        itemDto.setRequestId(Long.valueOf(1));
    }

    @Test
    void testEquals() {
        boolean result = itemDto.equals("o");
        Assertions.assertEquals(false, result);
    }

    @Test
    void testCanEqual() {
        boolean result = itemDto.canEqual("other");
        Assertions.assertEquals(false, result);
    }
    @Test
    void testToString() {
        String result = itemDto.toString();
        Assertions.assertEquals("ItemDto(id=null, name=null, description=null, available=null, owner=owner, requestId=null)", result);
    }
}