package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/items")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {
    public final String userIdHeader = "X-Sharer-User-Id";
    private final ItemClient itemClient;

    @PostMapping
    public Mono<ResponseEntity<Object>> createItem(@RequestHeader(userIdHeader) @Min(1) Long userId,
                                                   @Valid @RequestBody ItemDto itemDto) {
        return itemClient.createItem(userId, itemDto);
    }

    @PatchMapping("{itemId}")
    public Mono<ResponseEntity<Object>> updateItem(@RequestHeader(userIdHeader) @Min(1) Long userId,
                                                   @RequestBody ItemDtoUpdate itemDtoUpdate,
                                                   @PathVariable @Min(1) Long itemId) {
        return itemClient.updateItem(userId, itemDtoUpdate, itemId);
    }

    @GetMapping("{itemId}")
    public Mono<ResponseEntity<Object>> getItemByItemId(@RequestHeader(userIdHeader) @Min(1) Long userId,
                                                        @PathVariable @Min(1) Long itemId) {
        return itemClient.getItemByItemId(userId, itemId);
    }

    @GetMapping
    public Mono<ResponseEntity<Object>> getPersonalItems(
            @RequestHeader(userIdHeader) @Min(1) Long userId,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size) {
        return itemClient.getPersonalItems(userId, from, size);
    }

    @GetMapping("search")
    public Mono<ResponseEntity<Object>> getFoundItems(
            @RequestHeader(userIdHeader) @Min(1) Long userId,
            @RequestParam String text,
            @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(20) Integer size) {
        return itemClient.getFoundItems(userId, text, from, size);
    }

    @PostMapping("{itemId}/comment")
    public Mono<ResponseEntity<Object>> addComment(@PathVariable @Min(1) Long itemId,
                                                   @RequestHeader(userIdHeader) @Min(1) Long userId,
                                                   @Valid @RequestBody CommentDto commentDto) {
        return itemClient.addComment(itemId, userId, commentDto);
    }

}
