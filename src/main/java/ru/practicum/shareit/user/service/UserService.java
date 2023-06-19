package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto getById(long id);

    Collection<UserDto> getAll();

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto, long id);

    Boolean deleteById(long id);
}
