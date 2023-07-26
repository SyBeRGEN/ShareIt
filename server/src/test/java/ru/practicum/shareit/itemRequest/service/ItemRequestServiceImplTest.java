package ru.practicum.shareit.itemRequest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponse;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.mapper.ItemRequestMapper;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.storage.ItemRequestStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ItemRequestServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ItemRequestServiceImplTest {
    @MockBean
    private ItemRequestMapper itemRequestMapper;

    @Autowired
    private ItemRequestServiceImpl itemRequestServiceImpl;

    @MockBean
    private ItemRequestStorage itemRequestStorage;

    @MockBean
    private UserRepository userRepository;

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
        when(itemRequestStorage.createItemRequest(Mockito.<ItemRequest>any())).thenReturn(itemRequest);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        ItemRequestDtoResponse itemRequestDtoResponse = new ItemRequestDtoResponse();
        when(itemRequestMapper.toOutputDto(Mockito.<ItemRequest>any())).thenReturn(itemRequestDtoResponse);
        when(itemRequestMapper.toEntity(Mockito.<ItemRequestDto>any())).thenReturn(itemRequest2);
        assertSame(itemRequestDtoResponse, itemRequestServiceImpl
                .createItemRequest(new ItemRequestDto("The characteristics of someone or something"), 1L));
        verify(itemRequestStorage).createItemRequest(Mockito.<ItemRequest>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDto(Mockito.<ItemRequest>any());
        verify(itemRequestMapper).toEntity(Mockito.<ItemRequestDto>any());
    }

    @Test
    void testCreateItemRequest2() {
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
        when(itemRequestStorage.createItemRequest(Mockito.<ItemRequest>any())).thenReturn(itemRequest);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        when(itemRequestMapper.toOutputDto(Mockito.<ItemRequest>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        when(itemRequestMapper.toEntity(Mockito.<ItemRequestDto>any())).thenReturn(itemRequest2);
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl
                .createItemRequest(new ItemRequestDto("The characteristics of someone or something"), 1L));
        verify(itemRequestStorage).createItemRequest(Mockito.<ItemRequest>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDto(Mockito.<ItemRequest>any());
        verify(itemRequestMapper).toEntity(Mockito.<ItemRequestDto>any());
    }

    @Test
    void testCreateItemRequest3() {
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
        when(itemRequestStorage.createItemRequest(Mockito.<ItemRequest>any())).thenReturn(itemRequest);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

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
        when(itemRequestMapper.toOutputDto(Mockito.<ItemRequest>any())).thenReturn(new ItemRequestDtoResponse());
        when(itemRequestMapper.toEntity(Mockito.<ItemRequestDto>any())).thenReturn(itemRequest2);
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl
                .createItemRequest(new ItemRequestDto("The characteristics of someone or something"), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetPrivateAccessRequests() {
        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        when(itemRequestStorage.getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(itemRequestList);
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(itemRequestMapper.toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any())).thenReturn(new ArrayList<>());
        assertEquals(itemRequestList, itemRequestServiceImpl.getPrivateAccessRequests(null, 1L).getRequests());
        verify(itemRequestStorage).getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any());
        verify(userRepository).existsById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any());
    }

    @Test
    void testGetPrivateAccessRequests2() {
        when(itemRequestStorage.getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(itemRequestMapper.toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl.getPrivateAccessRequests(null, 1L));
        verify(itemRequestStorage).getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any());
        verify(userRepository).existsById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any());
    }

    @Test
    void testGetPrivateAccessRequests3() {
        when(itemRequestStorage.getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        when(itemRequestMapper.toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any())).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl.getPrivateAccessRequests(null, 1L));
        verify(userRepository).existsById(Mockito.<Long>any());
    }

    @Test
    void testGetNonPrivateAccessRequests() {
        ArrayList<ItemRequest> itemRequestList = new ArrayList<>();
        when(itemRequestStorage.getNonPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(itemRequestList);
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(itemRequestMapper.toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any())).thenReturn(new ArrayList<>());
        assertEquals(itemRequestList, itemRequestServiceImpl.getNonPrivateAccessRequests(null, 1L).getRequests());
        verify(itemRequestStorage).getNonPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any());
        verify(userRepository).existsById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any());
    }

    @Test
    void testGetNonPrivateAccessRequests2() {
        when(itemRequestStorage.getNonPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(itemRequestMapper.toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl.getNonPrivateAccessRequests(null, 1L));
        verify(itemRequestStorage).getNonPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any());
        verify(userRepository).existsById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any());
    }

    @Test
    void testGetNonPrivateAccessRequests3() {
        when(itemRequestStorage.getNonPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ArrayList<>());
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        when(itemRequestMapper.toOutputDtoWithItemsList(Mockito.<List<ItemRequest>>any())).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl.getNonPrivateAccessRequests(null, 1L));
        verify(userRepository).existsById(Mockito.<Long>any());
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
        when(itemRequestStorage.getItemRequest(Mockito.<Long>any())).thenReturn(itemRequest);
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        ItemRequestDtoResponseWithItems itemRequestDtoResponseWithItems = new ItemRequestDtoResponseWithItems();
        when(itemRequestMapper.toOutputDtoWithItems(Mockito.<ItemRequest>any()))
                .thenReturn(itemRequestDtoResponseWithItems);
        assertSame(itemRequestDtoResponseWithItems, itemRequestServiceImpl.getItemRequest(1L, 1L));
        verify(itemRequestStorage).getItemRequest(Mockito.<Long>any());
        verify(userRepository).existsById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDtoWithItems(Mockito.<ItemRequest>any());
    }

    @Test
    void testGetItemRequest2() {
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
        when(itemRequestStorage.getItemRequest(Mockito.<Long>any())).thenReturn(itemRequest);
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(true);
        when(itemRequestMapper.toOutputDtoWithItems(Mockito.<ItemRequest>any()))
                .thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl.getItemRequest(1L, 1L));
        verify(itemRequestStorage).getItemRequest(Mockito.<Long>any());
        verify(userRepository).existsById(Mockito.<Long>any());
        verify(itemRequestMapper).toOutputDtoWithItems(Mockito.<ItemRequest>any());
    }

    @Test
    void testGetItemRequest3() {
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
        when(itemRequestStorage.getItemRequest(Mockito.<Long>any())).thenReturn(itemRequest);
        when(userRepository.existsById(Mockito.<Long>any())).thenReturn(false);
        when(itemRequestMapper.toOutputDtoWithItems(Mockito.<ItemRequest>any()))
                .thenReturn(new ItemRequestDtoResponseWithItems());
        assertThrows(ResponseStatusException.class, () -> itemRequestServiceImpl.getItemRequest(1L, 1L));
        verify(userRepository).existsById(Mockito.<Long>any());
    }
}