package ru.practicum.request;

import java.util.List;

public interface RequestService {

    /**
     * Adds a new request for a specific user and event.
     * @param userId The ID of the user making the request.
     * @param eventId The ID of the event for which the request is made.
     * @return The details of the added request.
     */
    RequestDto addRequest(Long userId, Long eventId);

    /**
     * Retrieves a list of requests made by a specific user.
     * @param userId The ID of the user whose requests are to be retrieved.
     * @return A list of requests made by the specified user.
     */
    List<RequestDto> getRequestsByUserId(Long userId);

    /**
     * Cancels a specific request made by a user.
     * @param userId The ID of the user canceling the request.
     * @param requestId The ID of the request to be canceled.
     * @return The details of the canceled request.
     */
    RequestDto cancelRequest(Long userId, Long requestId);
}