package io.rogermoore.hexagonal.command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
