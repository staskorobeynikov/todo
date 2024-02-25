package ru.skdev.store.category;

import ru.skdev.model.Category;

import java.util.List;

public interface CategoryStore {
    List<Category> findAll();

    List<Category> findByIds(List<Integer> list);
}
