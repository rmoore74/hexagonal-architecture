package io.rogermoore.hexagonal.user.query;

import com.google.gson.Gson;
import io.rogermoore.hexagonal.event.Event;
import io.rogermoore.hexagonal.event.EventLogger;
import io.rogermoore.hexagonal.event.EventType;
import io.rogermoore.hexagonal.query.UnsupportedQueryException;
import io.rogermoore.hexagonal.user.User;
import io.rogermoore.hexagonal.user.UserNotFoundException;
import io.rogermoore.hexagonal.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserQueryHandlerTest {

    private static final Gson GSON = new Gson();
    private static final User USER = User.builder()
            .withUserUuid(UUID.randomUUID())
            .withFirstName("test")
            .withLastName("user")
            .withAge(10)
            .build();

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventLogger eventLogger;

    private GetUserDetailsQuery getUserDetailsQuery;

    private ArgumentCaptor<Event> eventArgumentCaptor;

    private UserQueryHandler userQueryHandler;

    @BeforeEach
    void setup() {
        eventArgumentCaptor = ArgumentCaptor.forClass(Event.class);
        getUserDetailsQuery = new GetUserDetailsQuery(USER.getUserUuid());

        userQueryHandler = new UserQueryHandler(userRepository, eventLogger);
    }

    @Test
    void handle_getUserDetails() {
        given(userRepository.find(USER.getUserUuid())).willReturn(USER);

        List<User> result = userQueryHandler.handle(getUserDetailsQuery);

        assertThat(result).isEqualTo(List.of(USER));

        verify(eventLogger).log(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getEventId()).isNotNull();
        assertThat(eventArgumentCaptor.getValue().getEventType()).isEqualTo(EventType.GET_USER_DETAILS);
        assertThat(eventArgumentCaptor.getValue().getEventBody()).isEqualTo(GSON.toJson(USER));
    }

    @Test
    void handle_noUserFound() {
        given(userRepository.find(USER.getUserUuid())).willThrow(new UserNotFoundException(USER.getUserUuid()));

        Throwable thrown = catchThrowable(() -> userQueryHandler.handle(getUserDetailsQuery));
        assertThat(thrown).isInstanceOf(UserNotFoundException.class);
        assertThat(thrown).hasMessage(String.format("User %s does not exist", USER.getUserUuid()));

        verify(eventLogger).log(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getEventId()).isNotNull();
        assertThat(eventArgumentCaptor.getValue().getEventType()).isEqualTo(EventType.FAILED_QUERY);
        assertThat(eventArgumentCaptor.getValue().getEventBody()).isEqualTo(GSON.toJson(getUserDetailsQuery));
    }

    @Test
    void handle_unsupportedQuery() {
        Throwable thrown = catchThrowable(() -> userQueryHandler.handle(new InvalidQuery()));

        assertThat(thrown).isInstanceOf(UnsupportedQueryException.class);
        assertThat(thrown).hasMessage(String.format("Unsupported query: %s", InvalidQuery.class.getTypeName()));
    }

    private static final class InvalidQuery implements UserQuery {
        public static String body = "test";
    }

}
