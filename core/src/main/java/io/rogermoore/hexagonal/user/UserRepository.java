package io.rogermoore.hexagonal.user;

import java.util.UUID;

public interface UserRepository {

    void add(User user);
    User find(UUID user);

}
