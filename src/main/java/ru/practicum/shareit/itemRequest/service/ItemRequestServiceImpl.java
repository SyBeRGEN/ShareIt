package ru.practicum.shareit.itemRequest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.dto.ItemRequestListDto;
import ru.practicum.shareit.itemRequest.mapper.ItemRequestMapper;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.storage.ItemRequestStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestStorage storage;
    private final UserRepository users;
    private final ItemRequestMapper mapper;

    @Override
    public ItemRequestDtoResponse createItemRequest(ItemRequestDto itemRequestDto, Long requesterId) {
        User user = users.findById(requesterId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пользователя с id = %s не существует", requesterId)));
        ItemRequest newRequest = mapper.toEntity(itemRequestDto);
        newRequest.setRequester(user);
        newRequest.setCreated(LocalDateTime.now());
        return mapper.toOutputDto(storage.createItemRequest(newRequest));
    }

    @Override
    public ItemRequestListDto getPrivateAccessRequests(PageRequest pageRequest, Long requesterId) {
        if (!users.existsById(requesterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пользователя с id = %s не существует", requesterId));
        }
        return ItemRequestListDto.builder()
                .requests(mapper.toOutputDtoWithItemsList(
                        storage.getPrivateAccessRequests(pageRequest, requesterId))).build();
    }

    @Override
    public ItemRequestListDto getNonPrivateAccessRequests(PageRequest pageRequest, Long requesterId) {
        if (!users.existsById(requesterId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пользователя с id = %s не существует", requesterId));
        }
        return ItemRequestListDto.builder()
                .requests(mapper.toOutputDtoWithItemsList(
                        storage.getNonPrivateAccessRequests(pageRequest, requesterId)
                )).build();
    }

    @Override
    public ItemRequestDtoResponseWithItems getItemRequest(Long userId, Long requestId) {
        if (!users.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Пользователя с id = %s не существует", userId));
        }
        return mapper.toOutputDtoWithItems(storage.getItemRequest(requestId));
    }
}
