package ru.skdev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skdev.model.User;
import ru.skdev.store.UserStore;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleUserService implements UserService {
    private final UserStore userStore;

    @Override
    public Optional<User> save(User user) {
        return userStore.save(user);
    }

    @Override
    public Optional<User> findByLoginAndPassword(User user) {
        return userStore.findByLoginAndPassword(user.getLogin(), user.getPassword());
    }
}
