package ru.skdev.store.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skdev.model.Task;
import ru.skdev.model.User;
import ru.skdev.store.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HibernateTaskStore implements TaskStore {

    private final CrudRepository repository;

    @Override
    public Optional<Task> create(Task task) {
        try {
            repository.run(session -> session.persist(task));
            return Optional.of(task);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Task> findAll(User user) {
        String query = """
                FROM Task t
                JOIN FETCH t.priority
                WHERE t.user.id = :id
                ORDER BY t.id
                """;
        Map<String, Object> arguments = Map.of(
                "id", user.getId()
        );
        return repository.query(query, Task.class, arguments);
    }

    @Override
    public List<Task> findUndoneTasks(User user) {
        String query = """
                FROM Task t
                JOIN FETCH t.priority
                WHERE t.done = false AND t.user.id = :id
                ORDER BY t.id
                """;
        Map<String, Object> arguments = Map.of(
                "id", user.getId()
        );
        return repository.query(query, Task.class, arguments);
    }

    @Override
    public List<Task> findDoneTasks(User user) {
        String query = """
                FROM Task t
                JOIN FETCH t.priority
                WHERE t.done = true AND t.user.id = :id
                ORDER BY t.id
                """;
        Map<String, Object> arguments = Map.of(
                "id", user.getId()
        );
        return repository.query(query, Task.class, arguments);
    }

    @Override
    public boolean update(int id, Task task) {
        String query = """
                UPDATE Task t
                SET t.title = :title,
                    t.description = :description
                WHERE t.id = :id
                """;
        Map<String, Object> arguments = Map.of(
                "title", task.getTitle(),
                "description", task.getDescription(),
                "id", id
        );
        return repository.getBoolean(query, arguments);
    }

    @Override
    public boolean delete(int id) {
        String query = """
                DELETE FROM Task t
                WHERE t.id = :id
                """;
        Map<String, Object> arguments = Map.of(
                "id", id
        );
        return repository.getBoolean(query, arguments);
    }

    @Override
    public boolean updateDone(int id) {
        String query = """
                UPDATE Task t
                SET t.done = true
                WHERE t.id = :id
                """;
        Map<String, Object> arguments = Map.of(
                "id", id
        );
        return repository.getBoolean(query, arguments);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        String query = """
                FROM Task t
                WHERE t.id = :id
                """;
        Map<String, Object> arguments = Map.of(
                "id", id
        );
        return repository.optional(query, Task.class, arguments);
    }
}
