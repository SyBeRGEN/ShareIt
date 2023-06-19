package ru.practicum.shareit.booking.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        ItemDto item = new ItemDto();
        when(service.createdBookingRequest(Mockito.<BookingInputDto>any(), anyLong())).thenReturn(new BookingOutputDto(1L,
                start, end, StatusType.WAITING, item, new UserDto(1L, "Name", "jane.doe@example.org")));
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
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        ItemDto item = new ItemDto();
        when(bookingService.updateBookingRequest(anyLong(), anyLong(), anyBoolean(), Mockito.<AccessLevel>any()))
                .thenReturn(new BookingOutputDto(1L, start, end, StatusType.WAITING, item,
                        new UserDto(1L, "Name", "jane.doe@example.org")));
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/bookings/{bookingId}", 1L);
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("approved", String.valueOf(true))
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"start\":[1970,1,1,0,0],\"end\":[1970,1,1,0,0],\"status\":\"WAITING\",\"item\":{\"id\":null,\"name\":null"
                                        + ",\"description\":null,\"available\":null,\"owner\":null,\"request\":null},\"booker\":{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe"
                                        + "@example.org\"}}"));
    }

    @Test
    void testGetBookingsByUser() throws Exception {
        when(bookingService.getBookingsByUser(anyLong(), Mockito.<State>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings")
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
        when(bookingService.getBookingsForOwner(anyLong(), Mockito.<State>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/owner")
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
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(1970, 1, 1).atStartOfDay();
        ItemDto item = new ItemDto();
        when(bookingService.findBookingById(anyLong(), anyLong(), Mockito.<AccessLevel>any()))
                .thenReturn(new BookingOutputDto(1L, start, end, StatusType.WAITING, item,
                        new UserDto(1L, "Name", "jane.doe@example.org")));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/{bookingId}", 1L)
                .header("X-Sharer-User-Id", 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"start\":[1970,1,1,0,0],\"end\":[1970,1,1,0,0],\"status\":\"WAITING\",\"item\":{\"id\":null,\"name\":null"
                                        + ",\"description\":null,\"available\":null,\"owner\":null,\"request\":null},\"booker\":{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe"
                                        + "@example.org\"}}"));
    }
}