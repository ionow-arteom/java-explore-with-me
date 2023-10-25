package ru.practicum.events;

import lombok.experimental.UtilityClass;
import ru.practicum.categories.Category;
import ru.practicum.categories.CategoryMapper;
import ru.practicum.events.dto.EventFull;
import ru.practicum.events.dto.EventNew;
import ru.practicum.events.dto.EventShort;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static ru.practicum.util.enumerated.State.PENDING;

@UtilityClass
public class EventMapper {

    public Event toEvent(EventNew eventNew, Category category, Location location, User user) {
        return Event.builder()
                .annotation(eventNew.getAnnotation())
                .category(category)
                .description(eventNew.getDescription())
                .eventDate(eventNew.getEventDate())
                .initiator(user)
                .location(location)
                .paid(eventNew.getPaid())
                .participantLimit(eventNew.getParticipantLimit())
                .requestModeration(eventNew.getRequestModeration())
                .createdOn(LocalDateTime.now())
                .views(0L)
                .state(PENDING)
                .confirmedRequests(0L)
                .title(eventNew.getTitle())
                .build();
    }

    public EventFull toEventFullDto(Event event) {
        EventFull eventFullDto = EventFull.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.returnCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.returnLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
        return eventFullDto;
    }

    public EventShort toEventShortDto(Event event) {
        return EventShort.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.returnCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public List<EventFull> toEventFullList(Iterable<Event> events) {
        List<EventFull> result = new ArrayList<>();
        events.forEach(event -> result.add(toEventFullDto(event)));
        return result;
    }

    public List<EventShort> toEventShortDtoList(Iterable<Event> events) {
        List<EventShort> result = new ArrayList<>();
        events.forEach(event -> result.add(toEventShortDto(event)));
        return result;
    }
}