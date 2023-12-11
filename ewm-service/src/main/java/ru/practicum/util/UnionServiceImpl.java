package ru.practicum.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.categories.Category;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.compilation.Compilation;
import ru.practicum.compilation.CompilationRepository;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.errorhandling.NotFoundException;
import ru.practicum.request.Request;
import ru.practicum.request.RequestRepository;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.time.LocalDateTime;

import static ru.practicum.dto.utilities.Constants.DATE_TIME_FORMATTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements UnionService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;

    @Override
    public User getUserOrNotFound(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new NotFoundException(User.class, "User id " + userId + " not found.");
                });
    }

    @Override
    public Category getCategoryOrNotFound(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category not found with ID: {}", categoryId);
                    return new NotFoundException(Category.class, "Category id " + categoryId + " not found.");
                });
    }

    @Override
    public Event getEventOrNotFound(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    log.error("Event not found with ID: {}", eventId);
                    return new NotFoundException(Event.class, "Event id " + eventId + " not found.");
                });
    }

    @Override
    public Request getRequestOrNotFound(Long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> {
                    log.error("Request not found with ID: {}", requestId);
                    return new NotFoundException(Request.class, "Request id " + requestId + " not found.");
                });
    }

    @Override
    public Compilation getCompilationOrNotFound(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> {
                    log.error("Compilation not found with ID: {}", compId);
                    return new NotFoundException(Compilation.class, "Compilation id " + compId + " not found.");
                });
    }

    @Override
    public LocalDateTime parseDate(String date) {
        if (date != null) {
            try {
                return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
            } catch (Exception e) {
                log.error("Error parsing date: {}", date, e);
                return null;
            }
        } else {
            return null;
        }
    }
}