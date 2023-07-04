package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.shareit.booking.dto.BookingItemDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.utils.StatusType;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.itemRequest.model.ItemRequest;
import ru.practicum.shareit.itemRequest.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper mapper;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final BookingMapper bookingMapper;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    @Override
    public ItemDtoWithBooking getById(long id, long userId) {
        itemIdValidator(id);
        ItemDtoWithBooking itemDtoWithBooking = mapper.toDtoWithBooking(itemStorage.getById(id));

        List<BookingItemDto> bookingItemDtoList = bookingRepository.findByItem_IdAndStatusOrderByStartDesc(id,
                        StatusType.APPROVED)
                .stream()
                .map(bookingMapper::toBookingItemDto)
                .collect(Collectors.toList());

        List<CommentDto> commentsDto = commentRepository.findByItem_IdOrderByCreatedDesc(id)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());

        if (itemDtoWithBooking.getOwner().getId() == userId) {
            setBookings(itemDtoWithBooking, bookingItemDtoList);

        }
        itemDtoWithBooking.setComments(commentsDto);

        return itemDtoWithBooking;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDtoWithBooking> getAllItemsByUserId(long userId) {
        List<ItemDtoWithBooking> itemDtoList = itemStorage.getAll()
                .stream()
                .filter(i -> Objects.equals(i.getOwner().getId(), userId))
                .map(mapper::toDtoWithBooking)
                .collect(Collectors.toList());

        List<BookingItemDto> bookingItemDtoList = bookingRepository.findAllByOwnerIdWithoutPaging(userId,
                        Sort.by(Sort.Direction.DESC, "start"))
                .stream()
                .map(bookingMapper::toBookingItemDto)
                .collect(Collectors.toList());

        List<Comment> comments = commentRepository.findAllByItemIdInOrderByCreatedDesc(
                itemDtoList.stream()
                        .map(ItemDtoWithBooking::getId)
                        .collect(Collectors.toList()));

        itemDtoList.forEach(i -> {
            setBookings(i, bookingItemDtoList);
            setComments(itemDtoList, comments);
        });
        return itemDtoList;
    }

    @Transactional
    @Override
    public ItemDto create(ItemDto itemDto, long userId) {
        Item newItem = mapper.toEntity(itemDto);
        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = itemRequestRepository.findById(itemDto.getRequestId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Запроса с id = %s нет", itemDto.getRequestId())));

            newItem.setRequest(itemRequest);
        }
        User owner = userStorage.getById(userId);
        itemOwnerCheckValidator(owner, newItem, userId);
        Item createdItem = itemStorage.create(newItem);
        return mapper.toDto(createdItem);
    }

    @Transactional
    @Override
    public ItemDto update(ItemDto itemDto, long itemId, long userId) {
        Item item = mapper.toEntity(itemDto);
        userStorage.getById(userId);
        Item oldItem = itemStorage.getById(itemId);
        itemOwnerNameDescAvailValidator(item, oldItem, userId);
        Item changedItem = itemStorage.update(oldItem);
        return mapper.toDto(changedItem);
    }

    @Transactional
    public void deleteById(long id) {
        itemIdValidator(id);
        itemStorage.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemDto> searchItemsByDescription(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getAll()
                .stream()
                .filter(i -> i.getDescription().toLowerCase().contains(text.toLowerCase()) && i.getAvailable())
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CommentDto createComment(long userId, long itemId, CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        User user = userStorage.getById(userId);
        Item item = itemStorage.getById(itemId);

        List<Booking> bookings = bookingRepository.findAllByItemIdAndBookerIdAndStatus(itemId, userId, StatusType.APPROVED,
                Sort.by(Sort.Direction.DESC, "start")).orElseThrow(() -> new NotFoundException(
                String.format("Пользователь с id %d не арендовал вещь с id %d", userId, itemId)));

        bookings.stream().filter(booking -> booking.getEnd().isBefore(LocalDateTime.now())).findAny().orElseThrow(() ->
                new NotValidException(String.format("Пользователь с id %d не может оставлять комментарии вещи " +
                        "с id %d.", userId, itemId)));
        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());
        Comment commentSaved = commentRepository.save(comment);

        return commentMapper.toDto(commentSaved);
    }

    private void itemIdValidator(long id) {
        try {
            Item itemFromBd = itemStorage.getById(id);
            if (itemFromBd.getName().isBlank()) {
                throw new NotValidException("Имя не может быть пустым");
            } else if (itemFromBd.getDescription().isBlank()) {
                throw new NotValidException("Описание не может быть пустым");
            }
        } catch (NullPointerException e) {
            throw new NotFoundException("Предмет с id: " + id + " не найден");
        }
    }

    private void itemOwnerCheckValidator(User owner, Item newItem, long id) {
        if (owner == null) {
            throw new NotFoundException(String.format("Пользователь с id = %s не найден", id));
        } else {
            newItem.setOwner(owner);
        }
    }

    private void itemOwnerNameDescAvailValidator(Item item, Item oldItem, long userId) {
        if (oldItem.getOwner().getId() != userId) {
            throw new NotFoundException("Пользователь не является носителем данного предмета!");
        }
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
    }

    private void setBookings(ItemDtoWithBooking itemDtoWithBooking, List<BookingItemDto> bookings) {
        itemDtoWithBooking.setLastBooking(bookings.stream()
                .filter(booking -> booking.getItem().getId() == itemDtoWithBooking.getId() &&
                        booking.getStart().isBefore(LocalDateTime.now()))
                .max(Comparator.comparing(BookingItemDto::getStart)).orElse(null));

        itemDtoWithBooking.setNextBooking(bookings.stream()
                .filter(booking -> booking.getItem().getId() == itemDtoWithBooking.getId() &&
                        booking.getStart().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(BookingItemDto::getStart)).orElse(null));
    }

    private void setComments(List<ItemDtoWithBooking> itemDto, List<Comment> comments) {
        for (ItemDtoWithBooking itemDtoWithBooking : itemDto) {
            itemDtoWithBooking.setComments(comments.stream()
                    .filter(comment -> comment.getItem().getId() == itemDtoWithBooking.getId())
                    .map(commentMapper::toDto)
                    .collect(Collectors.toList()));
        }
    }
}
