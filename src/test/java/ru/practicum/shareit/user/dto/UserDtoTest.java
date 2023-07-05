package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {
    @Test
    void testCanEqual() {
        assertFalse((new UserDto(1L, "Name", "jane.doe@example.org")).canEqual("Other"));
    }

    @Test
    void testCanEqual2() {
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");
        assertTrue(userDto.canEqual(new UserDto(1L, "Name", "jane.doe@example.org")));
    }

    @Test
    void testConstructor() {
        UserDto actualUserDto = new UserDto();
        actualUserDto.setEmail("jane.doe@example.org");
        actualUserDto.setId(1L);
        actualUserDto.setName("Name");
        String actualToStringResult = actualUserDto.toString();
        assertEquals("jane.doe@example.org", actualUserDto.getEmail());
        assertEquals(1L, actualUserDto.getId());
        assertEquals("Name", actualUserDto.getName());
        assertEquals("UserDto(id=1, name=Name, email=jane.doe@example.org)", actualToStringResult);
    }

    @Test
    void testConstructor2() {
        UserDto actualUserDto = new UserDto(1L, "Name", "jane.doe@example.org");

        assertEquals("jane.doe@example.org", actualUserDto.getEmail());
        assertEquals("Name", actualUserDto.getName());
        assertEquals(1L, actualUserDto.getId());
    }

    @Test
    void testEquals() {
        assertNotEquals(new UserDto(1L, "Name", "jane.doe@example.org"), null);
        assertNotEquals(new UserDto(1L, "Name", "jane.doe@example.org"), "Different type to UserDto");
    }

    @Test
    void testEquals2() {
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");
        assertEquals(userDto, userDto);
        int expectedHashCodeResult = userDto.hashCode();
        assertEquals(expectedHashCodeResult, userDto.hashCode());
    }

    @Test
    void testEquals3() {
        UserDto userDto = new UserDto(1L, "Name", "jane.doe@example.org");
        UserDto userDto2 = new UserDto(1L, "Name", "jane.doe@example.org");

        assertEquals(userDto, userDto2);
        int expectedHashCodeResult = userDto.hashCode();
        assertEquals(expectedHashCodeResult, userDto2.hashCode());
    }

    @Test
    void testEquals4() {
        UserDto userDto = new UserDto(2L, "Name", "jane.doe@example.org");
        assertNotEquals(userDto, new UserDto(1L, "Name", "jane.doe@example.org"));
    }

    @Test
    void testEquals5() {
        UserDto userDto = new UserDto(1L, "jane.doe@example.org", "jane.doe@example.org");
        assertNotEquals(userDto, new UserDto(1L, "Name", "jane.doe@example.org"));
    }

    @Test
    void testEquals6() {
        UserDto userDto = new UserDto(1L, null, "jane.doe@example.org");
        assertNotEquals(userDto, new UserDto(1L, "Name", "jane.doe@example.org"));
    }

    @Test
    void testEquals7() {
        UserDto userDto = new UserDto(1L, "Name", "john.smith@example.org");
        assertNotEquals(userDto, new UserDto(1L, "Name", "jane.doe@example.org"));
    }

    @Test
    void testEquals8() {
        UserDto userDto = new UserDto(1L, "Name", null);
        assertNotEquals(userDto, new UserDto(1L, "Name", "jane.doe@example.org"));
    }

    @Test
    void testEquals9() {
        UserDto userDto = new UserDto(1L, null, "jane.doe@example.org");
        UserDto userDto2 = new UserDto(1L, null, "jane.doe@example.org");

        assertEquals(userDto, userDto2);
        int expectedHashCodeResult = userDto.hashCode();
        assertEquals(expectedHashCodeResult, userDto2.hashCode());
    }

    @Test
    void testEquals10() {
        UserDto userDto = new UserDto(1L, "Name", null);
        UserDto userDto2 = new UserDto(1L, "Name", null);

        assertEquals(userDto, userDto2);
        int expectedHashCodeResult = userDto.hashCode();
        assertEquals(expectedHashCodeResult, userDto2.hashCode());
    }
}

