package ru.practicum.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.categories.Category;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.compilation.Compilation;
import ru.practicum.compilation.CompilationRepository;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.request.Request;
import ru.practicum.request.RequestRepository;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;
import ru.practicum.util.UnionServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnionServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private CompilationRepository compilationRepository;

    @InjectMocks
    private UnionServiceImpl unionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get user or throw NotFoundException")
    void getUserOrNotFound() {
        Long userId = 1L;
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = unionService.getUserOrNotFound(userId);

        assertEquals(user, result);
    }

    @Test
    @DisplayName("Get category or throw NotFoundException")
    void getCategoryOrNotFound() {
        Long categoryId = 1L;
        Category category = mock(Category.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = unionService.getCategoryOrNotFound(categoryId);

        assertEquals(category, result);
    }

    @Test
    @DisplayName("Get event or throw NotFoundException")
    void getEventOrNotFound() {
        Long eventId = 1L;
        Event event = mock(Event.class);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event result = unionService.getEventOrNotFound(eventId);

        assertEquals(event, result);
    }

    @Test
    @DisplayName("Get request or throw NotFoundException")
    void getRequestOrNotFound() {
        Long requestId = 1L;
        Request request = mock(Request.class);
        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));

        Request result = unionService.getRequestOrNotFound(requestId);

        assertEquals(request, result);
    }

    @Test
    @DisplayName("Get compilation or throw NotFoundException")
    void getCompilationOrNotFound() {
        Long compId = 1L;
        Compilation compilation = mock(Compilation.class);
        when(compilationRepository.findById(compId)).thenReturn(Optional.of(compilation));

        Compilation result = unionService.getCompilationOrNotFound(compId);

        assertEquals(compilation, result);
    }

    @Test
    @DisplayName("Return null for invalid date string")
    void parseInvalidDate() {
        String dateString = "invalid-date";

        LocalDateTime result = unionService.parseDate(dateString);

        assertNull(result);
    }
}