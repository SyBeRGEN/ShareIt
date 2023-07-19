package ru.practicum.shareit.item.model;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class CommentTest {
    @Test
    void testConstructor() {
        Comment actualComment = new Comment();
        User author = new User();
        author.setEmail("jane.doe@example.org");
        author.setId(1L);
        author.setName("Name");
        actualComment.setAuthor(author);
        LocalDateTime created = LocalDate.of(1970, 1, 1).atStartOfDay();
        actualComment.setCreated(created);
        actualComment.setId(1L);
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");
        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setItems(new HashSet<>());
        request.setRequester(requester);
        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        actualComment.setItem(item);
        actualComment.setText("Text");
        assertSame(author, actualComment.getAuthor());
        assertSame(created, actualComment.getCreated());
        assertEquals(1L, actualComment.getId());
        assertSame(item, actualComment.getItem());
        assertEquals("Text", actualComment.getText());
    }

    @Test
    void testConstructor2() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest request = new ItemRequest();
        request.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setItems(new HashSet<>());
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);

        User author = new User();
        author.setEmail("jane.doe@example.org");
        author.setId(1L);
        author.setName("Name");
        Comment actualComment = new Comment(1L, "Text", item, author, LocalDate.of(1970, 1, 1).atStartOfDay());

        assertSame(author, actualComment.getAuthor());
        assertEquals("Text", actualComment.getText());
        assertSame(item, actualComment.getItem());
        assertEquals("00:00", actualComment.getCreated().toLocalTime().toString());
        assertEquals(1L, actualComment.getId());
    }
}