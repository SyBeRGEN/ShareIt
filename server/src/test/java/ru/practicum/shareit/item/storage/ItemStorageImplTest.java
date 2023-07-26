package ru.practicum.shareit.item.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemStorageImpl.class})
@ExtendWith(SpringExtension.class)
class ItemStorageImplTest {
    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemStorageImpl itemStorageImpl;

    @Test
    void testCreate() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemRepository.save(Mockito.<Item>any())).thenReturn(item);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        assertSame(item2, itemStorageImpl.create(item2));
        verify(itemRepository).save(Mockito.<Item>any());
    }

    @Test
    void testUpdate() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        when(itemRepository.save(Mockito.<Item>any())).thenReturn(item);

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        assertSame(item2, itemStorageImpl.update(item2));
        verify(itemRepository).save(Mockito.<Item>any());
    }

    @Test
    void testGetById() {
        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(item, itemStorageImpl.getById(1L));
        verify(itemRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetAll() {
        ArrayList<Item> itemList = new ArrayList<>();
        when(itemRepository.findAll()).thenReturn(itemList);
        List<Item> actualAll = itemStorageImpl.getAll();
        assertSame(itemList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(itemRepository).findAll();
    }

    @Test
    void testDeleteById() {
        doNothing().when(itemRepository).deleteById(Mockito.<Long>any());
        itemStorageImpl.deleteById(1L);
        verify(itemRepository).deleteById(Mockito.<Long>any());
    }
}

