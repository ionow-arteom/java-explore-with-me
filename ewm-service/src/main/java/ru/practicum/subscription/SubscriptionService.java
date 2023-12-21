package ru.practicum.subscription;

import ru.practicum.subscription.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {

    /**
     * Subscribes a user to another user.
     * @param subscriberId unique identifier of the subscriber.
     * @param subscribedToId unique identifier of the user to be subscribed to.
     * @return details of the subscription.
     */
    SubscriptionDto subscribe(Long subscriberId, Long subscribedToId);

    /**
     * Unsubscribes a user from another user.
     * @param subscriberId unique identifier of the subscriber.
     * @param subscribedToId unique identifier of the user to be unsubscribed from.
     * @return a confirmation message.
     */
    String unsubscribe(Long subscriberId, Long subscribedToId);

    /**
     * Retrieves a list of subscriptions for a user.
     * @param subscriberId unique identifier of the subscriber.
     * @return list of subscription details.
     */
    List<SubscriptionDto> getSubscriptions(Long subscriberId);

    /**
     * Retrieves a list of users who are subscribers of a specific user.
     * @param userId unique identifier of the user.
     * @return list of subscriber details.
     */
    List<SubscriptionDto> getSubscribers(Long userId);

    /**
     * Retrieves a list of usernames of users who are subscribed to a specific user.
     * @param userId unique identifier of the user.
     * @return list of subscribed usernames.
     */
    List<String> getSubscribedUsers(Long userId);

    /**
     * Retrieves a list of names of users who are subscribers of a specific user.
     * @param userId unique identifier of the user.
     * @return list of subscriber names.
     */
    List<String> getSubscriberNames(Long userId);
}