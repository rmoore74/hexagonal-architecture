package io.rogermoore.hexagonal.event.query;

import io.rogermoore.hexagonal.event.Event;
import io.rogermoore.hexagonal.event.EventLogger;
import io.rogermoore.hexagonal.query.UnsupportedQueryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EventQueryHandlerTest {

    @Mock
    private EventLogger eventLogger;

    private EventQueryHandler eventQueryHandler;

    @BeforeEach
    void setup() {
        eventQueryHandler = new EventQueryHandler(eventLogger);
    }

    @Test
    void handle_getAllEvents() {
        GetAllEventsQuery event = mock(GetAllEventsQuery.class);
        given(eventLogger.fetchEvents()).willReturn(List.of());

        List<Event> result = eventQueryHandler.handle(event);

        assertThat(result).isEqualTo(List.of());
    }

    @Test
    void handle_getEvent() {
        GetEventQuery eventQuery = mock(GetEventQuery.class);
        Event event = mock(Event.class);
        UUID eventId = UUID.randomUUID();

        given(eventQuery.getEventId()).willReturn(eventId);
        given(eventLogger.fetchEvent(eventId)).willReturn(event);

        List<Event> result = eventQueryHandler.handle(eventQuery);

        assertThat(result).isEqualTo(List.of(event));
    }

    @Test
    void handle_unsupportedQuery() {
        Throwable thrown = catchThrowable(() -> eventQueryHandler.handle(new InvalidQuery()));

        assertThat(thrown).isInstanceOf(UnsupportedQueryException.class);
        assertThat(thrown).hasMessage(String.format("Unsupported query: %s", InvalidQuery.class.getTypeName()));
    }

    private static class InvalidQuery implements EventQuery {}

}
