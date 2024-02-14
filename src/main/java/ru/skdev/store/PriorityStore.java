package ru.skdev.store;

import ru.skdev.model.Priority;

import java.util.List;

public interface PriorityStore {
    List<Priority> findAll();
}
