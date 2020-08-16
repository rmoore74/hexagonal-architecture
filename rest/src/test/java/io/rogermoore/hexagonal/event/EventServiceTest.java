package io.rogermoore.hexagonal.event;

import io.rogermoore.hexagonal.event.query.EventQueryHandler;
import io.rogermoore.hexagonal.event.query.GetAllEventsQuery;
import io.rogermoore.hexagonal.event.query.GetEventQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventQueryHandler eventQueryHandler;

    private EventService eventService;

    @BeforeEach
    void setup() {
        eventService = new EventService(eventQueryHandler);
    }

    @Test
    void getAllEvents() {
        given(eventQueryHandler.handle(any(GetAllEventsQuery.class))).willReturn(List.of());

        List<Event> result = eventService.getAllEvents();

        assertThat(result).isEqualTo(List.of());
    }

    @Test
    void getEvent() {
        ArgumentCaptor<GetEventQuery> captor = ArgumentCaptor.forClass(GetEventQuery.class);
        UUID eventId = UUID.randomUUID();
        Event event = mock(Event.class);
        given(eventQueryHandler.handle(captor.capture())).willReturn(List.of(event));

        eventService.getEvent(eventId);

        assertThat(captor.getValue().getEventId()).isEqualTo(eventId);
    }

}
