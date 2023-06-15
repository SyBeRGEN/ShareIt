package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final UserStorage storage;

    @Override
    public UserDto getById(long id) {
        return mapper.toUserDto(storage.getById(id));
    }

    @Override
    public Collection<UserDto> getAll() {
        return storage.getAll()
                .stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = storage.create(mapper.toUser(userDto));
        return mapper.toUserDto(user);
    }

    public UserDto update(UserDto userDto, long id) {
        userDto.setId(id);
        return mapper.toUserDto(storage.update(mapper.toUser(userDto)));
    }

    @Override
    public Boolean deleteById(long id) {
        return storage.deleteById(id);
    }
}
