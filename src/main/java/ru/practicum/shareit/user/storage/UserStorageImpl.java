package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidEmailException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long usedId = 0;

    @Override
    public User getById(long id) {
        validateById(id);
        return users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User create(User user) {
        validateEmail(user);
        user.setId(++usedId);
        users.put(user.getId(), user);
        return users.get(user.getId());
    }

    @Override
    public User update(User user) {
        validateById(user.getId());
        validateEmail(user);
        User updatedUser = users.get(user.getId());
        if (user.getName() != null && !user.getName().isEmpty()) {
            updatedUser.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            updatedUser.setEmail(user.getEmail());
        }
        users.put(updatedUser.getId(), updatedUser);
        return users.get(updatedUser.getId());
    }

    public Boolean deleteById(long id) {
        users.remove(id);
        return !users.containsKey(id);
    }

    private void validateById(long id) {
        if (id != 0 && !users.containsKey(id)) {
            throw new NotFoundException("Пользователь с идентификатором " +
                    id + " не зарегистрирован!");
        }
    }

    private void validateEmail(User user) {
        if (users.values()
                .stream()
                .anyMatch(
                        stored -> stored.getEmail().equalsIgnoreCase(user.getEmail())
                                && stored.getId() != user.getId()
                )
        ) {
            throw new NotValidEmailException("Пользователь с таким адресом Эл. почты " +
                    user.getEmail() + " уже существует!");
        }
    }
}
