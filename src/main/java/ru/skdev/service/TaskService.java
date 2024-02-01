package ru.skdev.service;

import ru.skdev.model.Task;
import ru.skdev.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll(User user);

    List<Task> findUndoneTasks(User user);

    List<Task> findDoneTasks(User user);

    Optional<Task> save(Task task);

    Optional<Task> findById(Integer id);

    boolean delete(Integer id);

    boolean updateDone(Integer id);

    boolean update(Integer id, Task task);
}
