package ru.practicum.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.errorhandling.ConflictException;
import ru.practicum.events.EventRepository;
import ru.practicum.util.UnionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UnionService unionService;

    @Transactional
    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        Category category = CategoryMapper.fromDto(categoryDto);
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto, Long categoryId) {
        Category existingCategory = unionService.getCategoryOrNotFound(categoryId);
        existingCategory.setName(categoryDto.getName());
        return CategoryMapper.toDto(categoryRepository.save(existingCategory));
    }

    @Transactional
    @Override
    public void delete(Long categoryId) {
        if (!eventRepository.findByCategoryId(categoryId).isEmpty()) {
            throw new ConflictException("Category with id " + categoryId + " is in use and cannot be deleted.");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> getList(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageRequest).stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        return CategoryMapper.toDto(unionService.getCategoryOrNotFound(categoryId));
    }
}