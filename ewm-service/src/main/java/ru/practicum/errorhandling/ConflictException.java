package ru.practicum.errorhandling;

public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}