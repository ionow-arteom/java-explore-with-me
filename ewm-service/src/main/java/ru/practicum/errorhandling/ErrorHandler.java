package ru.practicum.errorhandling;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.errorhandling.exception.ConflictException;
import ru.practicum.errorhandling.exception.ValidationException;

@RestControllerAdvice
public class ErrorHandler {

    /**
     * Handles the exceptions of type NotFoundException.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final ChangeSetPersister.NotFoundException e) {
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
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles general exceptions to catch all that are not specifically managed.
     * @param e Exception to be handled.
     * @return ErrorResponse containing the error message.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(final Exception e) {
        return new ErrorResponse("An unexpected error occurred.");
    }
}