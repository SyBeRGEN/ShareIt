package ru.practicum.shareit.itemRequest.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.repository.ItemRequestRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemRequestStorageImpl implements ItemRequestStorage {
    private final ItemRequestRepository repository;

    @Override
    public ItemRequest createItemRequest(ItemRequest itemRequest) {
        return repository.save(itemRequest);
    }

    @Override
    public List<ItemRequest> getPrivateAccessRequests(PageRequest pageRequest, Long requesterId) {
        return repository.findAllByRequesterId(pageRequest, requesterId);
    }

    @Override
    public List<ItemRequest> getNonPrivateAccessRequests(PageRequest pageRequest, Long requesterId) {
        return repository.findAllByRequesterIdNot(pageRequest, requesterId);
    }

    @Override
    public ItemRequest getItemRequest(Long requestId) {
        return repository.findById(requestId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Запроса с id = %s не существует", requestId)
                        )
                );
    }
}
