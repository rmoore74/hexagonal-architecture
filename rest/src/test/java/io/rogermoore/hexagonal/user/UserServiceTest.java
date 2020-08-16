package io.rogermoore.hexagonal.user;

import io.rogermoore.hexagonal.user.command.CreateUserCommand;
import io.rogermoore.hexagonal.user.command.UserCommandHandler;
import io.rogermoore.hexagonal.user.query.GetUserDetailsQuery;
import io.rogermoore.hexagonal.user.query.UserQueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final User USER = User.builder()
            .withUserUuid(UUID.randomUUID())
            .withFirstName("test")
            .withLastName("user")
            .withAge(10)
            .build();

    @Mock
    private UserQueryHandler userQueryHandler;

    @Mock
    private UserCommandHandler userCommandHandler;

    @Mock
    private UserAdapter userAdapter;

    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserService(userQueryHandler, userCommandHandler, userAdapter);
    }

    @Test
    void createUser() {
        RestCreateUserRequest request = mock(RestCreateUserRequest.class);
        given(userAdapter.adapt(request)).willReturn(USER);

        UUID result = userService.createUser(request);

        ArgumentCaptor<CreateUserCommand> captor = ArgumentCaptor.forClass(CreateUserCommand.class);
        verify(userCommandHandler).handle(captor.capture());
        assertThat(captor.getValue().getUser()).isEqualTo(USER);
        assertThat(result).isEqualTo(USER.getUserUuid());
    }

    @Test
    void getUserDetails() {
        RestGetUserDetailsResponse expected = mock(RestGetUserDetailsResponse.class);
        ArgumentCaptor<GetUserDetailsQuery> captor = ArgumentCaptor.forClass(GetUserDetailsQuery.class);
        given(userQueryHandler.handle(captor.capture())).willReturn(List.of(USER));
        given(userAdapter.adapt(USER)).willReturn(expected);

        RestGetUserDetailsResponse result = userService.getUserDetails(USER.getUserUuid());

        assertThat(captor.getValue().getUserUuid()).isEqualTo(USER.getUserUuid());
        assertThat(result).isEqualTo(expected);
    }

}
