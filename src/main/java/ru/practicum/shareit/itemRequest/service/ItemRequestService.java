package ru.practicum.shareit.itemRequest.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.dto.ItemRequestListDto;

public interface ItemRequestService {
    ItemRequestDtoResponse createItemRequest(ItemRequestDto itemRequestDto, Long requesterId);

    ItemRequestListDto getPrivateAccessRequests(PageRequest pageRequest, Long requesterId);

    ItemRequestListDto getNonPrivateAccessRequests(PageRequest pageRequest, Long requesterId);

    ItemRequestDtoResponseWithItems getItemRequest(Long userId, Long requestId);
}
