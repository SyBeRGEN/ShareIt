package ru.practicum.shareit.itemRequest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDto;
import ru.practicum.shareit.itemRequest.dto.ItemRequestDtoResponseWithItems;
import ru.practicum.shareit.itemRequest.dto.ItemRequestListDto;
import ru.practicum.shareit.itemRequest.service.ItemRequestService;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {ItemRequestController.class})
@ExtendWith(SpringExtension.class)
class ItemRequestControllerTest {
    @Autowired
    private ItemRequestController itemRequestController;

    @MockBean
    private ItemRequestService itemRequestService;

    @Test
    void testCreateRequest() throws Exception {
        when(itemRequestService.getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ItemRequestListDto());

        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("The characteristics of someone or something");
        String content = (new ObjectMapper()).writeValueAsString(itemRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests")
                .header("X-Sharer-User-Id", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }

    @Test
    void testGetItemRequest() throws Exception {
        when(itemRequestService.getItemRequest(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ItemRequestDtoResponseWithItems());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests/{requestId}", 1L)
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"description\":null,\"created\":null,\"items\":null}"));
    }

    @Test
    void testGetItemRequest2() throws Exception {
        when(itemRequestService.getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ItemRequestListDto());
        when(itemRequestService.getItemRequest(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(new ItemRequestDtoResponseWithItems());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/requests/{requestId}", "", "Uri Variables")
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }

    @Test
    void testGetOtherRequests() throws Exception {
        when(itemRequestService.getNonPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ItemRequestListDto());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/requests/all");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }

    @Test
    void testGetPrivateRequests() throws Exception {
        when(itemRequestService.getPrivateAccessRequests(Mockito.<PageRequest>any(), Mockito.<Long>any()))
                .thenReturn(new ItemRequestListDto());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/requests");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .header("X-Sharer-User-Id", "42");
        MockMvcBuilders.standaloneSetup(itemRequestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("null"));
    }
}