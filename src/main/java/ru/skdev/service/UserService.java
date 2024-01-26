package ru.skdev.service;

import ru.skdev.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(User user);
}
