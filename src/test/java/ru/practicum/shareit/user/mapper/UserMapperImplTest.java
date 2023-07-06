package ru.practicum.shareit.user.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ContextConfiguration(classes = {UserMapperImpl.class})
@ExtendWith(SpringExtension.class)
class UserMapperImplTest {
    @Autowired
    private UserMapperImpl userMapperImpl;

    @Test
    void testToEntity() {
        User actualToEntityResult = userMapperImpl.toEntity(new UserDto(1L, "Name", "jane.doe@example.org"));
        assertEquals("jane.doe@example.org", actualToEntityResult.getEmail());
        assertEquals("Name", actualToEntityResult.getName());
        assertEquals(1L, actualToEntityResult.getId());
    }

    @Test
    void testToEntity2() {
        assertNull(userMapperImpl.toEntity(null));
    }

    @Test
    void testToDto() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserDto actualToDtoResult = userMapperImpl.toDto(user);
        assertEquals("jane.doe@example.org", actualToDtoResult.getEmail());
        assertEquals("Name", actualToDtoResult.getName());
        assertEquals(1L, actualToDtoResult.getId());
    }
}