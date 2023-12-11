package ru.practicum.errorhandling;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * Handles the exceptions of type NotFoundException.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles the exceptions related to validation.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles exceptions related to invalid method arguments.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles exceptions related to constraint violations.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConstraintViolation(final ConstraintViolationException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles exceptions of type ConflictException.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final ConflictException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAlreadySubscribedException(final AlreadySubscribedException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSubscriptionNotFoundException(final SubscriptionNotFoundException e) {
        logError(e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Logs the error message and stack trace.
     * @param e Exception to be logged.
     */
    private void logError(Exception e) {
        LOGGER.error("Error occurred: {}", e.getMessage(), e);
    }
}