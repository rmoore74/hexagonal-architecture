package io.rogermoore.hexagonal;

import io.rogermoore.hexagonal.event.Event;
import io.rogermoore.hexagonal.event.EventNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventLoggerTest {

    private static final UUID EVENT_ID = UUID.randomUUID();

    @Mock
    private Event event;

    @Mock
    private ConcurrentMap<UUID, Event> events;

    private EventLogger eventLogger;

    @BeforeEach
    void setup() {
        eventLogger = new EventLogger(events);
    }

    @Test
    void log() {
        given(event.getEventId()).willReturn(EVENT_ID);

        eventLogger.log(event);

        verify(events).put(EVENT_ID, event);
    }

    @Test
    void fetchEvent() {
        given(events.get(EVENT_ID)).willReturn(event);

        Event result = eventLogger.fetchEvent(EVENT_ID);

        assertThat(result).isEqualTo(event);
    }

    @Test
    void fetchEvent_nonExistantEvent() {
        Throwable thrown = catchThrowable(() -> eventLogger.fetchEvent(EVENT_ID));

        assertThat(thrown).isInstanceOf(EventNotFoundException.class);
        assertThat(thrown).hasMessage(String.format("Event %s not found", EVENT_ID));
    }

    @Test
    void fetchEvents() {
        given(events.values()).willReturn(List.of());

        List<Event> result = eventLogger.fetchEvents();

        assertThat(result).isEqualTo(List.of());
    }
}
