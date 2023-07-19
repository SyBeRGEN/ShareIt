package ru.practicum.shareit.user.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidEmailException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class UserStorageImpl implements UserStorage {
    private final UserRepository repository;

    @Transactional
    @Override
    public User getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с идентификатором %s не найден", id)));
    }

    @Transactional
    @Override
    public Collection<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public User create(User user) {
        try {
            repository.save(user);
        } catch (RuntimeException e) {
            throw new NotValidEmailException("Пользователь с таким адресом Эл. почты " +
                    user.getEmail() + " уже существует!");
        }
        return user;
    }

    @Transactional
    @Override
    public User update(User user) {
        User userFromDb = repository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с идентификатором %s не найден", user.getId())));

        if (user.getName() == null) {
            user.setName(userFromDb.getName());
        } else if (user.getEmail() == null) {
            user.setEmail(userFromDb.getEmail());
        }
        repository.save(user);

        return user;
    }

    public Boolean deleteById(long id) {
        try {
            repository.deleteById(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
