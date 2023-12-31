package ru.practicum.subscription;

import lombok.experimental.UtilityClass;
import ru.practicum.subscription.dto.SubscriptionDto;

@UtilityClass
public class SubscriptionMapper {

    public static SubscriptionDto toDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setSubscriberId(subscription.getSubscriberId());
        dto.setSubscribedToId(subscription.getSubscribedToId());
        return dto;
    }

    public static Subscription toEntity(SubscriptionDto dto) {
        Subscription subscription = new Subscription();
        subscription.setId(dto.getId());
        subscription.setSubscriberId(dto.getSubscriberId());
        subscription.setSubscribedToId(dto.getSubscribedToId());
        return subscription;
    }
}