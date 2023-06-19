package ru.practicum.shareit.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "PUBLIC")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Size(max = 140)
    @NotNull
    @Column(name = "name", nullable = false, length = 140)
    private String name;

    @Size(max = 140)
    @NotNull
    @Column(name = "email", nullable = false, length = 140, unique = true)
    private String email;

}