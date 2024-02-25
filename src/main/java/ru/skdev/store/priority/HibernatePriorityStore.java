package ru.skdev.store.priority;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skdev.model.Priority;
import ru.skdev.store.CrudRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HibernatePriorityStore implements PriorityStore {

    private final CrudRepository repository;

    @Override
    public List<Priority> findAll() {
        String query = """
                FROM Priority p
                """;
        return repository.query(query, Priority.class);
    }
}
