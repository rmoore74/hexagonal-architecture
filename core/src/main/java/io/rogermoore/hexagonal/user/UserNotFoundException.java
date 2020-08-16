package io.rogermoore.hexagonal.user;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final UUID userUuid) {
        super(String.format("User %s does not exist", userUuid));
    }
}
