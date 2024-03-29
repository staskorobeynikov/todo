package ru.skdev.store.user;

import ru.skdev.model.User;

import java.util.Optional;

public interface UserStore {

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);
}
