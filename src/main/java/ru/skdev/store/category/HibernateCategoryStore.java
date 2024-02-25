package ru.skdev.store.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.skdev.model.Category;
import ru.skdev.store.CrudRepository;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class HibernateCategoryStore implements CategoryStore {

    private final CrudRepository repository;

    @Override
    public List<Category> findAll() {
        String query = """
                FROM Category c
                """;
        return repository.query(query, Category.class);
    }

    @Override
    public List<Category> findByIds(List<Integer> list) {
        String query = """
                FROM Category c
                WHERE c.id IN :ids
                """;
        Map<String, Object> arguments = Map.of(
                "ids", list
        );
        return repository.query(query, Category.class, arguments);
    }
}
