package ru.practicum.subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.errorhandling.AlreadySubscribedException;
import ru.practicum.errorhandling.NotFoundException;
import ru.practicum.errorhandling.SubscriptionNotAllowedException;
import ru.practicum.errorhandling.SubscriptionNotFoundException;
import ru.practicum.subscription.dto.SubscriptionDto;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public SubscriptionDto subscribe(Long subscriberId, Long subscribedToId) {
        User subscribedTo = userRepository.findById(subscribedToId)
                .orElseThrow(() -> new NotFoundException(User.class, "User id " + subscribedToId + " not found."));
        if (!subscribedTo.isAllowSubscriptions()) {
            throw new SubscriptionNotAllowedException("User does not allow subscriptions");
        }
        if (subscriptionRepository.existsBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId)) {
            throw new AlreadySubscribedException("User " + subscriberId + " is already subscribed to user " + subscribedToId);
        }
        Subscription subscription = new Subscription();
        subscription.setSubscriberId(subscriberId);
        subscription.setSubscribedToId(subscribedToId);
        log.info("Subscribed to user Id {} Success", subscribedToId);
        return SubscriptionMapper.toDto(subscriptionRepository.save(subscription));
    }

    @Override
    public String unsubscribe(Long subscriberId, Long subscribedToId) {
        if (!subscriptionRepository.existsBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId)) {
            throw new SubscriptionNotFoundException("Subscription not found for user " + subscriberId + " to user " + subscribedToId);
        }
        subscriptionRepository.deleteBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId);
        return "User " + subscriberId + " successfully unsubscribed from user " + subscribedToId;
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(Long subscriberId) {
        if (!userRepository.existsById(subscriberId)) {
            throw new NotFoundException(Subscription.class, "User id " + subscriberId + " not found.");
        }
        return subscriptionRepository.findBySubscriberId(subscriberId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionDto> getSubscribers(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(Subscription.class, "User id " + userId + " not found.");
        }
        return subscriptionRepository.findBySubscribedToId(userId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSubscribedUsers(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(Subscription.class,"User id " + userId + " not found.");
        }
        List<Subscription> subscriptions = subscriptionRepository.findBySubscriberId(userId);
        List<Long> subscribedUserIds = subscriptions.stream()
                .map(Subscription::getSubscribedToId)
                .collect(Collectors.toList());
        return userRepository.findByIdIn(subscribedUserIds).stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSubscriberNames(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(Subscription.class,"User id " + userId + " not found.");
        }
        List<Subscription> subscribers = subscriptionRepository.findBySubscribedToId(userId);
        List<Long> subscriberIds = subscribers.stream()
                .map(Subscription::getSubscriberId)
                .collect(Collectors.toList());
        return userRepository.findByIdIn(subscriberIds).stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }
}