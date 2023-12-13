package ru.practicum.subscription;

import ru.practicum.subscription.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDto subscribe(Long subscriberId, Long subscribedToId);

    void unsubscribe(Long subscriberId, Long subscribedToId);

    List<SubscriptionDto> getSubscriptions(Long subscriberId);

    List<SubscriptionDto> getSubscribers(Long userId);

    List<String> getSubscribedUsers(Long userId);

    List<String> getSubscriberNames(Long userId);
}