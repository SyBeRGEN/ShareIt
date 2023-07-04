package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDto implements Serializable {
    private Long id;
    @NotBlank
    @Size(max = 140)
    private String name;
    @NotBlank
    @Size(max = 400)
    private String description;
    @NotNull
    private Boolean available;
    private UserDto owner;
    @Min(1)
    private Long requestId;
}