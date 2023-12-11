package ru.practicum.errorhandling;

public class SubscriptionNotAllowedException extends RuntimeException {
    public SubscriptionNotAllowedException(String message) {
        super(message);
    }
}