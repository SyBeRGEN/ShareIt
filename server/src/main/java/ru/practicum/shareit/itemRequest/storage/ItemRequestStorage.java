package ru.practicum.shareit.itemRequest.storage;

import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.itemRequest.model.ItemRequest;

import java.util.List;

public interface ItemRequestStorage {
    ItemRequest createItemRequest(ItemRequest itemRequest);

    List<ItemRequest> getPrivateAccessRequests(PageRequest pageRequest, Long requesterId);

    List<ItemRequest> getNonPrivateAccessRequests(PageRequest pageRequest, Long requesterId);

    ItemRequest getItemRequest(Long requestId);
}
