package ru.practicum.subscription;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDto subscribe(Long subscriberId, Long subscribedToId);

    void unsubscribe(Long subscriberId, Long subscribedToId);

    List<SubscriptionDto> getSubscriptions(Long subscriberId);
}