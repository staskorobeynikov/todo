package ru.skdev.store.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skdev.model.User;
import ru.skdev.store.CrudRepository;

import java.util.Map;
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

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        String query = """
                FROM User u
                WHERE u.login = :login AND u.password = :password
                """;
        Map<String, Object> arguments = Map.of(
                "login", login,
                "password", password
        );
        return repository.optional(query, User.class, arguments);
    }
}
