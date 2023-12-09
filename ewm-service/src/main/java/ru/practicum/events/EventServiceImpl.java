package ru.practicum.events;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.StatsClient;
import ru.practicum.categories.Category;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;
import ru.practicum.events.dto.*;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.errorhandling.ConflictException;
import ru.practicum.errorhandling.NotFoundException;
import ru.practicum.errorhandling.ValidationException;
import ru.practicum.request.Request;
import ru.practicum.request.RequestMapper;
import ru.practicum.request.RequestRepository;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.user.User;
import ru.practicum.util.enumerated.StateAction;
import ru.practicum.util.enumerated.Status;
import ru.practicum.util.UnionService;
import ru.practicum.util.enumerated.State;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

import static ru.practicum.dto.utilities.Constants.START_TIME;
import static ru.practicum.util.enumerated.State.PUBLISHED;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    private final UnionService unionService;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;
    private final StatsClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public EventFull addEvent(Long userId, EventNew eventNewDto) {
        log.info("Adding new event for user with ID: {}", userId);

        User user = unionService.getUserOrNotFound(userId);
        Category category = unionService.getCategoryOrNotFound(eventNewDto.getCategory());
        Location location = locationRepository.save(LocationMapper.returnLocation(eventNewDto.getLocation()));
        Event event = EventMapper.toEvent(eventNewDto, category, location, user);
        eventRepository.save(event);

        log.info("Event added successfully for user with ID: {}", userId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShort> getAllEventsByUserId(Long userId, Integer from, Integer size) {
        log.info("Fetching all events for user with ID: {}", userId);

        unionService.getUserOrNotFound(userId);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findByInitiatorId(userId, pageRequest);

        log.info("Fetched {} events for user with ID: {}", events.size(), userId);
        return EventMapper.toEventShortDtoList(events);
    }

    @Override
    public EventFull getUserEventById(Long userId, Long eventId) {
        log.info("Fetching event with ID: {} for user with ID: {}", eventId, userId);

        unionService.getUserOrNotFound(userId);
        unionService.getEventOrNotFound(eventId);
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId);

        log.info("Fetched event with ID: {} for user with ID: {}", eventId, userId);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFull updateEventByUserId(EventUpdate eventUpdate, Long userId, Long eventId) {
        log.info("Updating event with ID: {} for user with ID: {}", eventId, userId);

        User user = unionService.getUserOrNotFound(userId);
        Event event = unionService.getEventOrNotFound(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            log.warn("Conflict: User {} is not the initiator of the event {}.", userId, eventId);
            throw new ConflictException(String.format("User %s is not the initiator of the event %s.",userId, eventId));
        }
        if (event.getState().equals(PUBLISHED)) {
            log.warn("Conflict: User {} cannot update event {} that has already been published.", userId, eventId);
            throw new ConflictException(String.format("User %s cannot update event %s that has already been published.",userId, eventId));
        }

        Event updatedEvent = baseUpdateEvent(event, eventUpdate);

        log.info("Updated event with ID: {} for user with ID: {}", eventId, userId);
        return EventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    public List<RequestDto> getRequestsForEventIdByUserId(Long userId, Long eventId) {
        log.info("Fetching requests for event with ID: {} by user with ID: {}", eventId, userId);
        User user = unionService.getUserOrNotFound(userId);
        Event event = unionService.getEventOrNotFound(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format("User %s is not the initiator of the event %s.",userId, eventId));
        }
        List<Request> requests = requestRepository.findAllByEventId(eventId);

        log.info("Fetched {} requests for event with ID: {} by user with ID: {}", requests.size(), eventId, userId);
        return RequestMapper.mapToRequestDtoList(requests);
    }

    @Override
    @Transactional
    public RequestUpdateResult updateStatusRequestsForEventIdByUserId(RequestUpdate requestDto, Long userId, Long eventId) {
        log.info("Updating request status for event with ID: {} by user with ID: {}", eventId, userId);
        User user = unionService.getUserOrNotFound(userId);
        Event event = unionService.getEventOrNotFound(eventId);

        validateRequestUpdateConditions(event, user);
        List<Request> requestsList = requestRepository.findAllById(requestDto.getRequestIds());
        RequestUpdateResult result = processRequestUpdates(event, requestsList, requestDto, eventId);

        eventRepository.save(event);
        requestRepository.saveAll(requestsList);

        log.info("Updated request status with {} confirmed and {} rejected for event with ID: {} by user with ID: {}",
                result.getConfirmedRequests().size(), result.getRejectedRequests().size(), eventId, userId);

        return result;
    }

    private void validateRequestUpdateConditions(Event event, User user) {
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format("User %s is not the initiator of the event %s.", user.getId(), event.getId()));
        }
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConflictException("Request moderation not enabled or participant limit is zero.");
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Exceeded the limit of participants.");
        }
    }

    private RequestUpdateResult processRequestUpdates(Event event, List<Request> requestsList, RequestUpdate requestDto, Long eventId) {
        long vacantPlaces = event.getParticipantLimit() - event.getConfirmedRequests();
        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();

        for (Request request : requestsList) {
            if (!request.getStatus().equals(Status.PENDING)) {
                throw new ConflictException("Request must have status PENDING.");
            }
            if (requestDto.getStatus().equals(Status.CONFIRMED) && vacantPlaces > 0) {
                request.setStatus(Status.CONFIRMED);
                confirmedRequests.add(RequestMapper.mapToRequestDto(request));
                vacantPlaces--;
            } else {
                request.setStatus(Status.REJECTED);
                rejectedRequests.add(RequestMapper.mapToRequestDto(request));
            }
        }
        event.setConfirmedRequests(event.getConfirmedRequests() + confirmedRequests.size());

        log.debug("Processed {} requests: {} confirmed, {} rejected for event ID {}",
                requestsList.size(), confirmedRequests.size(), rejectedRequests.size(), eventId);

        return RequestUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    @Override
    @Transactional
    public EventFull updateEventByAdmin(EventUpdate eventUpdate, Long eventId) {
        log.info("Admin updating event with ID: {}", eventId);
        Event event = unionService.getEventOrNotFound(eventId);
        if (eventUpdate.getStateAction() != null) {
            if (eventUpdate.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                if (!event.getState().equals(State.PENDING)) {
                    throw new ConflictException(String.format("Event - %s, has already been published, cannot be published again ", event.getTitle()));
                }
                event.setPublishedOn(LocalDateTime.now());
                event.setState(PUBLISHED);
            } else {
                if (!event.getState().equals(State.PENDING)) {
                    throw new ConflictException(String.format("Event - %s, cannot be canceled because its statute is not \"PENDING\"", event.getTitle()));
                }
                event.setState(State.CANCELED);
            }
        }
        Event updateEvent = baseUpdateEvent(event, eventUpdate);
        log.info("Admin successfully updated event with ID: {}", eventId);
        return EventMapper.toEventFullDto(updateEvent);
    }

    @Override
    public List<EventFull> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                            String rangeStart, String rangeEnd, Integer from, Integer size) {
        log.info("Admin fetching events with given parameters");
        LocalDateTime startTime = unionService.parseDate(rangeStart);
        LocalDateTime endTime = unionService.parseDate(rangeEnd);
        List<State> statesValue = new ArrayList<>();
        if (states != null) {
            for (String state : states) {
                statesValue.add(State.getStateValue(state));
            }
        }
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
        }
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findEventsByAdminFromParam(users, statesValue, categories,  startTime, endTime, pageRequest);
        log.info("Admin fetched {} events with given parameters", events.size());

        return EventMapper.toEventFullList(events);
    }

    @Override
    public EventFull getEventById(Long eventId, String uri, String ip) {
        log.info("Fetching event with ID: {} for URI: {} and IP: {}", eventId, uri, ip);
        Event event = unionService.getEventOrNotFound(eventId);
        if (!event.getState().equals(PUBLISHED)) {
            throw new NotFoundException(Event.class, String.format("Event %s not published", eventId));
        }
        sendInfo(uri, ip);
        event.setViews(getViewsEventById(event.getId()));
        eventRepository.save(event);
        log.info("Fetched event with ID: {} successfully", eventId);

        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShort> getEventsByPublic(String text, List<Long> categories, Boolean paid, String rangeStart,
                                              String rangeEnd, Boolean onlyAvailable, String sort, Integer from,
                                              Integer size, String uri, String ip) {
        log.info("Fetching public events with given parameters");
        LocalDateTime startTime = unionService.parseDate(rangeStart);
        LocalDateTime endTime = unionService.parseDate(rangeEnd);
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
        }
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findEventsByPublicFromParam(text, categories, paid, startTime, endTime, onlyAvailable, sort, pageRequest);

        sendInfo(uri, ip);
        for (Event event : events) {
            event.setViews(getViewsEventById(event.getId()));
            eventRepository.save(event);
        }
        log.info("Fetched {} public events with given parameters", events.size());
        return EventMapper.toEventShortDtoList(events);
    }

    private Event baseUpdateEvent(Event event, EventUpdate eventUpdateDto) {

        log.info("Updating event with ID: {}", event.getId());

        setIfPresent(eventUpdateDto.getAnnotation(), event::setAnnotation);
        setIfPresent(eventUpdateDto.getCategory(), id -> event.setCategory(unionService.getCategoryOrNotFound(id)));
        setIfPresent(eventUpdateDto.getDescription(), event::setDescription);
        setIfPresent(eventUpdateDto.getEventDate(), event::setEventDate);
        setIfPresent(eventUpdateDto.getLocation(), loc -> event.setLocation(LocationMapper.returnLocation(loc)));
        setIfPresent(eventUpdateDto.getPaid(), event::setPaid);
        setIfPresent(eventUpdateDto.getParticipantLimit(), event::setParticipantLimit);
        setIfPresent(eventUpdateDto.getRequestModeration(), event::setRequestModeration);
        setIfPresent(eventUpdateDto.getStateAction(), action -> updateEventState(action, event));
        setIfPresent(eventUpdateDto.getTitle(), event::setTitle);

        locationRepository.save(event.getLocation());

        Event updatedEvent = eventRepository.save(event);
        log.info("Updated event: {}", updatedEvent);
        return updatedEvent;
    }

    private <T> void setIfPresent(T value, Consumer<T> setter) {
        Optional.ofNullable(value).ifPresent(valuePresent -> {
            setter.accept(valuePresent);
            log.info("Set value: {}", valuePresent);
        });
    }

    private void updateEventState(StateAction stateAction, Event event) {
        switch (stateAction) {
            case PUBLISH_EVENT:
                event.setState(PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
                log.info("Event set to PUBLISHED state");
                break;
            case REJECT_EVENT:
            case CANCEL_REVIEW:
                event.setState(State.CANCELED);
                log.info("Event set to CANCELED state");
                break;
            case SEND_TO_REVIEW:
                event.setState(State.PENDING);
                log.info("Event set to PENDING state");
                break;
        }
    }

    private void sendInfo(String uri, String ip) {
        HitDto hitDto = HitDto.builder()
                .app("ewm-service")
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
        client.addHit(hitDto);
    }

    private Long getViewsEventById(Long eventId) {
        String uri = "/events/" + eventId;
        ResponseEntity<Object> response = client.findStats(START_TIME, LocalDateTime.now(), uri, true);
        List<StatsDto> result = objectMapper.convertValue(response.getBody(), new TypeReference<>() {});
        if (result.isEmpty()) {
            return 0L;
        } else {
            return result.get(0).getHits();
        }
    }
}