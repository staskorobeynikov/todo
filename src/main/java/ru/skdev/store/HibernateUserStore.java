package ru.skdev.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skdev.model.User;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HibernateUserStore implements UserStore {
    private final CrudRepository repository;

    @Override
    public Optional<User> save(User user) {
        try {
            repository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
