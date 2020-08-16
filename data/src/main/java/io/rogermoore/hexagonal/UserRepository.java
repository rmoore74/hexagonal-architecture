package io.rogermoore.hexagonal;

import io.rogermoore.hexagonal.user.User;
import io.rogermoore.hexagonal.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named
public class UserRepository implements io.rogermoore.hexagonal.user.UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private final ConcurrentMap<UUID, User> userStore;

    public UserRepository() {
        this.userStore = new ConcurrentHashMap<>();
    }

    /*
     * FOR TEST PURPOSE
     */
    UserRepository(ConcurrentMap<UUID, User> userStore) {
        this.userStore = userStore;
    }

    @Override
    public void add(User user) {
        LOGGER.info("Adding user: {}", user.getUserUuid());
        userStore.put(user.getUserUuid(), user);
    }

    @Override
    public User find(UUID userUuid) {
        LOGGER.info("Finding user: {}", userUuid);
        return Optional.ofNullable(userStore.get(userUuid))
                .orElseThrow(() -> new UserNotFoundException(userUuid));
    }

}
