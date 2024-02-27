package ru.skdev.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skdev.model.Category;
import ru.skdev.store.category.CategoryStore;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final CategoryStore store;

    @Override
    public List<Category> findAll() {
        return store.findAll();
    }

    @Override
    public List<Category> findByIds(List<Integer> list) {
        return store.findByIds(list);
    }
}
