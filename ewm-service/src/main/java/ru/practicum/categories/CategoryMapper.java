package ru.practicum.categories;

import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;
import java.util.List;

@UtilityClass
public class CategoryMapper {

    public CategoryDto returnCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category returnCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public List<CategoryDto> returnCategoryDtoList(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::returnCategoryDto)
                .collect(Collectors.toList());
    }
}

