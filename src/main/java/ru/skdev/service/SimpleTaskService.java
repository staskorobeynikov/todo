package ru.skdev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skdev.model.Task;
import ru.skdev.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore store;

    @Override
    public List<Task> findAll() {
        return store.findAll();
    }

    @Override
    public List<Task> findUndoneTasks() {
        return store.findUndoneTasks();
    }

    @Override
    public List<Task> findDoneTasks() {
        return store.findDoneTasks();
    }

    @Override
    public Optional<Task> save(Task task) {
        return store.create(task);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return store.findById(id);
    }
}
