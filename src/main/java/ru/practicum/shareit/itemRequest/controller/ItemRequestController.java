package ru.practicum.shareit.itemRequest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.dto.ItemRequestListDto;
import ru.practicum.shareit.itemRequest.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/requests")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<ItemRequestDtoResponse> createRequest(@RequestHeader(userIdHeader) @Min(1) Long requesterId,
                                                                @RequestBody @Valid ItemRequestDto itemRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemRequestService.createItemRequest(itemRequestDto, requesterId));
    }

    @GetMapping
    public ResponseEntity<ItemRequestListDto> getPrivateRequests(
            @RequestHeader(userIdHeader) @Min(1) Long requesterId,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemRequestService.getPrivateAccessRequests(
                        PageRequest.of(from / size, size).withSort(Sort.by("created").descending()),
                        requesterId));
    }

    @GetMapping("all")
    public ResponseEntity<ItemRequestListDto> getOtherRequests(
            @RequestHeader(userIdHeader) @Min(1) Long requesterId,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(itemRequestService.getNonPrivateAccessRequests(
                        PageRequest.of(
                                from / size, size, Sort.by(Sort.Direction.DESC, "created")),
                        requesterId));
    }

    @GetMapping("{requestId}")
    public ResponseEntity<ItemRequestDtoResponseWithItems> getItemRequest(
            @RequestHeader(userIdHeader) @Min(1) Long userId,
            @PathVariable @Min(1) Long requestId) {
        return ResponseEntity.status(HttpStatus.OK).body(itemRequestService.getItemRequest(userId, requestId));
    }
}
