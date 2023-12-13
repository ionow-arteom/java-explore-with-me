package ru.practicum.subscription.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.subscription.dto.SubscriptionDto;
import ru.practicum.subscription.SubscriptionService;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDto> subscribe(@PathVariable Long userId, @RequestParam Long subscribedToId) {
        SubscriptionDto subscriptionDto = subscriptionService.subscribe(userId, subscribedToId);
        return ResponseEntity.ok(subscriptionDto);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<?> unsubscribe(@PathVariable Long userId, @RequestParam Long subscribedToId) {
        subscriptionService.unsubscribe(userId, subscribedToId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(@PathVariable Long userId) {
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptions(userId);
        return ResponseEntity.ok(subscriptionDtos);
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<SubscriptionDto>> getSubscribers(@PathVariable Long userId) {
        List<SubscriptionDto> subscribers = subscriptionService.getSubscribers(userId);
        return ResponseEntity.ok(subscribers);
    }

    @GetMapping("/subscribed-users")
    public ResponseEntity<List<String>> getSubscribedUsers(@PathVariable Long userId) {
        List<String> subscribedUserNames = subscriptionService.getSubscribedUsers(userId);
        return ResponseEntity.ok(subscribedUserNames);
    }

    @GetMapping("/subscribers-names")
    public ResponseEntity<List<String>> getSubscriberNames(@PathVariable Long userId) {
        List<String> subscriberNames = subscriptionService.getSubscriberNames(userId);
        return ResponseEntity.ok(subscriberNames);
    }
}