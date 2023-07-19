package ru.practicum.shareit.itemRequest.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ItemRequestStorageImpl.class})
@ExtendWith(SpringExtension.class)
class ItemRequestStorageImplTest {
    @MockBean
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private ItemRequestStorageImpl itemRequestStorageImpl;

    @Test
    void testCreateItemRequest() {
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new HashSet<>());
        itemRequest.setRequester(requester);
        when(itemRequestRepository.save(Mockito.<ItemRequest>any())).thenReturn(itemRequest);

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest2.setDescription("The characteristics of someone or something");
        itemRequest2.setId(1L);
        itemRequest2.setItems(new HashSet<>());
        itemRequest2.setRequester(requester2);
        assertSame(itemRequest, itemRequestStorageImpl.createItemRequest(itemRequest2));
        verify(itemRequestRepository).save(Mockito.<ItemRequest>any());
    }

    @Test
    void testCreateItemRequest2() {
        when(itemRequestRepository.save(Mockito.<ItemRequest>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new HashSet<>());
        itemRequest.setRequester(requester);
        assertThrows(ResponseStatusException.class, () -> itemRequestStorageImpl.createItemRequest(itemRequest));
        verify(itemRequestRepository).save(Mockito.<ItemRequest>any());
    }

    @Test
    void testGetPrivateAccessRequests() {
        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        when(itemRequestRepository.findAllByRequesterId(Mockito.<Pageable>any(), Mockito.<Long>any()))
                .thenReturn(itemRequestList);
        List<ItemRequest> actualPrivateAccessRequests = itemRequestStorageImpl.getPrivateAccessRequests(null, 1L);
        assertSame(itemRequestList, actualPrivateAccessRequests);
        assertTrue(actualPrivateAccessRequests.isEmpty());
        verify(itemRequestRepository).findAllByRequesterId(Mockito.<Pageable>any(), Mockito.<Long>any());
    }

    @Test
    void testGetPrivateAccessRequests2() {
        when(itemRequestRepository.findAllByRequesterId(Mockito.<Pageable>any(), Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> itemRequestStorageImpl.getPrivateAccessRequests(null, 1L));
        verify(itemRequestRepository).findAllByRequesterId(Mockito.<Pageable>any(), Mockito.<Long>any());
    }

    @Test
    void testGetNonPrivateAccessRequests() {
        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        when(itemRequestRepository.findAllByRequesterIdNot(Mockito.<Pageable>any(), Mockito.<Long>any()))
                .thenReturn(itemRequestList);
        List<ItemRequest> actualNonPrivateAccessRequests = itemRequestStorageImpl.getNonPrivateAccessRequests(null, 1L);
        assertSame(itemRequestList, actualNonPrivateAccessRequests);
        assertTrue(actualNonPrivateAccessRequests.isEmpty());
        verify(itemRequestRepository).findAllByRequesterIdNot(Mockito.<Pageable>any(), Mockito.<Long>any());
    }

    @Test
    void testGetNonPrivateAccessRequests2() {
        when(itemRequestRepository.findAllByRequesterIdNot(Mockito.<Pageable>any(), Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> itemRequestStorageImpl.getNonPrivateAccessRequests(null, 1L));
        verify(itemRequestRepository).findAllByRequesterIdNot(Mockito.<Pageable>any(), Mockito.<Long>any());
    }

    @Test
    void testGetItemRequest() {
        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setCreated(LocalDate.of(1970, 1, 1).atStartOfDay());
        itemRequest.setDescription("The characteristics of someone or something");
        itemRequest.setId(1L);
        itemRequest.setItems(new HashSet<>());
        itemRequest.setRequester(requester);
        Optional<ItemRequest> ofResult = Optional.of(itemRequest);
        when(itemRequestRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(itemRequest, itemRequestStorageImpl.getItemRequest(1L));
        verify(itemRequestRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetItemRequest2() {
        when(itemRequestRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> itemRequestStorageImpl.getItemRequest(1L));
        verify(itemRequestRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetItemRequest3() {
        when(itemRequestRepository.findById(Mockito.<Long>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> itemRequestStorageImpl.getItemRequest(1L));
        verify(itemRequestRepository).findById(Mockito.<Long>any());
    }
}