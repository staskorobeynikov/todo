package ru.skdev.service;

import ru.skdev.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    List<Task> findUndoneTasks();

    List<Task> findDoneTasks();
}
