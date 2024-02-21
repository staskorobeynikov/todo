package ru.skdev.store.priority;

import ru.skdev.model.Priority;

import java.util.List;

public interface PriorityStore {
    List<Priority> findAll();
}
