package io.rogermoore.hexagonal.user.command;

import com.google.gson.Gson;
import io.rogermoore.hexagonal.command.UnsupportedCommandException;
import io.rogermoore.hexagonal.event.Event;
import io.rogermoore.hexagonal.event.EventLogger;
import io.rogermoore.hexagonal.event.EventType;
import io.rogermoore.hexagonal.user.User;
import io.rogermoore.hexagonal.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserCommandHandlerTest {

    private static final User USER = User.builder()
            .withFirstName("test")
            .withLastName("user")
            .withAge(10)
            .withUserUuid(UUID.randomUUID())
            .build();

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventLogger eventLogger;


    private UserCommandHandler userCommandHandler;

    @BeforeEach
    void setup() {
        userCommandHandler = new UserCommandHandler(userRepository, eventLogger);
    }

    @Test
    void handle_createUser() {
        Gson gson = new Gson();
        CreateUserCommand createUserCommand = mock(CreateUserCommand.class);
        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        given(createUserCommand.getUser()).willReturn(USER);

        userCommandHandler.handle(createUserCommand);

        verify(userRepository).add(USER);
        verify(eventLogger).log(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getEventId()).isNotNull();
        assertThat(eventArgumentCaptor.getValue().getEventType()).isEqualTo(EventType.CREATE_USER);
        assertThat(eventArgumentCaptor.getValue().getEventBody()).isEqualTo(gson.toJson(USER));
    }

    @Test
    void handle_unsupportedCommand() {
        Throwable thrown = catchThrowable(() -> userCommandHandler.handle(new InvalidCommand()));

        assertThat(thrown).isInstanceOf(UnsupportedCommandException.class);
        assertThat(thrown).hasMessage(String.format("Unsupported command: %s", InvalidCommand.class.getTypeName()));

        ArgumentCaptor<Event> eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventLogger).log(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getEventId()).isNotNull();
        assertThat(eventArgumentCaptor.getValue().getEventType()).isEqualTo(EventType.FAILED_COMMAND);
        assertThat(eventArgumentCaptor.getValue().getEventBody()).isEqualTo("{\"body\":\"test\"}");
    }

    private static class InvalidCommand implements UserCommand {
        public String body = "test";
    }

}
