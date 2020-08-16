package io.rogermoore.hexagonal.event;

import java.util.UUID;

public class Event {

    private final UUID eventId;
    private final EventType eventType;
    private final String eventBody;

    public Event(final UUID eventId,
                 final EventType eventType,
                 final String eventBody) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventBody = eventBody;
    }

    public UUID getEventId() {
        return eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getEventBody() {
        return eventBody;
    }

}
