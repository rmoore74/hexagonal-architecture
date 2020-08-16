package io.rogermoore.hexagonal;

import io.rogermoore.hexagonal.event.Event;
import io.rogermoore.hexagonal.event.EventNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named
public class EventLogger implements io.rogermoore.hexagonal.event.EventLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogger.class);

    private final ConcurrentMap<UUID, Event> events;

    public EventLogger() {
        events = new ConcurrentHashMap<>();
    }

    /*
     * FOR TEST PURPOSE
     */
    EventLogger(final ConcurrentMap<UUID, Event> events) {
        this.events = events;
    }

    @Override
    public void log(Event event) {
        LOGGER.info("eventId: {}, eventType: {}, eventBody: {}", event.getEventId(), event.getEventType(), event.getEventBody());
        events.put(event.getEventId(), event);
    }

    @Override
    public Event fetchEvent(UUID eventId) {
        return Optional.ofNullable(events.get(eventId))
                .orElseThrow(() -> new EventNotFoundException(eventId));
    }

    @Override
    public List<Event> fetchEvents() {
        return List.copyOf(events.values());
    }

}
