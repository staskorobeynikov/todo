package ru.skdev.store;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
@Repository
public class CrudRepository {
    private final SessionFactory factory;

    public void run(Consumer<Session> command) {
        execute(session -> {
                    command.accept(session);
                    return null;
                }
        );
    }

    public void run(String query, Map<String, Object> arguments) {
        Consumer<Session> command = session -> {
            var sessionQuery = session
                    .createQuery(query);
            for (Map.Entry<String, Object> argument : arguments.entrySet()) {
                sessionQuery.setParameter(argument.getKey(), argument.getValue());
            }
            sessionQuery.executeUpdate();
        };
        run(command);
    }

    public boolean getBoolean(String query, Map<String, Object> arguments) {
        Function<Session, Boolean> command = session -> {
            var sessionQuery = session
                    .createQuery(query);
            for (Map.Entry<String, Object> argument : arguments.entrySet()) {
                sessionQuery.setParameter(argument.getKey(), argument.getValue());
            }
            return sessionQuery.executeUpdate() > 0;
        };
        return execute(command);
    }

    public <T> Optional<T> optional(String query, Class<T> clazz, Map<String, Object> arguments) {
        Function<Session, Optional<T>> command = session -> {
            var sessionQuery = session
                    .createQuery(query, clazz);
            for (Map.Entry<String, Object> argument : arguments.entrySet()) {
                sessionQuery.setParameter(argument.getKey(), argument.getValue());
            }
            return sessionQuery.uniqueResultOptional();
        };
        return execute(command);
    }

    public <T> List<T> query(String query, Class<T> clazz) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, clazz)
                .list();
        return execute(command);
    }

    public <T> List<T> query(String query, Class<T> clazz, Map<String, Object> arguments) {
        Function<Session, List<T>> command = session -> {
            var sessionQuery = session
                    .createQuery(query, clazz);
            for (Map.Entry<String, Object> argument : arguments.entrySet()) {
                sessionQuery.setParameter(argument.getKey(), argument.getValue());
            }
            return sessionQuery.list();
        };
        return execute(command);
    }

    public <T> T execute(Function<Session, T> command) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
