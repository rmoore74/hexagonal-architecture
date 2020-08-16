package io.rogermoore.hexagonal.event;

import java.util.List;
import java.util.UUID;

public interface EventLogger {
    void log(Event event);
    Event fetchEvent(UUID eventId);
    List<Event> fetchEvents();
}
