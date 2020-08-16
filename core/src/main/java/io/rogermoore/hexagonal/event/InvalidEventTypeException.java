package io.rogermoore.hexagonal.event;

public class InvalidEventTypeException extends RuntimeException {

    public InvalidEventTypeException(final String message) {
        super(message);
    }
}
