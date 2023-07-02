package ru.practicum.shareit.booking.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utils.State;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingStorageImpl.class})
@ExtendWith(SpringExtension.class)
class BookingStorageImplTest {
    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingStorageImpl bookingStorageImpl;

    @Test
    void testSaveBooking() {
        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

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

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);
        when(bookingRepository.save(Mockito.<Booking>any())).thenReturn(booking);

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

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

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(StatusType.WAITING);
        assertSame(booking, bookingStorageImpl.saveBooking(booking2));
        verify(bookingRepository).save(Mockito.<Booking>any());
    }

    @Test
    void testSaveBooking2() {
        when(bookingRepository.save(Mockito.<Booking>any())).thenThrow(new NotFoundException("An error occurred"));

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

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

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);
        assertThrows(NotFoundException.class, () -> bookingStorageImpl.saveBooking(booking));
        verify(bookingRepository).save(Mockito.<Booking>any());
    }

    @Test
    void testFindBookingById() {
        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

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

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(StatusType.WAITING);
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(booking, bookingStorageImpl.findBookingById(1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testFindBookingById2() {
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> bookingStorageImpl.findBookingById(1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testFindBookingById3() {
        when(bookingRepository.findById(Mockito.<Long>any())).thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> bookingStorageImpl.findBookingById(1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
    }

    @Test
    void testGetBookingsByUser() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByBookerId(anyLong(), Mockito.<Sort>any())).thenReturn(bookingList);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsByUser = bookingStorageImpl.getBookingsByUser(booker, sort, State.ALL);
        assertSame(bookingList, actualBookingsByUser);
        assertTrue(actualBookingsByUser.isEmpty());
        verify(bookingRepository).findAllByBookerId(anyLong(), Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsByUser2() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(bookingList);
        when(bookingRepository.findAllByBookerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsByUser = bookingStorageImpl.getBookingsByUser(booker, sort, State.CURRENT);
        assertSame(bookingList, actualBookingsByUser);
        assertTrue(actualBookingsByUser.isEmpty());
        verify(bookingRepository).findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsByUser3() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(
                bookingRepository.findAllByBookerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(bookingList);
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsByUser = bookingStorageImpl.getBookingsByUser(booker, sort, State.PAST);
        assertSame(bookingList, actualBookingsByUser);
        assertTrue(actualBookingsByUser.isEmpty());
        verify(bookingRepository).findAllByBookerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsByUser4() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByBookerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(bookingList);
        when(
                bookingRepository.findAllByBookerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsByUser = bookingStorageImpl.getBookingsByUser(booker, sort, State.FUTURE);
        assertSame(bookingList, actualBookingsByUser);
        assertTrue(actualBookingsByUser.isEmpty());
        verify(bookingRepository).findAllByBookerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsByUser5() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByBookerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any()))
                .thenReturn(bookingList);
        when(bookingRepository.findAllByBookerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(
                bookingRepository.findAllByBookerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsByUser = bookingStorageImpl.getBookingsByUser(booker, sort, State.WAITING);
        assertSame(bookingList, actualBookingsByUser);
        assertTrue(actualBookingsByUser.isEmpty());
        verify(bookingRepository).findAllByBookerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsByUser6() {
        when(bookingRepository.findAllByBookerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        when(bookingRepository.findAllByBookerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(
                bookingRepository.findAllByBookerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByBookerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");
        assertThrows(NotFoundException.class,
                () -> bookingStorageImpl.getBookingsByUser(booker, Sort.unsorted(), State.WAITING));
        verify(bookingRepository).findAllByBookerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any());
    }

    @Test
    void testGetBookingsForOwner() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(bookingList);

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsForOwner = bookingStorageImpl.getBookingsForOwner(owner, sort, State.ALL);
        assertSame(bookingList, actualBookingsForOwner);
        assertTrue(actualBookingsForOwner.isEmpty());
        verify(bookingRepository).findAllByOwnerId(anyLong(), Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsForOwner2() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(bookingList);
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsForOwner = bookingStorageImpl.getBookingsForOwner(owner, sort, State.CURRENT);
        assertSame(bookingList, actualBookingsForOwner);
        assertTrue(actualBookingsForOwner.isEmpty());
        verify(bookingRepository).findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsForOwner3() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByOwnerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(bookingList);
        when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsForOwner = bookingStorageImpl.getBookingsForOwner(owner, sort, State.PAST);
        assertSame(bookingList, actualBookingsForOwner);
        assertTrue(actualBookingsForOwner.isEmpty());
        verify(bookingRepository).findAllByOwnerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsForOwner4() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(
                bookingRepository.findAllByOwnerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(bookingList);
        when(bookingRepository.findAllByOwnerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsForOwner = bookingStorageImpl.getBookingsForOwner(owner, sort, State.FUTURE);
        assertSame(bookingList, actualBookingsForOwner);
        assertTrue(actualBookingsForOwner.isEmpty());
        verify(bookingRepository).findAllByOwnerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsForOwner5() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        when(bookingRepository.findAllByOwnerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any()))
                .thenReturn(bookingList);
        when(
                bookingRepository.findAllByOwnerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        Sort sort = Sort.unsorted();
        List<Booking> actualBookingsForOwner = bookingStorageImpl.getBookingsForOwner(owner, sort, State.WAITING);
        assertSame(bookingList, actualBookingsForOwner);
        assertTrue(actualBookingsForOwner.isEmpty());
        verify(bookingRepository).findAllByOwnerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any());
        assertTrue(sort.toList().isEmpty());
    }

    @Test
    void testGetBookingsForOwner6() {
        when(bookingRepository.findAllByOwnerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        when(
                bookingRepository.findAllByOwnerIdAndStartAfter(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdAndEndBefore(anyLong(), Mockito.<LocalDateTime>any(), Mockito.<Sort>any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(anyLong(), Mockito.<LocalDateTime>any(),
                Mockito.<Sort>any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findAllByOwnerId(anyLong(), Mockito.<Sort>any())).thenReturn(new ArrayList<>());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");
        assertThrows(NotFoundException.class,
                () -> bookingStorageImpl.getBookingsForOwner(owner, Sort.unsorted(), State.WAITING));
        verify(bookingRepository).findAllByOwnerIdAndStatus(anyLong(), Mockito.<StatusType>any(), Mockito.<Sort>any());
    }
}