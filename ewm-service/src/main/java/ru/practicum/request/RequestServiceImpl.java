package ru.practicum.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.EventRepository;
import ru.practicum.errorhandling.ConflictException;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.user.User;
import ru.practicum.events.model.Event;
import ru.practicum.util.enumerated.State;
import ru.practicum.util.enumerated.Status;
import java.time.LocalDateTime;
import ru.practicum.util.UnionService;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UnionService unionService;

    @Override
    @Transactional
    public RequestDto addRequest(Long userId, Long eventId) {
        User user = unionService.getUserOrNotFound(userId);
        Event event = unionService.getEventOrNotFound(eventId);

        validateRequest(user, event);

        Request request = createNewRequest(user, event);
        request = requestRepository.save(request);

        if (isAutoConfirmEnabled(event)) {
            confirmRequest(event, request);
        }

        log.info("Request added with ID: {}", request.getId());
        return RequestMapper.mapToRequestDto(request);
    }

    private void validateRequest(User user, Event event) {
        if (isRequestLimitExceeded(event)) {
            throw new ConflictException(String.format("Event %s is already at full capacity.", event.getTitle()));
        }
        if (isUserEventInitiator(user, event)) {
            throw new ConflictException("Event initiators cannot participate in their own events.");
        }
        if (isDuplicateRequest(user, event)) {
            throw new ConflictException("You have already applied to this event.");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("This event is not yet published.");
        }
    }

    private boolean isRequestLimitExceeded(Event event) {
        return event.getParticipantLimit() <= event.getConfirmedRequests() && event.getParticipantLimit() != 0;
    }

    private boolean isUserEventInitiator(User user, Event event) {
        return event.getInitiator().getId().equals(user.getId());
    }

    private boolean isDuplicateRequest(User user, Event event) {
        return requestRepository.findByRequesterIdAndEventId(user.getId(), event.getId()).isPresent();
    }

    private Request createNewRequest(User user, Event event) {
        return Request.builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .status(Status.PENDING)
                .build();
    }

    private boolean isAutoConfirmEnabled(Event event) {
        return !event.getRequestModeration() || event.getParticipantLimit() == 0;
    }

    private void confirmRequest(Event event, Request request) {
        request.setStatus(Status.CONFIRMED);
        event.setConfirmedRequests(requestRepository.countAllByEventIdAndStatus(event.getId(), Status.CONFIRMED));
        eventRepository.save(event);
    }

    @Override
    public List<RequestDto> getRequestsByUserId(Long userId) {
        unionService.getUserOrNotFound(userId);
        List<Request> requestList = requestRepository.findAllByRequesterId(userId);
        log.info("Retrieved {} requests for user ID: {}", requestList.size(), userId);
        return RequestMapper.mapToRequestDtoList(requestList);
    }

    @Override
    @Transactional
    public RequestDto cancelRequest(Long userId, Long requestId) {
        unionService.getUserOrNotFound(userId);
        Request request = unionService.getRequestOrNotFound(requestId);
        request.setStatus(Status.CANCELED);
        log.info("Request with ID: {} has been canceled", requestId);
        return RequestMapper.mapToRequestDto(requestRepository.save(request));
    }
}