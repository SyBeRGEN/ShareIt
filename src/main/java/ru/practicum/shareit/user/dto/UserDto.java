package ru.practicum.shareit.user.dto;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Value
public class UserDto implements Serializable {
    long id;
    @Size(max = 140)
    String name;
    @Email
    @NotBlank String email;
}