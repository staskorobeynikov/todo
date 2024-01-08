package ru.skdev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skdev.model.Task;
import ru.skdev.store.TaskStore;

import java.util.List;

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
}
