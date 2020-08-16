package io.rogermoore.hexagonal.user;

import io.rogermoore.hexagonal.user.command.CreateUserCommand;
import io.rogermoore.hexagonal.user.command.UserCommandHandler;
import io.rogermoore.hexagonal.user.query.GetUserDetailsQuery;
import io.rogermoore.hexagonal.user.query.UserQueryHandler;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named
public class UserService {

    private final UserQueryHandler queryHandler;
    private final UserCommandHandler commandHandler;
    private final UserAdapter userAdapter;

    @Inject
    public UserService(final UserQueryHandler queryHandler,
                       final UserCommandHandler commandHandler,
                       final UserAdapter userAdapter) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
        this.userAdapter = userAdapter;
    }

    public UUID createUser(final RestCreateUserRequest createUserRequest) {
        User user = userAdapter.adapt(createUserRequest);

        CreateUserCommand createUserCommand = new CreateUserCommand(user);
        commandHandler.handle(createUserCommand);

        return user.getUserUuid();
    }

    public RestGetUserDetailsResponse getUserDetails(final UUID uuid) {
        GetUserDetailsQuery getUserDetailsQuery = new GetUserDetailsQuery(uuid);
        User user = queryHandler.handle(getUserDetailsQuery).get(0);
        return userAdapter.adapt(user);
    }

}
