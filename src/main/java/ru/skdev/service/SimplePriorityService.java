package ru.skdev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skdev.model.Priority;
import ru.skdev.store.priority.PriorityStore;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimplePriorityService implements PriorityService {

    private final PriorityStore store;

    @Override
    public List<Priority> findAll() {
        return store.findAll();
    }
}
