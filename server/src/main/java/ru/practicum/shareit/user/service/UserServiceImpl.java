package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public UserDto getById(long id) {
        return mapper.toDto(storage.getById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<UserDto> getAll() {
        return storage.getAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user = storage.create(mapper.toEntity(userDto));
        return mapper.toDto(user);
    }

    @Transactional
    public UserDto update(UserDto userDto, long id) {
        User user = mapper.toEntity(userDto);
        user.setId(id);
        return mapper.toDto(storage.update(user));
    }

    @Transactional
    @Override
    public Boolean deleteById(long id) {
        return storage.deleteById(id);
    }
}
