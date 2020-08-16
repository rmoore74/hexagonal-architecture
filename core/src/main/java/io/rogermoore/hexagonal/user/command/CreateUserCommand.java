package io.rogermoore.hexagonal.user.command;

import io.rogermoore.hexagonal.user.User;

public class CreateUserCommand implements UserCommand {

    private final User user;

    public CreateUserCommand(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
