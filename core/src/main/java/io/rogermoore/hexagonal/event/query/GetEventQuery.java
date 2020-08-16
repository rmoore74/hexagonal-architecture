package io.rogermoore.hexagonal.event.query;

import java.util.UUID;

public class GetEventQuery implements EventQuery {

    private final UUID eventId;

    public GetEventQuery(final UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getEventId() {
        return eventId;
    }
}
