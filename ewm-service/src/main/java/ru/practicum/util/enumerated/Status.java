package ru.practicum.util.enumerated;

import ru.practicum.errorhandling.ValidationException;

public enum Status {

    PENDING,
    CONFIRMED,
    CANCELED,
    REJECTED;

    public static Status getStatusValue(String status) {
        try {
            return Status.valueOf(status);
        } catch (Exception e) {
            throw new ValidationException("Unknown status: " + status);
        }
    }
}