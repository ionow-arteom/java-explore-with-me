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
        LOGGER.error("NotFoundException: {}", e.getMessage());
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
        LOGGER.error("ValidationException: {}", e.getMessage());
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
        LOGGER.error("MethodArgumentNotValidException: {}", e.getMessage());
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
        LOGGER.error("ConstraintViolationException: {}", e.getMessage());
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
        LOGGER.error("ConflictException: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles unhandled exceptions.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnhandledException(final Exception e) {
        LOGGER.error("Unhandled Exception: {}", e.getMessage(), e);
        return new ErrorResponse("Internal Server Error");
    }
}