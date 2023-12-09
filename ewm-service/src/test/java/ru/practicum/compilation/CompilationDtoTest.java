package ru.practicum.compilation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.events.dto.EventShort;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilationDtoTest {

    @Test
    @DisplayName("CompilationDto Lombok functionality test")
    void testCompilationDtoLombokFeatures() {
        Long id = 1L;
        Boolean pinned = true;
        String title = "Compilation Title";
        Set<EventShort> events = new HashSet<>();
        CategoryDto categoryDto = new CategoryDto(1L, "Category Name");
        UserShortDto userShortDto = new UserShortDto(1L, "User Name");

        EventShort eventShort = EventShort.builder()
                .id(1L)
                .title("Event Title")
                .annotation("Event Annotation")
                .category(categoryDto)
                .confirmedRequests(0L)
                .eventDate(LocalDateTime.now())
                .initiator(userShortDto)
                .paid(true)
                .views(0L)
                .build();

        events.add(eventShort);

        CompilationDto compilationDto = CompilationDto.builder()
                .id(id)
                .pinned(pinned)
                .title(title)
                .events(events)
                .build();

        assertEquals(id, compilationDto.getId());
        assertEquals(pinned, compilationDto.getPinned());
        assertEquals(title, compilationDto.getTitle());
        assertEquals(events, compilationDto.getEvents());
        assertEquals(1, compilationDto.getEvents().size());

        EventShort actualEventShort = compilationDto.getEvents().iterator().next();
        assertEquals(eventShort.getId(), actualEventShort.getId());
        assertEquals(eventShort.getTitle(), actualEventShort.getTitle());
    }
}