package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping()
    public ResponseEntity<ItemDto> create(@Valid @RequestBody ItemDto itemDto, @RequestHeader(userIdHeader) long userId) {
        ItemDto itemCreated = service.create(itemDto, userId);
        return ResponseEntity.status(201).body(itemCreated);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> update(@RequestBody ItemDto itemDto, @PathVariable long itemId,
                                          @RequestHeader(userIdHeader) Long userId) {
        ItemDto itemUpdated = service.update(itemDto, itemId, userId);
        return ResponseEntity.ok().body(itemUpdated);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<ItemDto>> searchItems(@RequestParam(name = "text") String text) {
        return ResponseEntity.ok().body(service.searchItemsByDescription(text));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteById(@PathVariable long itemId) {
        service.deleteById(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDtoWithBooking> getById(@RequestHeader(userIdHeader) long userId, @PathVariable long itemId) {
        ItemDtoWithBooking item = service.getById(itemId, userId);
        return ResponseEntity.ok().body(item);
    }

    @GetMapping()
    public ResponseEntity<List<ItemDtoWithBooking>> findAll(@RequestHeader(userIdHeader) long userId) {
        List<ItemDtoWithBooking> items = service.getAllItemsByUserId(userId);
        return ResponseEntity.ok().body(items);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestHeader(userIdHeader) long userId, @PathVariable long itemId,
                                                    @RequestBody @Valid CommentDto commentDto) {
        return ResponseEntity.ok().body(service.createComment(userId, itemId, commentDto));
    }
}
