package ru.skdev.service.category;

import ru.skdev.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    List<Category> findByIds(List<Integer> list);
}
