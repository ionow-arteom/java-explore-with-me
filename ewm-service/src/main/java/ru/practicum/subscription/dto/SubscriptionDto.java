package ru.practicum.subscription.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Long id;
    private Long subscriberId;
    private Long subscribedToId;
}