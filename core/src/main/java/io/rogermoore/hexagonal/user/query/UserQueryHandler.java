package io.rogermoore.hexagonal.user.query;

import io.rogermoore.hexagonal.event.EventFactory;
import io.rogermoore.hexagonal.event.EventLogger;
import io.rogermoore.hexagonal.query.QueryHandler;
import io.rogermoore.hexagonal.query.UnsupportedQueryException;
import io.rogermoore.hexagonal.user.User;
import io.rogermoore.hexagonal.user.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named
public class UserQueryHandler implements QueryHandler<User, UserQuery> {

    private final UserRepository userRepository;
    private final EventLogger eventLogger;

    @Inject
    public UserQueryHandler(final UserRepository userRepository,
                            final EventLogger eventLogger) {
        this.userRepository = userRepository;
        this.eventLogger = eventLogger;
    }

    @Override
    public List<User> handle(UserQuery query) {
        if (query instanceof GetUserDetailsQuery) {
            try {
                User result = userRepository.find(((GetUserDetailsQuery) query).getUserUuid());
                eventLogger.log(EventFactory.from(query, result));
                return Collections.singletonList(result);
            } catch (Exception e) {
                eventLogger.log(EventFactory.failed(query));
                throw e;
            }
        }
        throw new UnsupportedQueryException(query);
    }

}
