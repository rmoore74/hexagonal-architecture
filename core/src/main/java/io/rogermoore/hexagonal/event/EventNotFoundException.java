package io.rogermoore.hexagonal.event;

import java.util.UUID;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(UUID eventId) {
        super(String.format("Event %s not found", eventId));
    }
}
