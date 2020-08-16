package io.rogermoore.hexagonal.event;

import io.rogermoore.hexagonal.command.Command;
import io.rogermoore.hexagonal.event.query.GetAllEventsQuery;
import io.rogermoore.hexagonal.event.query.GetEventQuery;
import io.rogermoore.hexagonal.query.Query;
import io.rogermoore.hexagonal.user.command.CreateUserCommand;
import io.rogermoore.hexagonal.user.query.GetUserDetailsQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EventTypeTest {

    @Test
    void from_command_invalidEventType() {
        Command command = new InvalidCommand();

        Throwable thrown = catchThrowable(() -> EventType.from(command));

        assertThat(thrown).isInstanceOf(InvalidEventTypeException.class);
        assertThat(thrown).hasMessage(String.format("Event type for %s not supported", command.getClass().getTypeName()));
    }

    @Test
    void from_query_invalidEventType() {
        Query query = new InvalidQuery();

        Throwable thrown = catchThrowable(() -> EventType.from(query));

        assertThat(thrown).isInstanceOf(InvalidEventTypeException.class);
        assertThat(thrown).hasMessage(String.format("Event type for %s not supported", query.getClass().getTypeName()));
    }

    @ParameterizedTest
    @MethodSource("commandByEventType")
    void from_command(Command command, EventType eventType) {
        assertThat(EventType.from(command)).isEqualTo(eventType);
    }

    @ParameterizedTest
    @MethodSource("queryByEventType")
    void from_query(Query query, EventType eventType) {
        assertThat(EventType.from(query)).isEqualTo(eventType);
    }

    private static Stream<Arguments> commandByEventType() {
        return Stream.of(
                Arguments.of(mock(CreateUserCommand.class), EventType.CREATE_USER)
        );
    }

    private static Stream<Arguments> queryByEventType() {
        return Stream.of(
                Arguments.of(mock(GetUserDetailsQuery.class), EventType.GET_USER_DETAILS),
                Arguments.of(mock(GetAllEventsQuery.class), EventType.GET_ALL_EVENTS),
                Arguments.of(mock(GetEventQuery.class), EventType.GET_EVENT)
        );
    }

    private static class InvalidCommand implements Command {}
    private static class InvalidQuery implements Query {}

}
