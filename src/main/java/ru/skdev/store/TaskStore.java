package ru.skdev.store;

import ru.skdev.model.Task;
import ru.skdev.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskStore {
    Optional<Task> create(Task task);

    List<Task> findAll(User user);

    List<Task> findUndoneTasks(User user);

    List<Task> findDoneTasks(User user);

    boolean update(int id, Task task);

    boolean delete(int id);

    boolean updateDone(int id);

    Optional<Task> findById(Integer id);
}
