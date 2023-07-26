package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private Long requestId;
}