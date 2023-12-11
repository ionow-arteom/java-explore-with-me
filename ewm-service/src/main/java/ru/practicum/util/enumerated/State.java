package ru.practicum.util.enumerated;

import ru.practicum.errorhandling.ValidationException;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static State getStateValue(String state) {
        try {
            return State.valueOf(state);
        } catch (Exception e) {
            throw new ValidationException("Unknown state: " + state);
        }
    }
}
