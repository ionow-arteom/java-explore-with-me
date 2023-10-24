package ru.practicum.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.errorhandling.exception.ConflictException;
import ru.practicum.events.EventRepository;
import ru.practicum.util.UnionService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UnionService unionService;

    private CategoryDto convertToDto(Category category) {
        return CategoryMapper.returnCategoryDto(category);
    }

    private Category convertToEntity(CategoryDto categoryDto) {
        return CategoryMapper.returnCategory(categoryDto);
    }

    @Transactional
    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        Category category = convertToEntity(categoryDto);
        categoryRepository.save(category);
        return convertToDto(category);
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto, Long categoryId) {
        Category category = unionService.getCategoryOrNotFound(categoryId);
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return convertToDto(category);
    }

    @Transactional
    @Override
    public void delete(Long categoryId) {
        if (!eventRepository.findByCategoryId(categoryId).isEmpty()) {
            throw new ConflictException(String.format("Category id %s is in use and cannot be deleted", categoryId));
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> getList(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return CategoryMapper.returnCategoryDtoList((List<Category>) categoryRepository.findAll(pageRequest));
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        Category category = unionService.getCategoryOrNotFound(categoryId);
        return convertToDto(category);
    }
}