package io.rogermoore.hexagonal.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventService eventService;

    private EventController eventController;

    @BeforeEach
    void setup() {
        eventController = new EventController(eventService);
    }

    @Test
    void getEvents() {
        given(eventService.getAllEvents()).willReturn(List.of());

        ResponseEntity<List<Event>> result = eventController.getEvents();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(List.of());
    }

    @Test
    void getEvent() {
        UUID eventId = UUID.randomUUID();
        Event event = mock(Event.class);
        given(eventService.getEvent(eventId)).willReturn(event);

        ResponseEntity<Event> result = eventController.getEvent(eventId.toString());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(event);
    }

}
