package ru.practicum.shareit.booking.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.dto.BookingOutputDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.utils.AccessLevel;
import ru.practicum.shareit.booking.utils.State;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingController.class})
@ExtendWith(SpringExtension.class)
class BookingControllerTest {
    @Autowired
    private BookingController bookingController;

    @MockBean
    private BookingService bookingService;

    @Test
    void testCreateBookingRequest() {
        BookingService service = mock(BookingService.class);
        when(service.createdBookingRequest(Mockito.<BookingInputDto>any(), anyLong())).thenReturn(new BookingOutputDto());
        BookingController bookingController = new BookingController(service);
        ResponseEntity<BookingOutputDto> actualCreateBookingRequestResult = bookingController.createBookingRequest(1L,
                new BookingInputDto());
        assertTrue(actualCreateBookingRequestResult.hasBody());
        assertTrue(actualCreateBookingRequestResult.getHeaders().isEmpty());
        assertEquals(201, actualCreateBookingRequestResult.getStatusCodeValue());
        verify(service).createdBookingRequest(Mockito.<BookingInputDto>any(), anyLong());
    }

    @Test
    void testUpdateBookingRequest() throws Exception {
        when(bookingService.updateBookingRequest(anyLong(), anyLong(), anyBoolean(), Mockito.<AccessLevel>any()))
                .thenReturn(new BookingOutputDto());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/bookings/{bookingId}", 1L);
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("approved", String.valueOf(true))
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"start\":null,\"end\":null,\"status\":null,\"item\":null,\"booker\":null}"));
    }

    @Test
    void testGetBookingsByUser() throws Exception {
        when(bookingService.getBookingsByUser(Mockito.<Pageable>any(), anyLong(), Mockito.<State>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/bookings");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .param("state", "")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBookingsForOwner() throws Exception {
        when(bookingService.getBookingsForOwner(Mockito.<Pageable>any(), anyLong(), Mockito.<State>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/bookings/owner");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .param("state", "")
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetBooking() throws Exception {
        when(bookingService.findBookingById(anyLong(), anyLong(), Mockito.<AccessLevel>any()))
                .thenReturn(new BookingOutputDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/{bookingId}", 1L)
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"start\":null,\"end\":null,\"status\":null,\"item\":null,\"booker\":null}"));
    }
}