package ru.practicum.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionDto> subscribe(@RequestParam Long subscriberId, @RequestParam Long subscribedToId) {
        SubscriptionDto subscriptionDto = subscriptionService.subscribe(subscriberId, subscribedToId);
        return ResponseEntity.ok(subscriptionDto);
    }

    @DeleteMapping
    public ResponseEntity<?> unsubscribe(@RequestParam Long subscriberId, @RequestParam Long subscribedToId) {
        subscriptionService.unsubscribe(subscriberId, subscribedToId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions(@RequestParam Long subscriberId) {
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptions(subscriberId);
        return ResponseEntity.ok(subscriptionDtos);
    }
}