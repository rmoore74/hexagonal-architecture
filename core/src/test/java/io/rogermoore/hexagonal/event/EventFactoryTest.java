package io.rogermoore.hexagonal.event;

import io.rogermoore.hexagonal.command.Command;
import io.rogermoore.hexagonal.event.query.GetAllEventsQuery;
import io.rogermoore.hexagonal.query.Query;
import io.rogermoore.hexagonal.user.command.CreateUserCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventFactoryTest {

    @Test
    void from_command() {
        Event result = EventFactory.from(new CreateUserCommand(null), "test");

        assertThat(result.getEventId()).isNotNull();
        assertThat(result.getEventType()).isEqualTo(EventType.CREATE_USER);
        assertThat(result.getEventBody()).isEqualTo("\"test\"");
    }

    @Test
    void from_query() {
        Event result = EventFactory.from(new GetAllEventsQuery(), "test");

        assertThat(result.getEventId()).isNotNull();
        assertThat(result.getEventType()).isEqualTo(EventType.GET_ALL_EVENTS);
        assertThat(result.getEventBody()).isEqualTo("\"test\"");
    }

    @Test
    void failed_command() {
        Event result = EventFactory.failed(new MockCommand());

        assertThat(result.getEventId()).isNotNull();
        assertThat(result.getEventType()).isEqualTo(EventType.FAILED_COMMAND);
        assertThat(result.getEventBody()).isEqualTo("{\"mockMessage\":\"mock\"}");
    }

    @Test
    void failed_query() {
        Event result = EventFactory.failed(new MockQuery());

        assertThat(result.getEventId()).isNotNull();
        assertThat(result.getEventType()).isEqualTo(EventType.FAILED_QUERY);
        assertThat(result.getEventBody()).isEqualTo("{\"mockMessage\":\"mock\"}");
    }

    private static class MockQuery implements Query {
        public String mockMessage = "mock";
    }

    private static class MockCommand implements Command {
        public String mockMessage = "mock";
    }

}
