package ru.skdev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skdev.model.Task;
import ru.skdev.model.User;
import ru.skdev.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore store;

    @Override
    public List<Task> findAll(User user) {
        return store.findAll(user);
    }

    @Override
    public List<Task> findUndoneTasks(User user) {
        return store.findUndoneTasks(user);
    }

    @Override
    public List<Task> findDoneTasks(User user) {
        return store.findDoneTasks(user);
    }

    @Override
    public Optional<Task> save(Task task) {
        return store.create(task);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return store.findById(id);
    }

    @Override
    public boolean delete(Integer id) {
        return store.delete(id);
    }

    @Override
    public boolean updateDone(Integer id) {
        return store.updateDone(id);
    }

    @Override
    public boolean update(Integer id, Task task) {
        return store.update(id, task);
    }
}
