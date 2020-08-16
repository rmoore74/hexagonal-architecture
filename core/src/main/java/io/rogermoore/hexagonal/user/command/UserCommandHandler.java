package io.rogermoore.hexagonal.user.command;

import io.rogermoore.hexagonal.command.CommandHandler;
import io.rogermoore.hexagonal.command.UnsupportedCommandException;
import io.rogermoore.hexagonal.event.EventFactory;
import io.rogermoore.hexagonal.event.EventLogger;
import io.rogermoore.hexagonal.user.User;
import io.rogermoore.hexagonal.user.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserCommandHandler implements CommandHandler<UserCommand> {

    private final UserRepository userRepository;
    private final EventLogger eventLogger;

    @Inject
    public UserCommandHandler(final UserRepository userRepository,
                              final EventLogger eventLogger) {
        this.userRepository = userRepository;
        this.eventLogger = eventLogger;
    }

    @Override
    public void handle(UserCommand command) {
        if (command instanceof CreateUserCommand) {
            User user = ((CreateUserCommand) command).getUser();
            userRepository.add(user);
            eventLogger.log(EventFactory.from(command, user));
        } else {
            eventLogger.log(EventFactory.failed(command));
            throw new UnsupportedCommandException(command);
        }
    }

}
