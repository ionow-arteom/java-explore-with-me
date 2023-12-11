package ru.practicum.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findBySubscriberId(Long subscriberId);
    boolean existsBySubscriberIdAndSubscribedToId(Long subscriberId, Long subscribedToId);
    void deleteBySubscriberIdAndSubscribedToId(Long subscriberId, Long subscribedToId);
}