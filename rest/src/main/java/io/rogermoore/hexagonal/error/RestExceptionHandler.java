package io.rogermoore.hexagonal.error;

import io.rogermoore.hexagonal.command.UnsupportedCommandException;
import io.rogermoore.hexagonal.event.EventNotFoundException;
import io.rogermoore.hexagonal.event.InvalidEventTypeException;
import io.rogermoore.hexagonal.query.UnsupportedQueryException;
import io.rogermoore.hexagonal.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User does not exist.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFound(EventNotFoundException exception, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Event does not exist.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEventTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEventType(InvalidEventTypeException exception, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid event type.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedQueryException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedQuery(UnsupportedQueryException exception, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid query.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedCommandException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedCommand(UnsupportedCommandException exception, WebRequest request) {
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid command.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
