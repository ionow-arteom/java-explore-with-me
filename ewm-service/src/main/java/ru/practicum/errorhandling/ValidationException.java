package ru.practicum.errorhandling;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}