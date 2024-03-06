package ru.skdev.converter;

import org.springframework.core.convert.converter.Converter;
import ru.skdev.model.Category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayToCategoryListConverter implements Converter<String[], List<Category>> {
    @Override
    public List<Category> convert(String[] source) {
        return Arrays.stream(source)
                .map(Integer::parseInt)
                .map(id -> Category.builder()
                                    .id(id)
                                    .build())
                .collect(Collectors.toList());
    }
}
