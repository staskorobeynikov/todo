package ru.skdev.store;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.skdev.model.Priority;
import ru.skdev.model.Task;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Repository
public class HibernatePriorityStore implements PriorityStore {

    private final CrudRepository repository;

    @Override
    public List<Priority> findAll() {
        String query = """
                FROM Priority t
                """;
        return repository.query(query, Priority.class);
    }
}
