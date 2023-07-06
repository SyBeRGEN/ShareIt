package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.utils.StatusType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_IdOrderByStartDesc(long id, Pageable pageable);

    List<Booking> findByItem_IdAndStatusOrderByStartDesc(long id, StatusType status);

    List<Booking> findByBooker_IdAndStatusOrderByStartDesc(long id, StatusType status, Pageable pageable);

    List<Booking> findByBooker_IdAndStartAfterOrderByStartDesc(long id, LocalDateTime start, Pageable pageable);

    List<Booking> findByBooker_IdAndEndBeforeOrderByStartDesc(long id, LocalDateTime end, Pageable pageable);

    Optional<List<Booking>> findAllByItemIdAndBookerIdAndStatus(long itemId, long bookerId, StatusType status, Sort sort);

    List<Booking> findByBooker_IdAndStartBeforeAndEndAfterOrderByStartDesc(long id, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Booking> findByItem_Owner_IdOrderByStartDesc(long id, Pageable pageable);

    @Query(value = "select b from Booking b where b.item.owner.id = ?1")
    List<Booking> findAllByOwnerIdWithoutPaging(long ownerId, Sort sort);

    List<Booking> findByItem_Owner_IdAndStatusOrderByStartDesc(long id, StatusType status, Pageable pageable);

    List<Booking> findByItem_Owner_IdAndStartAfterOrderByStartDesc(long id, LocalDateTime start, Pageable pageable);

    List<Booking> findByItem_Owner_IdAndEndBeforeOrderByStartDesc(long id, LocalDateTime end, Pageable pageable);

    List<Booking> findByItem_Owner_IdAndStartBeforeAndEndAfterOrderByStartDesc(long id, LocalDateTime start, LocalDateTime end, Pageable pageable);
}