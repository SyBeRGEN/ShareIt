package ru.practicum.shareit.itemRequest.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "REQUESTS", schema = "PUBLIC")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    private Long id;

    @Size(max = 400)
    @NotNull
    @Column(name = "description", nullable = false, length = 400)
    private String description;

    @NotNull
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> items;

}