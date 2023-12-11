package ru.practicum.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.errorhandling.AlreadySubscribedException;
import ru.practicum.errorhandling.NotFoundException;
import ru.practicum.errorhandling.SubscriptionNotAllowedException;
import ru.practicum.errorhandling.SubscriptionNotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   SubscriptionMapper subscriptionMapper, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.userRepository = userRepository;
    }

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
        return SubscriptionMapper.toDto(subscriptionRepository.save(subscription));
    }

    @Override
    public void unsubscribe(Long subscriberId, Long subscribedToId) {
        if (!subscriptionRepository.existsBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId)) {
            throw new SubscriptionNotFoundException("Subscription not found for user " + subscriberId + " to user " + subscribedToId);
        }
        subscriptionRepository.deleteBySubscriberIdAndSubscribedToId(subscriberId, subscribedToId);
    }

    @Override
    public List<SubscriptionDto> getSubscriptions(Long subscriberId) {
        return subscriptionRepository.findBySubscriberId(subscriberId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .collect(Collectors.toList());
    }
}