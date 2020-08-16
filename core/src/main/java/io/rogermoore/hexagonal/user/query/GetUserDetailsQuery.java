package io.rogermoore.hexagonal.user.query;

import java.util.UUID;

public class GetUserDetailsQuery implements UserQuery {

    private final UUID userUuid;

    public GetUserDetailsQuery(final UUID userUuid) {
        this.userUuid = userUuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

}
