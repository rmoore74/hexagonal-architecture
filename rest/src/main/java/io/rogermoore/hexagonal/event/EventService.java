package io.rogermoore.hexagonal.event;

import io.rogermoore.hexagonal.event.query.EventQueryHandler;
import io.rogermoore.hexagonal.event.query.GetAllEventsQuery;
import io.rogermoore.hexagonal.event.query.GetEventQuery;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
public class EventService {

    private final EventQueryHandler eventQueryHandler;

    @Inject
    public EventService(final EventQueryHandler eventQueryHandler) {
        this.eventQueryHandler = eventQueryHandler;
    }

    public List<Event> getAllEvents() {
        GetAllEventsQuery eventQuery = new GetAllEventsQuery();
        return eventQueryHandler.handle(eventQuery);
    }

    public Event getEvent(UUID eventId) {
        GetEventQuery eventQuery = new GetEventQuery(eventId);
        return eventQueryHandler.handle(eventQuery).get(0);
    }

}
