package ru.practicum.categories;

import java.util.List;

public interface CategoryService {

    /**
     * Adds a new category.
     * @param categoryDto details of the category to add
     * @return the added category
     */
    CategoryDto add(CategoryDto categoryDto);

    /**
     * Updates the specified category.
     * @param categoryDto   updated category details
     * @param categoryId    ID of the category to update
     * @return the updated category
     */
    CategoryDto update(CategoryDto categoryDto, Long categoryId);

    /**
     * Removes the category with the given ID.
     * @param categoryId ID of the category to remove
     */
    void delete(Long categoryId);

    /**
     * Retrieves a list of categories with pagination.
     * @param from  starting index
     * @param size  number of categories to retrieve
     * @return list of categories
     */
    List<CategoryDto> getList(Integer from, Integer size);

    /**
     * Fetches the category by its ID.
     * @param categoryId ID of the category to fetch
     * @return the category details
     */
    CategoryDto getById(Long categoryId);
}