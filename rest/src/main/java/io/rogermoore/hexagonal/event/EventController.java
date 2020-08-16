package io.rogermoore.hexagonal.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@RestController
public class EventController {

    private final EventService eventService;

    @Inject
    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable final String eventId) {
        return ResponseEntity.ok(eventService.getEvent(UUID.fromString(eventId)));
    }

}
