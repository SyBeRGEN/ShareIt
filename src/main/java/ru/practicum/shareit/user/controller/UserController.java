package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("{id}")
    public UserDto getById(@PathVariable long id) {

        return service.getById(id);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {

        return service.getAll();
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserDto userDto) {

        return service.create(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto,
                          @PathVariable long userId) {
        return service.update(userDto, userId);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable long id) {

        service.deleteById(id);
    }
}
