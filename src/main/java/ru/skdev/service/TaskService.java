package ru.skdev.service;

import ru.skdev.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();

    List<Task> findUndoneTasks();

    List<Task> findDoneTasks();

    Optional<Task> save(Task task);
}
