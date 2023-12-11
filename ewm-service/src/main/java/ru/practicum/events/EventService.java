package ru.practicum.events;

import ru.practicum.events.dto.*;
import ru.practicum.request.dto.RequestDto;

import java.util.List;

public interface EventService {

    /**
     * Adds a new event associated with the provided user ID.
     * @param userId The user ID.
     * @param eventNew The details of the new event.
     * @return The full details of the added event.
     */
    EventFull addEvent(Long userId, EventNew eventNew);

    /**
     * Retrieves an event associated with the provided user ID and event ID.
     * @param userId The user ID.
     * @param eventId The event ID.
     * @return The full details of the event.
     */
    EventFull getUserEventById(Long userId, Long eventId);

    /**
     * Retrieves a list of events associated with the provided user ID, using pagination.
     * @param userId The user ID.
     * @param from The starting index.
     * @param size The size of the list.
     * @return A list of events.
     */
    List<EventShort> getAllEventsByUserId(Long userId, Integer from, Integer size);

    /**
     * Update the details of an event associated with the provided user ID.
     * @param eventUpdateDto The details for the event update.
     * @param userId The user ID.
     * @param eventId The event ID.
     * @return The updated event details.
     */
    EventFull updateEventByUserId(EventUpdate eventUpdateDto, Long userId, Long eventId);

    /**
     * Update the details of an event by an admin.
     * @param eventUpdateDto The details for the event update.
     * @param eventId The event ID.
     * @return The updated event details.
     */
    EventFull updateEventByAdmin(EventUpdate eventUpdateDto, Long eventId);

    /**
     * Retrieves all requests associated with an event and user.
     * @param userId The user ID.
     * @param eventId The event ID.
     * @return A list of requests.
     */
    List<RequestDto> getRequestsForEventIdByUserId(Long userId, Long eventId);

    /**
     * Update the status of multiple requests associated with a specific event and user.
     * @param requestDto The request containing status update details.
     * @param userId The user ID.
     * @param eventId The event ID.
     * @return The result of the request updates.
     */
    RequestUpdateResult updateStatusRequestsForEventIdByUserId(RequestUpdate requestDto, Long userId, Long eventId);

    /**
     * Retrieves a public event by ID. Also logs the URI and IP.
     * @param eventId The event ID.
     * @param uri The accessed URI.
     * @param ip The IP of the requester.
     * @return The full details of the event.
     */
    EventFull getEventById(Long eventId, String uri, String ip);

    /**
     * Retrieves events filtered by various criteria, specifically for admin views.
     * @param users List of user IDs.
     * @param states List of event states.
     * @param categories List of category IDs.
     * @param startTime The start time filter.
     * @param endTime The end time filter.
     * @param from Pagination starting index.
     * @param size Size of the list/page.
     * @return A list of events matching the criteria.
     */
    List<EventFull> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories, String startTime,
                                     String endTime, Integer from, Integer size);

    /**
     * Retrieves a list of public events based on various filters.
     * @param text Search text.
     * @param categories List of category IDs.
     * @param paid Whether the event is paid.
     * @param startTime The starting time.
     * @param endTime The ending time.
     * @param onlyAvailable Whether to filter by availability.
     * @param sort The sorting criteria.
     * @param from The starting index.
     * @param size The size of the list.
     * @param uri The accessed URI.
     * @param ip The IP of the requester.
     * @return A list of events.
     */
    List<EventShort> getEventsByPublic(String text, List<Long> categories, Boolean paid, String startTime, String endTime,
                                       Boolean onlyAvailable, String sort, Integer from, Integer size, String uri, String ip);
}
