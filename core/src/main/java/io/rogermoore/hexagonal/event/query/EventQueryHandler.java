package io.rogermoore.hexagonal.event.query;

import io.rogermoore.hexagonal.event.Event;
import io.rogermoore.hexagonal.event.EventLogger;
import io.rogermoore.hexagonal.query.QueryHandler;
import io.rogermoore.hexagonal.query.UnsupportedQueryException;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Named
public class EventQueryHandler implements QueryHandler<Event, EventQuery> {

    private final EventLogger eventLogger;

    @Inject
    public EventQueryHandler(final EventLogger eventLogger) {
        this.eventLogger = eventLogger;
    }

    @Override
    public List<Event> handle(EventQuery query) {
        if (query instanceof GetAllEventsQuery) {
            return eventLogger.fetchEvents();
        }
        if (query instanceof GetEventQuery) {
            UUID eventId = ((GetEventQuery) query).getEventId();
            return Collections.singletonList(eventLogger.fetchEvent(eventId));
        }
        throw new UnsupportedQueryException(query);
    }

}
