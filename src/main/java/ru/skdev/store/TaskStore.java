package ru.skdev.store;

import ru.skdev.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskStore {
    Optional<Task> create(Task task);

    List<Task> findAll();

    List<Task> findUndoneTasks();

    List<Task> findDoneTasks();

    boolean update(int id, Task task);

    boolean delete(int id);

    boolean updateDone(int id);

    Optional<Task> findById(Integer id);
}
