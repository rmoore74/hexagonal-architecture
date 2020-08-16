package io.rogermoore.hexagonal.command;

public class UnsupportedCommandException extends RuntimeException {
    public UnsupportedCommandException(final Command command) {
        super(String.format("Unsupported command: %s", command.getClass().getTypeName()));
    }
}
